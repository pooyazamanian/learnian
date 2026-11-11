package com.learnian.splash.domain.usecase

import com.learnian.splash.domain.model.SplashConfig
import com.learnian.splash.domain.repository.SplashRepository

class GetSplashConfigUseCase(
    private val repository: SplashRepository
) {
    suspend operator fun invoke(): Result<SplashConfig> {
        return runCatching {
            repository.getSplashConfig()
        }
    }
}
