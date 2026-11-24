package com.learnian.quiz.data.remote

import com.learnian.core.network.util.KtorClientWrapper
import com.learnian.core.network.util.Result as NetworkResult
import com.learnian.quiz.data.dto.QuizAckDto
import com.learnian.quiz.data.dto.QuizAnswerRequest
import com.learnian.quiz.data.dto.QuizSessionDto
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

/**
 * Remote data source for the quiz feature.
 *
 * Endpoints (relative to [com.learnian.core.network.config.AppConfig.currentBaseUrl]):
 * - `GET  quiz/session` → one round; every question carries its own `type`
 * - `POST quiz/answers` → report one graded answer
 *
 * Calls are bounded by [REQUEST_TIMEOUT_MS] so an unreachable backend degrades quickly
 * to the bundled catalog instead of blocking the screen on a spinner.
 */
class QuizApi(
    private val client: KtorClientWrapper,
) {

    suspend fun fetchSession(): kotlin.Result<QuizSessionDto> {
        return request { client.get<QuizSessionDto>(urlString = "quiz/session") }
    }

    suspend fun submitAnswer(questionId: String, isCorrect: Boolean): kotlin.Result<Unit> {
        return request {
            client.post<QuizAckDto>(
                urlString = "quiz/answers",
                body = QuizAnswerRequest(questionId = questionId, isCorrect = isCorrect),
            )
        }.map { }
    }

    private suspend fun <T> request(block: suspend () -> NetworkResult<T>): kotlin.Result<T> {
        return try {
            when (val response = withTimeout(REQUEST_TIMEOUT_MS) { block() }) {
                is NetworkResult.Success -> kotlin.Result.success(response.data)
                is NetworkResult.Error -> kotlin.Result.failure(
                    QuizApiException(response.message, response.cause),
                )

                is NetworkResult.Loading -> kotlin.Result.failure(
                    QuizApiException("Request is still loading"),
                )
            }
        } catch (timeout: TimeoutCancellationException) {
            kotlin.Result.failure(QuizApiException("Request timed out", timeout))
        }
    }

    private companion object {
        const val REQUEST_TIMEOUT_MS = 1_800L
    }
}

class QuizApiException(message: String, cause: Throwable? = null) : Exception(message, cause)
