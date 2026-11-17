package com.learnian.home.data.mapper

import com.learnian.home.data.dto.HomeDashboardDto
import com.learnian.home.domain.model.HomeDashboard

fun HomeDashboardDto.toDomain(): HomeDashboard {
    return HomeDashboard(
        userName = userName ?: "شایان",
        streakDays = streakDays ?: 12,
        dueReviewsCount = dueReviewsCount ?: 18,
        xpPoints = xpPoints ?: 740,
        currentLevel = currentLevel ?: 29,
        targetLevelXp = targetLevelXp ?: 1000,
        userLevel = userLevel ?: "سطح B1 • Intermediate",
        newWordsCount = newWordsCount ?: 10,
        dailyGoalCompleted = dailyGoalCompleted ?: 13,
        dailyGoalTarget = dailyGoalTarget ?: 20,
        retentionRate = retentionRate ?: 94,
        estimatedReviewMinutes = estimatedReviewMinutes ?: 7,
    )
}
