package com.learnian.quiz.domain.model

/**
 * One quiz round returned by the backend.
 *
 * [currentIndex] is the 0-based position the server wants the learner to resume from
 * and [totalCount] is the size of the round (it can exceed [questions] when the server
 * paginates the round).
 */
data class QuizSession(
    val id: String,
    val streakDays: Int,
    val accuracyPercent: Int,
    val currentIndex: Int,
    val totalCount: Int,
    val questions: List<QuizQuestion>,
) {
    val size: Int get() = questions.size

    val resolvedIndex: Int
        get() = currentIndex.coerceIn(0, (questions.lastIndex).coerceAtLeast(0))

    val resolvedTotal: Int
        get() = totalCount.coerceAtLeast(questions.size)
}
