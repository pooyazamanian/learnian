package com.learnian.review.data.sample

import com.learnian.review.domain.model.WordMasteryStatus
import com.learnian.review.domain.model.WordReviewProgress

/**
 * Bundled FSRS progress for the review hub (notebook, dictionary and saved lists).
 *
 * The library reuses [WordCatalog] for the vocabulary itself, so a card and its
 * deep-dive detail always describe the same word. This catalog only owns the
 * per-word scheduling numbers; when the backend exposes them, this file is the
 * single place to delete.
 */
class ReviewLibraryCatalog {

    private val progress: Map<String, WordReviewProgress> = mapOf(
        "word_abandon_1" to WordReviewProgress(
            masteryPercent = 32,
            reviewCount = 4,
            stabilityHours = 4,
            difficulty = 3,
            lastReviewedHoursAgo = 23,
            nextReviewDays = 4.2f,
            status = WordMasteryStatus.CHALLENGING,
            tags = listOf("۱۰۰ واژه ضروری", "آیلتس"),
        ),
        "word_resilient_2" to WordReviewProgress(
            masteryPercent = 78,
            reviewCount = 9,
            stabilityHours = 72,
            difficulty = 2,
            lastReviewedHoursAgo = 40,
            nextReviewDays = 12f,
            status = WordMasteryStatus.STABLE,
            tags = listOf("مکالمه"),
        ),
        "word_meticulous_3" to WordReviewProgress(
            masteryPercent = 91,
            reviewCount = 12,
            stabilityHours = 120,
            difficulty = 1,
            lastReviewedHoursAgo = 30,
            nextReviewDays = 18f,
            status = WordMasteryStatus.STABLE,
            tags = listOf("۱۰۰ واژه ضروری"),
        ),
        "word_benevolent_4" to WordReviewProgress(
            masteryPercent = 46,
            reviewCount = 3,
            stabilityHours = 8,
            difficulty = 4,
            lastReviewedHoursAgo = 50,
            nextReviewDays = 2.5f,
            status = WordMasteryStatus.NEEDS_REVIEW,
            tags = listOf("داستان کوتاه"),
        ),
        "word_ephemeral_5" to WordReviewProgress(
            masteryPercent = 28,
            reviewCount = 2,
            stabilityHours = 3,
            difficulty = 5,
            lastReviewedHoursAgo = 5,
            nextReviewDays = 1f,
            status = WordMasteryStatus.CHALLENGING,
            tags = listOf("آیلتس", "داستان کوتاه"),
        ),
        "word_pragmatic_6" to WordReviewProgress(
            masteryPercent = 61,
            reviewCount = 6,
            stabilityHours = 24,
            difficulty = 3,
            lastReviewedHoursAgo = 12,
            nextReviewDays = 6f,
            status = WordMasteryStatus.NEEDS_REVIEW,
            tags = listOf("مکالمه"),
        ),
    )

    /** Progress of [wordId], or neutral defaults for an unscheduled word. */
    fun progressFor(wordId: String): WordReviewProgress =
        progress[wordId] ?: WordReviewProgress()
}
