package com.learnian.stats.data.remote

import com.learnian.core.network.util.KtorClientWrapper
import com.learnian.core.network.util.Result as NetworkResult
import com.learnian.stats.data.dto.StatsDashboardDto
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

/**
 * Remote data source for the stats feature.
 *
 * Endpoint (relative to [com.learnian.core.network.config.AppConfig.currentBaseUrl]):
 * - `GET stats/dashboard` → one snapshot with every section of the stats tab
 *
 * The call is bounded by [REQUEST_TIMEOUT_MS] so an unreachable backend degrades quickly
 * to the bundled catalog instead of blocking the screen on a spinner.
 */
class StatsApi(
    private val client: KtorClientWrapper,
) {

    suspend fun fetchDashboard(): kotlin.Result<StatsDashboardDto> {
        return request { client.get<StatsDashboardDto>(urlString = "stats/dashboard") }
    }

    private suspend fun <T> request(block: suspend () -> NetworkResult<T>): kotlin.Result<T> {
        return try {
            when (val response = withTimeout(REQUEST_TIMEOUT_MS) { block() }) {
                is NetworkResult.Success -> kotlin.Result.success(response.data)
                is NetworkResult.Error -> kotlin.Result.failure(
                    StatsApiException(response.message, response.cause),
                )

                is NetworkResult.Loading -> kotlin.Result.failure(
                    StatsApiException("Request is still loading"),
                )
            }
        } catch (timeout: TimeoutCancellationException) {
            kotlin.Result.failure(StatsApiException("Request timed out", timeout))
        }
    }

    private companion object {
        const val REQUEST_TIMEOUT_MS = 1_800L
    }
}

class StatsApiException(message: String, cause: Throwable? = null) : Exception(message, cause)
