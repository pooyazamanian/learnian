package com.learnian.learning.data.remote

import com.learnian.learning.data.dto.LearningCatalogDto
import com.learnian.learning.domain.model.PackageStudySettings

/**
 * Remote boundary of the learning bounded context.
 *
 * Reads return the full catalog snapshot; writes only acknowledge the call because the
 * server owns the plan. The UI never keeps a local copy of a setting: it reloads the
 * catalog after every confirmed write, so the screens always show server truth.
 */
interface LearningApi {

    suspend fun fetchCatalog(): Result<LearningCatalogDto>

    /** Creates or updates the study plan of one package (`PUT .../plan`). */
    suspend fun saveStudyPlan(
        packageId: String,
        settings: PackageStudySettings,
    ): Result<Unit>

    /** Restores Learnian's recommended plan for one package (`DELETE .../plan`). */
    suspend fun resetStudyPlan(packageId: String): Result<Unit>
}

/** Raised when the backend is unreachable or rejects a call. */
class LearningApiException(
    override val message: String,
    override val cause: Throwable? = null,
) : Exception(message, cause)