package com.learnian.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.learnian.core.datastore.model.AppLanguage
import platform.Foundation.NSUserDefaults

@Composable
actual fun PlatformLocaleEffect(language: AppLanguage) {
    val applyLocale = {
        NSUserDefaults.standardUserDefaults.setObject(listOf(language.code), "AppleLanguages")
        NSUserDefaults.standardUserDefaults.synchronize()
    }

    applyLocale()
    SideEffect {
        applyLocale()
    }
}
