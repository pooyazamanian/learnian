package com.learnian.review.domain.model

/**
 * FSRS (Free Spaced Repetition Scheduler) 4-tier user rating.
 */
enum class FsrsRating(val score: Int) {
    AGAIN(1),
    HARD(2),
    GOOD(3),
    EASY(4)
}
