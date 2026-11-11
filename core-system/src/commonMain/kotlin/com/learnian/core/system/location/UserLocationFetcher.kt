package com.learnian.core.system.location

import androidx.compose.runtime.Composable

expect class UserLocationFetcher {
    fun getCurrentLocation(
        onLocationReceived: (lat: Double, lng: Double) -> Unit,
        onError: (String) -> Unit
    )
}

@Composable
expect fun rememberUserLocationFetcher(): UserLocationFetcher
