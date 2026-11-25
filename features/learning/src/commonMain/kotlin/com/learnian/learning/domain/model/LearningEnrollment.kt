package com.learnian.learning.domain.model

import kotlin.math.roundToInt

/** Reading progress of one package for the signed-in learner. */
data class PackageProgress(
    val learnedWords: Int = 0,
    val totalWords: Int = 0,
    val dueToday: Int = 0,
    val learnedToday: Int = 0,
) {
    val fraction: Float
        get() = if (totalWords <= 0) 0f else (learnedWords.toFloat() / totalWords).coerceIn(0f, 1f)

    val percent: Int
        get() = (fraction * 100f).roundToInt()

    val remainingWords: Int
        get() = (totalWords - learnedWords).coerceAtLeast(0)
}

/**
 * A package the learner has already added to their daily plan, together with the
 * per-package study settings.
 */
data class PackageEnrollment(
    val packageId: String,
    val dailyQuota: Int,
    val practiceMode: PracticeMode,
    val isActive: Boolean,
    val progress: PackageProgress,
)

/** A package paired with its enrollment, ready for the hub and detail screens. */
data class EnrolledPackage(
    val packageInfo: LearningPackage,
    val enrollment: PackageEnrollment,
) {
    val progress: PackageProgress get() = enrollment.progress
    val dailyQuota: Int get() = enrollment.dailyQuota
}

/** The plan the learner picks *before* enrolling into a package. */
data class StudyPlan(
    val dailyNewWords: Int,
    val practiceMode: PracticeMode,
)

/** Editable settings of an already enrolled package (the payload of the plan endpoint). */
data class PackageStudySettings(
    val dailyQuota: Int,
    val practiceMode: PracticeMode,
    val isActive: Boolean,
    val dailyCapacity: Int = DEFAULT_DAILY_CAPACITY,
)
