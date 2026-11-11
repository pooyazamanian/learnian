package com.learnian.splash.data.repository

import com.learnian.base.ResourceKey
import com.learnian.splash.data.dto.SplashConfigDto
import com.learnian.splash.data.mapper.toDomain
import com.learnian.splash.domain.model.SplashConfig
import com.learnian.splash.domain.repository.SplashRepository

class SplashRepositoryImpl : SplashRepository {
    override val resourceKey: ResourceKey = ResourceKey("splash_config")

    override suspend fun getSplashConfig(): SplashConfig {
        val dto = SplashConfigDto(
            appVersion = "1.0.0",
            minRequiredVersion = "1.0.0",
            isMaintenanceMode = false
        )
        return dto.toDomain()
    }
}
