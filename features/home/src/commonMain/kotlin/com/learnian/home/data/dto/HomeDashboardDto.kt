package com.learnian.home.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeDashboardDto(
    @SerialName("user_name") val userName: String? = null,
    @SerialName("streak_days") val streakDays: Int? = null,
    @SerialName("due_reviews") val dueReviewsCount: Int? = null,
    @SerialName("xp_points") val xpPoints: Int? = null,
    @SerialName("current_level") val currentLevel: Int? = null,
    @SerialName("target_xp") val targetLevelXp: Int? = null,
    @SerialName("user_level") val userLevel: String? = null,
    @SerialName("new_words_count") val newWordsCount: Int? = null,
    @SerialName("daily_goal_completed") val dailyGoalCompleted: Int? = null,
    @SerialName("daily_goal_target") val dailyGoalTarget: Int? = null,
    @SerialName("retention_rate") val retentionRate: Int? = null,
    @SerialName("estimated_review_minutes") val estimatedReviewMinutes: Int? = null,
)
