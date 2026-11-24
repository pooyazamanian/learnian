package com.learnian.stats.domain.model

/** How fast long-term memory is consolidating, as classified by the FSRS scheduler. */
enum class LearningMomentum {
    EXCELLENT,
    GOOD,
    SLOW,
}

/** A day inside the weekly review activity chart. */
enum class Weekday {
    SAT,
    SUN,
    MON,
    TUE,
    WED,
    THU,
    FRI,
}

/** FSRS mastery bucket a vocabulary card currently sits in. */
enum class MasteryTier {
    MASTERED,
    REVIEWING,
    NEW,
}

/** How safe the learner's memory stability currently is. */
enum class MemorySecurity {
    SECURE,
    MODERATE,
    AT_RISK,
}

/**
 * Everything the stats tab renders, delivered as one immutable snapshot.
 *
 * The server owns the numbers (counts, percentages, minutes); the app only knows how to
 * lay them out, which keeps the screen reusable for any user and any locale.
 */
data class StatsDashboard(
    val memory: MemorySummary,
    val vocabulary: VocabularySummary,
    val accuracy: AccuracySummary,
    val streak: StreakSummary,
    val weeklyActivity: WeeklyActivitySummary,
    val mastery: MasterySummary,
    val stability: StabilitySummary,
    val dailyGoal: DailyGoalSummary,
)

/** Long-term memory highlight shown in the FSRS hero card. */
data class MemorySummary(
    val promotedThisWeek: Int,
    val momentum: LearningMomentum,
)

/** Vocabulary counters shared by the two vocabulary metric cards. */
data class VocabularySummary(
    val totalWords: Int,
    val addedThisWeek: Int,
    val masteredWords: Int,
    val masteredPercent: Int,
)

/** Recall accuracy versus the daily target. */
data class AccuracySummary(
    val correctPercent: Int,
    val targetPercent: Int,
) {
    val isTargetMet: Boolean
        get() = correctPercent >= targetPercent
}

/** Current and all-time study streak. */
data class StreakSummary(
    val currentDays: Int,
    val recordDays: Int,
)

/** One reviewed day of the current week. */
data class DayActivity(
    val day: Weekday,
    val minutes: Int,
    val isToday: Boolean = false,
)

/** Weekly review activity: totals for the header chip and one entry per day. */
data class WeeklyActivitySummary(
    val totalMinutes: Int,
    val averageMinutes: Int,
    val days: List<DayActivity>,
) {
    val totalHours: Int
        get() = totalMinutes / MINUTES_PER_HOUR

    val remainingMinutes: Int
        get() = totalMinutes % MINUTES_PER_HOUR

    private companion object {
        const val MINUTES_PER_HOUR = 60
    }
}

/** One colored slice of the vocabulary mastery donut. */
data class MasterySegment(
    val tier: MasteryTier,
    val count: Int,
    val percent: Int,
)

/** Vocabulary split by FSRS mastery level. */
data class MasterySummary(
    val totalWords: Int,
    val segments: List<MasterySegment>,
)

/** Memory stability and tomorrow's retrieval chance. */
data class StabilitySummary(
    val retrievabilityPercent: Float,
    val stabilityDays: Float,
    val stabilityTargetDays: Int,
    val security: MemorySecurity,
) {
    /** Stability relative to its target, used by the second stability bar. */
    val stabilityProgress: Float
        get() = if (stabilityTargetDays <= 0) {
            0f
        } else {
            (stabilityDays / stabilityTargetDays).coerceIn(0f, 1f)
        }
}

/** Today's goal banner state. */
data class DailyGoalSummary(
    val isAchieved: Boolean,
    val queuedNewCards: Int,
    val progressPercent: Int,
)
