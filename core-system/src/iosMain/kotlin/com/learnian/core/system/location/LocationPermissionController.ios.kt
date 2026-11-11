package com.learnian.core.system.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

actual class LocationPermissionController {
    actual fun hasLocationPermission(): Boolean = true
    actual fun requestLocationPermission() {}
}

@Composable
actual fun rememberLocationPermissionController(
    onPermissionResult: (Boolean) -> Unit
): LocationPermissionController {
    return remember { LocationPermissionController() }
}
