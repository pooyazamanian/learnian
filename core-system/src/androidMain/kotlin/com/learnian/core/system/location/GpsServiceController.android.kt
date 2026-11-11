package com.learnian.core.system.location

import android.app.Activity
import android.content.Context
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority

actual class GpsServiceController(
    private val context: Context,
    private val launchGpsResolution: (ResolvableApiException, onSuccess: () -> Unit) -> Unit
) {
    actual fun isGpsEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true ||
                locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
    }

    actual fun requestEnableGps(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (isGpsEnabled()) {
            onSuccess()
            return
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10000L
        ).build()

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true)

        val client = LocationServices.getSettingsClient(context)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            onSuccess()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    launchGpsResolution(exception, onSuccess)
                } catch (e: Exception) {
                    onError(e.message ?: "Failed to prompt GPS resolution")
                }
            } else {
                onError(exception.message ?: "GPS service unavailable")
            }
        }
    }
}

@Composable
actual fun rememberGpsServiceController(): GpsServiceController {
    val context = LocalContext.current
    var pendingOnSuccessCallback by remember { mutableStateOf<(() -> Unit)?>(null) }

    val resolutionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            pendingOnSuccessCallback?.invoke()
        }
    }

    return remember(context, resolutionLauncher) {
        GpsServiceController(
            context = context,
            launchGpsResolution = { resolvableException, onSuccess ->
                pendingOnSuccessCallback = onSuccess
                val intentSenderRequest = IntentSenderRequest.Builder(resolvableException.resolution).build()
                resolutionLauncher.launch(intentSenderRequest)
            }
        )
    }
}
