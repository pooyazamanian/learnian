package com.learnian.core.system.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSBundle

@Composable
actual fun rememberAppVersionInfo(): AppVersionInfo {
    return remember {
        val infoDictionary = NSBundle.mainBundle.infoDictionary
        val versionName = (infoDictionary?.get("CFBundleShortVersionString") as? String) ?: "1.0.0"
        val buildNumber = (infoDictionary?.get("CFBundleVersion") as? String)?.toLongOrNull() ?: 1L
        AppVersionInfo(
            versionName = versionName,
            versionCode = buildNumber,
        )
    }
}
