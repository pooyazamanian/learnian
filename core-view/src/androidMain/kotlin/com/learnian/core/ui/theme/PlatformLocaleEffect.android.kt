package com.learnian.core.ui.theme

import android.os.LocaleList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.learnian.core.datastore.model.AppLanguage
import java.util.Locale

@Composable
actual fun PlatformLocaleEffect(language: AppLanguage) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val applyLocale = {
        val targetLocale = Locale.forLanguageTag(language.code)
        if (Locale.getDefault().language != targetLocale.language) {
            Locale.setDefault(targetLocale)
            val localeList = LocaleList(targetLocale)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        }
    }

    applyLocale()
    SideEffect {
        applyLocale()
    }
}
