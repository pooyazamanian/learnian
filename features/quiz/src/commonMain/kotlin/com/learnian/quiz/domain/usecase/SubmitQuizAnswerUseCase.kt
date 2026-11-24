package com.learnian.quiz.domain.usecase

import com.learnian.quiz.domain.model.QuizAnswer
import com.learnian.quiz.domain.model.QuizAnswerResult
import com.learnian.quiz.domain.model.QuizPayload
import com.learnian.quiz.domain.model.QuizQuestion
import com.learnian.quiz.domain.repository.QuizRepository

/**
 * Grades one answer and reports it to the backend.
 *
 * The correctness rule lives here (not in the ViewModel) so it stays testable and so the
 * same payload can be re-checked server-side once the endpoint exists. The remote report
 * is best effort and never turns a correct answer into a failure.
 */
class SubmitQuizAnswerUseCase(
    private val repository: QuizRepository,
) {

    suspend operator fun invoke(question: QuizQuestion, answer: QuizAnswer): QuizAnswerResult {
        val isCorrect = when (val body = question.payload) {
            is QuizPayload.Choice ->
                answer is QuizAnswer.Option && answer.optionId == body.correctOptionId

            is QuizPayload.Spelling ->
                answer is QuizAnswer.Spelling && answer.text.equals(body.answer, ignoreCase = true)
        }

        repository.submitAnswer(question.id, isCorrect)

        return QuizAnswerResult(
            isCorrect = isCorrect,
            correctAnswer = question.correctAnswerText,
        )
    }
}
