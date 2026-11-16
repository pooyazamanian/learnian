package com.learnian.core.ui.theme

import androidx.compose.runtime.Composable
import com.learnian.core.datastore.model.AppLanguage

/**
 * Platform-specific hook to synchronize device and runtime locale
 * with the user-selected [AppLanguage].
 */
@Composable
expect fun PlatformLocaleEffect(language: AppLanguage)
