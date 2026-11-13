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
    @SerialName("target_xp") val targetLevelXp: Int? = null
)
