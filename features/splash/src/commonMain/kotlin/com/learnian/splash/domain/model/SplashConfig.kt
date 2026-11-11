package com.learnian.splash.domain.model

data class SplashConfig(
    val appVersion: String,
    val minRequiredVersion: String,
    val isMaintenanceMode: Boolean
)
