package com.learnian.quiz.data.repository

import com.learnian.quiz.data.mapper.toDomain
import com.learnian.quiz.data.remote.QuizApi
import com.learnian.quiz.data.sample.QuizCatalog
import com.learnian.quiz.domain.model.QuizSession
import com.learnian.quiz.domain.repository.QuizRepository

/**
 * Offline-first [QuizRepository].
 *
 * The round is requested from the API first; while the backend is not deployed (or a
 * request fails) the bundled [QuizCatalog] keeps every one of the five question modes
 * playable. Answer reporting is best effort, exactly like the grading the server will
 * later own.
 */
class QuizRepositoryImpl(
    private val api: QuizApi,
    private val catalog: QuizCatalog,
) : QuizRepository {

    override suspend fun getSession(): Result<QuizSession> {
        val remote = api.fetchSession()
            .getOrNull()
            ?.toDomain()
            ?.takeIf { it.questions.isNotEmpty() }

        return Result.success(remote ?: catalog.session())
    }

    override suspend fun submitAnswer(questionId: String, isCorrect: Boolean): Result<Unit> {
        // Best effort: the learner always keeps the locally graded result.
        return api.submitAnswer(questionId = questionId, isCorrect = isCorrect).map { }
    }
}
