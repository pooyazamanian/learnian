package com.learnian.learning.domain.model

import kotlin.math.roundToInt

/** Aggregated state of today's learning plan across every active package. */
data class TodayPlan(
    val totalNewWords: Int = 0,
    val completedNewWords: Int = 0,
    val dueWords: Int = 0,
    val activePackages: Int = 0,
    val activePackageId: String? = null,
    val activePackageTitle: String = "",
    val activePackageQuota: Int = 0,
) {
    val fraction: Float
        get() = if (totalNewWords <= 0) 0f else (completedNewWords.toFloat() / totalNewWords).coerceIn(0f, 1f)

    val percent: Int
        get() = (fraction * 100f).roundToInt()

    val remainingNewWords: Int
        get() = (totalNewWords - completedNewWords).coerceAtLeast(0)

    val hasActivePackage: Boolean
        get() = activePackageId != null && activePackages > 0
}

/** A CEFR shortcut card on the hub: "start this level" points to a featured package. */
data class CefrLevel(
    val band: CefrBand,
    val featuredPackageId: String,
    val wordCount: Int,
)

/** Everything the learning screens need, in one consistent snapshot. */
data class LearningCatalog(
    val todayPlan: TodayPlan = TodayPlan(),
    val myPackages: List<EnrolledPackage> = emptyList(),
    val packages: List<LearningPackage> = emptyList(),
    val levels: List<CefrLevel> = emptyList(),
    val categories: List<PackageCategory> = PackageCategory.entries.toList(),
    val studyCapacity: Int = DEFAULT_DAILY_CAPACITY,
) {
    val activeCount: Int get() = myPackages.count { it.enrollment.isActive }

    fun packageById(packageId: String): LearningPackage? =
        packages.firstOrNull { it.id == packageId }

    fun enrollmentOf(packageId: String): PackageEnrollment? =
        myPackages.firstOrNull { it.packageInfo.id == packageId }?.enrollment

    /** Detail payload of one package; `null` when the catalog no longer offers it. */
    fun detailsOf(packageId: String): PackageDetails? {
        val packageInfo = packageById(packageId) ?: return null
        return PackageDetails(
            packageInfo = packageInfo,
            enrollment = enrollmentOf(packageId),
            recommendedPlan = StudyPlan(
                dailyNewWords = packageInfo.recommendedDailyWords,
                practiceMode = PracticeMode.FLASHCARDS,
            ),
            dailyCapacity = studyCapacity,
        )
    }
}

/** Complete payload of the package detail (enrollment) and settings screens. */
data class PackageDetails(
    val packageInfo: LearningPackage,
    val enrollment: PackageEnrollment?,
    val recommendedPlan: StudyPlan,
    val dailyCapacity: Int = DEFAULT_DAILY_CAPACITY,
) {
    val isEnrolled: Boolean get() = enrollment != null

    /** Settings the UI starts from: the learner's enrollment or Learnian's recommendation. */
    val settings: PackageStudySettings
        get() = PackageStudySettings(
            dailyQuota = enrollment?.dailyQuota ?: recommendedPlan.dailyNewWords,
            practiceMode = enrollment?.practiceMode ?: recommendedPlan.practiceMode,
            isActive = enrollment?.isActive ?: true,
            dailyCapacity = dailyCapacity,
        )
}

/**
 * Builds the hub snapshot from the catalog and the learner's enrollments.
 *
 * Today's plan and the CEFR shortcuts are intentionally *derived* instead of stored:
 * quota changes, enrollments and reactivations are reflected immediately and the
 * server never has to send two copies of the same truth.
 */
fun buildLearningCatalog(
    packages: List<LearningPackage>,
    enrollments: List<PackageEnrollment>,
    studyCapacity: Int = DEFAULT_DAILY_CAPACITY,
    categories: List<PackageCategory> = PackageCategory.entries.toList(),
): LearningCatalog {
    val enrolled = enrollments
        .mapNotNull { enrollment ->
            packages.firstOrNull { it.id == enrollment.packageId }
                ?.let { EnrolledPackage(packageInfo = it, enrollment = enrollment) }
        }
        .sortedWith(
            compareByDescending<EnrolledPackage> { it.enrollment.isActive }
                .thenBy { it.packageInfo.title },
        )

    val active = enrolled.filter { it.enrollment.isActive }
    val featured = active.maxByOrNull { it.progress.dueToday } ?: active.firstOrNull()

    val todayPlan = TodayPlan(
        totalNewWords = active.sumOf { it.enrollment.dailyQuota },
        completedNewWords = active.sumOf { it.progress.learnedToday },
        dueWords = active.sumOf { it.progress.dueToday },
        activePackages = active.size,
        activePackageId = featured?.packageInfo?.id,
        activePackageTitle = featured?.packageInfo?.title.orEmpty(),
        activePackageQuota = featured?.enrollment?.dailyQuota ?: 0,
    )

    return LearningCatalog(
        todayPlan = todayPlan,
        myPackages = enrolled,
        packages = packages,
        levels = cefrLevelsOf(packages),
        categories = categories,
        studyCapacity = studyCapacity,
    )
}

/** One shortcut per CEFR band, pointing at its richest package. */
private fun cefrLevelsOf(packages: List<LearningPackage>): List<CefrLevel> =
    CefrBand.entries.mapNotNull { band ->
        packages
            .filter { it.cefrBand == band }
            .maxByOrNull { it.totalWords }
            ?.let { CefrLevel(band = band, featuredPackageId = it.id, wordCount = it.totalWords) }
    }