package com.learnian.core.system.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

actual class UserLocationFetcher {
    actual fun getCurrentLocation(
        onLocationReceived: (lat: Double, lng: Double) -> Unit,
        onError: (String) -> Unit
    ) {
        onLocationReceived(35.6892, 51.3890)
    }
}

@Composable
actual fun rememberUserLocationFetcher(): UserLocationFetcher {
    return remember { UserLocationFetcher() }
}
