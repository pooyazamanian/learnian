package com.learnian.learning.data.remote

import com.learnian.core.network.util.KtorClientWrapper
import com.learnian.core.network.util.Result as NetworkResult
import com.learnian.learning.data.dto.LearningCatalogDto
import com.learnian.learning.data.dto.SimpleResponse
import com.learnian.learning.data.dto.StudyPlanRequest
import com.learnian.learning.data.mapper.wireValue
import com.learnian.learning.domain.model.PackageStudySettings
import kotlinx.coroutines.withTimeout

/**
 * Ktor implementation of [LearningApi].
 *
 * Every call is bounded by a short timeout so a missing backend never blocks the
 * offline fallback of the repository. It stays bound in DI until the backend is
 * deployed; [DemoLearningApi] serves the same contract in the meantime.
 */
class LearningRemoteApi(
    private val client: KtorClientWrapper,
) : LearningApi {

    override suspend fun fetchCatalog(): Result<LearningCatalogDto> = request {
        client.get<LearningCatalogDto>(urlString = CATALOG_PATH)
    }

    override suspend fun saveStudyPlan(
        packageId: String,
        settings: PackageStudySettings,
    ): Result<Unit> = request {
        client.put<SimpleResponse>(
            urlString = planPath(packageId),
            body = StudyPlanRequest(
                dailyQuota = settings.dailyQuota,
                practiceMode = settings.practiceMode.wireValue,
                isActive = settings.isActive,
            ),
        )
    }.map { }

    override suspend fun resetStudyPlan(packageId: String): Result<Unit> = request {
        client.delete<SimpleResponse>(urlString = planPath(packageId))
    }.map { }

    private suspend fun <T> request(block: suspend () -> NetworkResult<T>): Result<T> = try {
        when (val response = withTimeout(REQUEST_TIMEOUT_MS) { block() }) {
            is NetworkResult.Success -> Result.success(response.data)
            is NetworkResult.Error -> Result.failure(
                LearningApiException(response.message, response.cause),
            )

            is NetworkResult.Loading -> Result.failure(
                LearningApiException("Request is still loading"),
            )
        }
    } catch (error: Exception) {
        Result.failure(LearningApiException(error.message ?: "Network error", error))
    }

    private companion object {
        const val REQUEST_TIMEOUT_MS = 1_800L
        const val CATALOG_PATH = "learning/catalog"

        fun planPath(packageId: String): String = "learning/packages/$packageId/plan"
    }
}