package com.learnian.splash.data.mapper

import com.learnian.splash.data.dto.SplashConfigDto
import com.learnian.splash.domain.model.SplashConfig

fun SplashConfigDto.toDomain(): SplashConfig {
    return SplashConfig(
        appVersion = appVersion ?: "1.0.0",
        minRequiredVersion = minRequiredVersion ?: "1.0.0",
        isMaintenanceMode = isMaintenanceMode ?: false
    )
}
