package com.learnian.core.system.location

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

actual class UserLocationFetcher(private val context: Context) {

    @SuppressLint("MissingPermission")
    actual fun getCurrentLocation(
        onLocationReceived: (lat: Double, lng: Double) -> Unit,
        onError: (String) -> Unit
    ) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val cancellationTokenSource = CancellationTokenSource()

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            if (location != null) {
                onLocationReceived(location.latitude, location.longitude)
            } else {
                fusedLocationClient.lastLocation.addOnSuccessListener { lastLoc ->
                    if (lastLoc != null) {
                        onLocationReceived(lastLoc.latitude, lastLoc.longitude)
                    } else {
                        onError("Unable to retrieve current location")
                    }
                }.addOnFailureListener { e ->
                    onError(e.message ?: "Failed to retrieve last location")
                }
            }
        }.addOnFailureListener { exception ->
            onError(exception.message ?: "Failed to fetch current location")
        }
    }
}

@Composable
actual fun rememberUserLocationFetcher(): UserLocationFetcher {
    val context = LocalContext.current
    return remember(context) { UserLocationFetcher(context) }
}
