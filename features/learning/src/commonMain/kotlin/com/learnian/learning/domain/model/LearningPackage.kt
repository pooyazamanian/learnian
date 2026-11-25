package com.learnian.learning.domain.model

/**
 * Bounded business limits for the daily study quota of a single package.
 *
 * The quota is the number of *new* words the learner accepts to discover from
 * one package per day. It is always clamped into [MIN_DAILY_QUOTA]..[MAX_DAILY_QUOTA]
 * by the domain layer (not by the UI), so every entry point shares the same rule.
 */
const val MIN_DAILY_QUOTA: Int = 5
const val MAX_DAILY_QUOTA: Int = 60

/** Fallback quota used when the learner has never configured a package. */
const val DEFAULT_DAILY_QUOTA: Int = 20

/** Total daily study capacity (all packages) used to compute quota percentages. */
const val DEFAULT_DAILY_CAPACITY: Int = 40

/** Catalog taxonomy used for the discovery chips on the learning hub. */
enum class PackageCategory {
    GENERAL,
    EXAMS,
    MIGRATION,
}

/** The practice style the learner wants the package to be served with. */
enum class PracticeMode {
    FLASHCARDS,
    QUIZ,
}

/** Council of Europe reference levels, grouped the way the product sells them. */
enum class CefrBand {
    A1_A2,
    B1,
    B2,
    C1_C2,
}

/** A learner testimonial attached to a package (content, not chrome). */
data class PackageTestimonial(
    val quote: String,
    val authorName: String,
    val authorRank: String,
    val authorBadge: String,
    val authorInitials: String,
)

/**
 * A vocabulary package that can be enrolled into the learner's daily plan.
 *
 * Textual fields are package *content* delivered by the backend, therefore they
 * are modelled as plain strings. UI chrome (labels, captions, action titles)
 * always lives in the shared string resources.
 */
data class LearningPackage(
    val id: String,
    val title: String,
    val description: String,
    val category: PackageCategory,
    val cefrBand: CefrBand,
    val levelLabel: String,
    val audienceLabel: String,
    val updatedLabel: String,
    val version: String,
    val totalWords: Int,
    val lessonCount: Int,
    val audioCoveragePercent: Int,
    val fsrsVersion: String,
    val rating: Float,
    val ratingCount: Int,
    val studentCount: Int,
    val recommendedDailyWords: Int = DEFAULT_DAILY_QUOTA,
    val whyStudy: String = "",
    val highlights: List<String> = emptyList(),
    val testimonial: PackageTestimonial? = null,
) {
    /** Estimated number of days to finish the package with [dailyNewWords] new words per day. */
    fun estimatedDays(dailyNewWords: Int): Int {
        if (dailyNewWords <= 0) return 0
        val exact = totalWords.toFloat() / dailyNewWords
        val floored = exact.toInt()
        return if (floored.toFloat() == exact) floored else floored + 1
    }
}
