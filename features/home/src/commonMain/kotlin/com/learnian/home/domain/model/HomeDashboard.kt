package com.learnian.home.domain.model

data class HomeDashboard(
    val userName: String,
    val streakDays: Int,
    val dueReviewsCount: Int,
    val xpPoints: Int,
    val currentLevel: Int,
    val targetLevelXp: Int
)
