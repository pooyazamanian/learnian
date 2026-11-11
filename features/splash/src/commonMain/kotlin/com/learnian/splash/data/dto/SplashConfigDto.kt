package com.learnian.splash.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SplashConfigDto(
    @SerialName("app_version") val appVersion: String? = null,
    @SerialName("min_version") val minRequiredVersion: String? = null,
    @SerialName("is_maintenance") val isMaintenanceMode: Boolean? = null
)
