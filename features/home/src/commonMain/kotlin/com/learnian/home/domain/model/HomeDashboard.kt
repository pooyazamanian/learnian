package com.learnian.home.domain.model

data class HomeDashboard(
    val userName: String,
    val streakDays: Int,
    val dueReviewsCount: Int,
    val xpPoints: Int,
    val currentLevel: Int,
    val targetLevelXp: Int,
    val userLevel: String = "سطح B1 • Intermediate",
    val newWordsCount: Int = 10,
    val dailyGoalCompleted: Int = 13,
    val dailyGoalTarget: Int = 20,
    val retentionRate: Int = 94,
    val estimatedReviewMinutes: Int = 7,
)
