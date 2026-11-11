package com.learnian.core.system.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

actual class GpsServiceController {
    actual fun isGpsEnabled(): Boolean = true
    actual fun requestEnableGps(onSuccess: () -> Unit, onError: (String) -> Unit) {
        onSuccess()
    }
}

@Composable
actual fun rememberGpsServiceController(): GpsServiceController {
    return remember { GpsServiceController() }
}
