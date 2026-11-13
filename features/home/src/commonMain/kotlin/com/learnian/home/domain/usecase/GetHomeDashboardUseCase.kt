package com.learnian.home.domain.usecase

import com.learnian.home.domain.model.HomeDashboard
import com.learnian.home.domain.repository.HomeRepository

class GetHomeDashboardUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(): Result<HomeDashboard> {
        return runCatching {
            repository.getHomeDashboard()
        }
    }
}
