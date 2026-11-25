package com.learnian.learning.data.remote

import com.learnian.learning.data.dto.EnrollmentDto
import com.learnian.learning.data.dto.LearningCatalogDto
import com.learnian.learning.data.mapper.wireValue
import com.learnian.learning.data.sample.LearningSampleCatalog
import com.learnian.learning.domain.model.PackageStudySettings
import com.learnian.learning.domain.model.PracticeMode
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * In-memory stand-in for the learning backend, bound until the real API is deployed.
 *
 * It serves exactly the [LearningApi] contract: the catalog is the bundled snapshot,
 * writes mutate the fake server state and are acknowledged with success, and nothing
 * is stored on the device. Replacing this class with [LearningRemoteApi] in the DI
 * module is the only change the transition needs.
 */
class DemoLearningApi(
    private val sample: LearningSampleCatalog,
) : LearningApi {

    private val mutex = Mutex()

    /** Fake server state: the learner's enrollments, seeded with the demo plan. */
    private val enrollments: MutableMap<String, EnrollmentDto> =
        sample.seedEnrollments.associateBy { it.packageId }.toMutableMap()

    override suspend fun fetchCatalog(): Result<LearningCatalogDto> = mutex.withLock {
        Result.success(sample.catalog(enrollments = enrollments.values.toList()))
    }

    override suspend fun saveStudyPlan(
        packageId: String,
        settings: PackageStudySettings,
    ): Result<Unit> = mutex.withLock {
        if (sample.packages.none { it.id == packageId }) {
            return@withLock Result.failure(LearningApiException("Unknown package: $packageId"))
        }
        enrollments[packageId] = (enrollments[packageId] ?: EnrollmentDto(packageId = packageId))
            .copy(
                dailyQuota = settings.dailyQuota,
                practiceMode = settings.practiceMode.wireValue,
                isActive = settings.isActive,
            )
        Result.success(Unit)
    }

    override suspend fun resetStudyPlan(packageId: String): Result<Unit> = mutex.withLock {
        val packageInfo = sample.packages.firstOrNull { it.id == packageId }
            ?: return@withLock Result.failure(LearningApiException("Unknown package: $packageId"))
        val current = enrollments[packageId] ?: return@withLock Result.success(Unit)
        enrollments[packageId] = current.copy(
            dailyQuota = packageInfo.recommendedDailyWords,
            practiceMode = PracticeMode.FLASHCARDS.wireValue,
            isActive = true,
        )
        Result.success(Unit)
    }
}