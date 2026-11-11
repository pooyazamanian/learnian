package com.learnian.core.system.location

import androidx.compose.runtime.Composable

expect class GpsServiceController {
    fun isGpsEnabled(): Boolean
    fun requestEnableGps(onSuccess: () -> Unit, onError: (String) -> Unit)
}

@Composable
expect fun rememberGpsServiceController(): GpsServiceController
