package com.learnian.core.system.location

import androidx.compose.runtime.Composable

expect class LocationPermissionController {
    fun hasLocationPermission(): Boolean
    fun requestLocationPermission()
}

@Composable
expect fun rememberLocationPermissionController(
    onPermissionResult: (Boolean) -> Unit
): LocationPermissionController
