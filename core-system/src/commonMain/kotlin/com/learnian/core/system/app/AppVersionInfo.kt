package com.learnian.core.system.app

import androidx.compose.runtime.Composable

/**
 * Real application version metadata retrieved dynamically from the host platform.
 *
 * @param versionName The human-readable version string (e.g. "1.0", "1.0.0").
 * @param versionCode The numerical build/version code (e.g. 1, 308).
 */
data class AppVersionInfo(
    val versionName: String,
    val versionCode: Long,
)

/**
 * Remembers the real platform version metadata for the current running application.
 */
@Composable
expect fun rememberAppVersionInfo(): AppVersionInfo
