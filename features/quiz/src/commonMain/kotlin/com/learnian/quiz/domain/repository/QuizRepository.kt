package com.learnian.quiz.domain.repository

import com.learnian.base.BaseRepository
import com.learnian.base.ResourceKey
import com.learnian.quiz.domain.model.QuizSession

/**
 * Source of quiz rounds.
 *
 * The implementation is offline-first: it asks the API first and falls back to the
 * bundled catalog while the backend is not deployed yet.
 */
interface QuizRepository : BaseRepository<QuizSession> {

    override val resourceKey: ResourceKey
        get() = ResourceKey("quiz_session")

    /** Loads the current quiz round (remote first, bundled catalog as fallback). */
    suspend fun getSession(): Result<QuizSession>

    /**
     * Reports a graded answer to the backend.
     *
     * Best effort: a failure must never block the learner, so callers keep the local
     * result and only log the submission error.
     */
    suspend fun submitAnswer(questionId: String, isCorrect: Boolean): Result<Unit>
}
