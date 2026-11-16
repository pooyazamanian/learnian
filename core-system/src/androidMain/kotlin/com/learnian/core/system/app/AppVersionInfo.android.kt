package com.learnian.core.system.app

import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberAppVersionInfo(): AppVersionInfo {
    val context = LocalContext.current
    return remember(context) {
        try {
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.PackageInfoFlags.of(0L),
                )
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(context.packageName, 0)
            }

            val versionName = packageInfo.versionName ?: "1.0.0"
            val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode.toLong()
            }

            AppVersionInfo(
                versionName = versionName,
                versionCode = versionCode,
            )
        } catch (_: Exception) {
            AppVersionInfo(
                versionName = "1.0.0",
                versionCode = 1L,
            )
        }
    }
}
