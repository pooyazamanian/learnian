package com.learnian.home.data.mapper

import com.learnian.home.data.dto.HomeDashboardDto
import com.learnian.home.domain.model.HomeDashboard

fun HomeDashboardDto.toDomain(): HomeDashboard {
    return HomeDashboard(
        userName = userName ?: "کاربر لرنین",
        streakDays = streakDays ?: 5,
        dueReviewsCount = dueReviewsCount ?: 12,
        xpPoints = xpPoints ?: 740,
        currentLevel = currentLevel ?: 29,
        targetLevelXp = targetLevelXp ?: 1000
    )
}
