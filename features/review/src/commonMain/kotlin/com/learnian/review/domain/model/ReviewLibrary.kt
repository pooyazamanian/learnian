package com.learnian.review.domain.model

/**
 * One vocabulary card of the review hub, enriched with its spaced-repetition
 * progress.
 *
 * [wordId] is the navigation identity of the card: it is the exact same id the
 * deep-dive detail screen resolves, so every list entry can open the full word
 * card without carrying a payload through navigation.
 */
data class LibraryWord(
    val wordId: String,
    val term: String,
    val phonetic: String,
    val level: String,
    val partOfSpeechFa: String,
    val partOfSpeechEn: String,
    val meaning: String,
    val explanation: String,
    val simpleEnglishDefinition: String,
    val example: String,
    val examplePersian: String,
    val tags: List<String>,
    val isSaved: Boolean,
    val masteryPercent: Int,
    val reviewCount: Int,
    val stabilityHours: Int,
    val difficulty: Int,
    val lastReviewedHoursAgo: Int,
    val nextReviewDays: Float,
    val status: WordMasteryStatus,
) {
    /** Mastery as a 0..1 fraction, ready for a progress indicator. */
    val masteryFraction: Float get() = (masteryPercent / 100f).coerceIn(0f, 1f)
}

/** FSRS memory state of a word, used for the badge colour and the filters. */
enum class WordMasteryStatus {
    CHALLENGING,
    NEEDS_REVIEW,
    STABLE,
}

/**
 * Spaced-repetition progress attached to a word.
 *
 * It is a domain value object: the bundled catalog and (later) the FSRS
 * endpoint both produce it.
 */
data class WordReviewProgress(
    val masteryPercent: Int = 0,
    val reviewCount: Int = 0,
    val stabilityHours: Int = 0,
    val difficulty: Int = 3,
    val lastReviewedHoursAgo: Int = 0,
    val nextReviewDays: Float = 0f,
    val status: WordMasteryStatus = WordMasteryStatus.CHALLENGING,
    val tags: List<String> = emptyList(),
)

/** Aggregate numbers rendered in the notebook performance strip. */
data class ReviewLibrarySummary(
    val totalWords: Int,
    val fsrsReviews: Int,
    val masteredWords: Int,
    val savedWords: Int,
    val overallMasteryPercent: Int,
)

/**
 * Everything the review hub needs in one snapshot: the words, their aggregate
 * numbers and the tags available for the saved-words filter.
 */
data class ReviewLibrary(
    val summary: ReviewLibrarySummary,
    val words: List<LibraryWord>,
    val tags: List<String>,
) {
    val savedWords: List<LibraryWord> get() = words.filter { it.isSaved }
}

/**
 * Assembles the library snapshot from the enriched words.
 *
 * Counts are derived from the very same list that is rendered, so the header can
 * never disagree with the cards below it.
 */
fun buildReviewLibrary(words: List<LibraryWord>): ReviewLibrary {
    val saved = words.filter { it.isSaved }
    val summary = ReviewLibrarySummary(
        totalWords = words.size,
        fsrsReviews = words.sumOf { it.reviewCount },
        masteredWords = words.count { it.status == WordMasteryStatus.STABLE },
        savedWords = saved.size,
        overallMasteryPercent = if (words.isEmpty()) {
            0
        } else {
            words.sumOf { it.masteryPercent } / words.size
        },
    )

    return ReviewLibrary(
        summary = summary,
        words = words,
        tags = saved.flatMap { it.tags }.distinct().sorted(),
    )
}
