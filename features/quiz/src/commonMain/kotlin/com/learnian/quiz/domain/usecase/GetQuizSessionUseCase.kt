package com.learnian.quiz.domain.usecase

import com.learnian.base.BaseNoParamsUseCase
import com.learnian.quiz.domain.model.QuizSession
import com.learnian.quiz.domain.repository.QuizRepository

/**
 * Loads a quiz round and guarantees the state layer never receives an empty round.
 */
class GetQuizSessionUseCase(
    private val repository: QuizRepository,
) : BaseNoParamsUseCase<Result<QuizSession>> {

    override suspend fun invoke(): Result<QuizSession> =
        repository.getSession().mapCatching { session ->
            if (session.questions.isEmpty()) {
                error("Quiz session has no questions")
            }
            session
        }
}
