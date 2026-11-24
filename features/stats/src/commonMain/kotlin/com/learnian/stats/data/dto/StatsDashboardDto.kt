package com.learnian.stats.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire model of the stats dashboard.
 *
 * A single endpoint feeds the whole tab: every section is optional and independently
 * mapped, so the server can grow a new counter without breaking older clients.
 */
@Serializable
data class StatsDashboardDto(
    @SerialName("memory") val memory: MemorySummaryDto? = null,
    @SerialName("vocabulary") val vocabulary: VocabularySummaryDto? = null,
    @SerialName("accuracy") val accuracy: AccuracySummaryDto? = null,
    @SerialName("streak") val streak: StreakSummaryDto? = null,
    @SerialName("weekly_activity") val weeklyActivity: WeeklyActivityDto? = null,
    @SerialName("mastery") val mastery: MasterySummaryDto? = null,
    @SerialName("stability") val stability: StabilitySummaryDto? = null,
    @SerialName("daily_goal") val dailyGoal: DailyGoalDto? = null,
)

@Serializable
data class MemorySummaryDto(
    @SerialName("promoted_this_week") val promotedThisWeek: Int = 0,
    @SerialName("momentum") val momentum: String? = null,
)

@Serializable
data class VocabularySummaryDto(
    @SerialName("total_words") val totalWords: Int = 0,
    @SerialName("added_this_week") val addedThisWeek: Int = 0,
    @SerialName("mastered_words") val masteredWords: Int = 0,
    @SerialName("mastered_percent") val masteredPercent: Int = 0,
)

@Serializable
data class AccuracySummaryDto(
    @SerialName("correct_percent") val correctPercent: Int = 0,
    @SerialName("target_percent") val targetPercent: Int = 0,
)

@Serializable
data class StreakSummaryDto(
    @SerialName("current_days") val currentDays: Int = 0,
    @SerialName("record_days") val recordDays: Int = 0,
)

@Serializable
data class WeeklyActivityDto(
    @SerialName("total_minutes") val totalMinutes: Int = 0,
    @SerialName("average_minutes") val averageMinutes: Int = 0,
    @SerialName("days") val days: List<DayActivityDto> = emptyList(),
)

@Serializable
data class DayActivityDto(
    @SerialName("day") val day: String? = null,
    @SerialName("minutes") val minutes: Int = 0,
    @SerialName("is_today") val isToday: Boolean = false,
)

@Serializable
data class MasterySummaryDto(
    @SerialName("total_words") val totalWords: Int = 0,
    @SerialName("segments") val segments: List<MasterySegmentDto> = emptyList(),
)

@Serializable
data class MasterySegmentDto(
    @SerialName("tier") val tier: String? = null,
    @SerialName("count") val count: Int = 0,
    @SerialName("percent") val percent: Int = 0,
)

@Serializable
data class StabilitySummaryDto(
    @SerialName("retrievability_percent") val retrievabilityPercent: Float = 0f,
    @SerialName("stability_days") val stabilityDays: Float = 0f,
    @SerialName("stability_target_days") val stabilityTargetDays: Int = 0,
    @SerialName("security") val security: String? = null,
)

@Serializable
data class DailyGoalDto(
    @SerialName("is_achieved") val isAchieved: Boolean = false,
    @SerialName("queued_new_cards") val queuedNewCards: Int = 0,
    @SerialName("progress_percent") val progressPercent: Int = 0,
)
