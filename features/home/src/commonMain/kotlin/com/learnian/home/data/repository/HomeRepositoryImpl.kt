package com.learnian.home.data.repository

import com.learnian.base.ResourceKey
import com.learnian.home.data.dto.HomeDashboardDto
import com.learnian.home.data.mapper.toDomain
import com.learnian.home.domain.model.HomeDashboard
import com.learnian.home.domain.repository.HomeRepository

class HomeRepositoryImpl : HomeRepository {
    override val resourceKey: ResourceKey = ResourceKey("home_dashboard")

    override suspend fun getHomeDashboard(): HomeDashboard {
        val dto = HomeDashboardDto(
            userName = "شایان",
            streakDays = 12,
            dueReviewsCount = 18,
            xpPoints = 740,
            currentLevel = 29,
            targetLevelXp = 1000,
            userLevel = "سطح B1 • Intermediate",
            newWordsCount = 10,
            dailyGoalCompleted = 13,
            dailyGoalTarget = 20,
            retentionRate = 94,
            estimatedReviewMinutes = 7,
        )
        return dto.toDomain()
    }
}
