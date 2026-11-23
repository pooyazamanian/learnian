package com.learnian.review.data.remote

import com.learnian.core.network.util.KtorClientWrapper
import com.learnian.core.network.util.Result as NetworkResult
import com.learnian.review.data.dto.BookmarkRequest
import com.learnian.review.data.dto.RatingRequest
import com.learnian.review.data.dto.SimpleResponse
import com.learnian.review.data.dto.WordDetailDto
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

/**
 * Remote data source for the vocabulary review feature.
 *
 * Endpoints (relative to [com.learnian.core.network.config.AppConfig.currentBaseUrl]):
 * - `GET  words/{wordId}`           → full word detail payload
 * - `POST words/{wordId}/reviews`   → submit an FSRS grade
 * - `POST words/{wordId}/bookmark`  → persist bookmark state
 *
 * Calls are bounded by [REQUEST_TIMEOUT_MS] so an unreachable backend degrades
 * quickly to the offline catalog instead of blocking the screen on a spinner.
 */
class ReviewApi(
    private val client: KtorClientWrapper,
) {

    suspend fun fetchWordDetail(wordId: String): kotlin.Result<WordDetailDto> {
        return request { client.get<WordDetailDto>(urlString = "words/$wordId") }
    }

    suspend fun submitRating(wordId: String, rating: Int): kotlin.Result<Unit> {
        return request {
            client.post<SimpleResponse>(
                urlString = "words/$wordId/reviews",
                body = RatingRequest(rating = rating),
            )
        }.map { }
    }

    suspend fun setBookmark(wordId: String, bookmarked: Boolean): kotlin.Result<Unit> {
        return request {
            client.post<SimpleResponse>(
                urlString = "words/$wordId/bookmark",
                body = BookmarkRequest(bookmarked = bookmarked),
            )
        }.map { }
    }

    private suspend fun <T> request(block: suspend () -> NetworkResult<T>): kotlin.Result<T> {
        return try {
            when (val response = withTimeout(REQUEST_TIMEOUT_MS) { block() }) {
                is NetworkResult.Success -> kotlin.Result.success(response.data)
                is NetworkResult.Error -> kotlin.Result.failure(
                    ReviewApiException(response.message, response.cause),
                )

                is NetworkResult.Loading -> kotlin.Result.failure(
                    ReviewApiException("Request is still loading"),
                )
            }
        } catch (timeout: TimeoutCancellationException) {
            kotlin.Result.failure(ReviewApiException("Request timed out", timeout))
        }
    }

    private companion object {
        const val REQUEST_TIMEOUT_MS = 1_800L
    }
}

class ReviewApiException(message: String, cause: Throwable? = null) : Exception(message, cause)
