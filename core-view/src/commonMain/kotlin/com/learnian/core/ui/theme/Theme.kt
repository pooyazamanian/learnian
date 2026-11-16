package com.learnian.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.learnian.core.datastore.model.ThemeMode
import com.learnian.core.datastore.model.UserSettings

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
)

/**
 * Extensibility contract for user or brand custom themes.
 */
data class CustomTheme(
    val id: String,
    val name: String,
    val lightColors: ClayColors,
    val darkColors: ClayColors,
)

object CustomThemeRegistry {
    private val themes = mutableMapOf<String, CustomTheme>()

    fun register(theme: CustomTheme) {
        themes[theme.id] = theme
    }

    fun get(id: String?): CustomTheme? = id?.let { themes[it] }

    fun all(): List<CustomTheme> = themes.values.toList()
}

val LocalUserSettings = staticCompositionLocalOf { UserSettings() }

object ClayTheme {
    val colors: ClayColors
        @Composable
        @ReadOnlyComposable
        get() = LocalClayColors.current

    val userSettings: UserSettings
        @Composable
        @ReadOnlyComposable
        get() = LocalUserSettings.current
}

/**
 * Root theme composable that reactively applies:
 * - Theme mode (System, Light, Dark, or Custom)
 * - Scaled typography with font selection
 * - Layout direction (RTL for Persian, LTR for English)
 */
@Composable
fun AppPlatformTheme(
    userSettings: UserSettings = UserSettings(),
    darkTheme: Boolean = when (userSettings.themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    },
    content: @Composable () -> Unit,
) {
    val customTheme = remember(userSettings.customThemeId) {
        CustomThemeRegistry.get(userSettings.customThemeId)
    }

    val clayColors = when {
        customTheme != null -> if (darkTheme) customTheme.darkColors else customTheme.lightColors
        darkTheme -> DarkClayColors
        else -> LightClayColors
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val typography = remember(userSettings.font, userSettings.fontSizeScale) {
        createAppTypography(
            font = userSettings.font,
            fontScale = userSettings.fontSizeScale.scale,
        )
    }

    val layoutDirection = if (userSettings.language.isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(
        LocalClayColors provides clayColors,
        LocalUserSettings provides userSettings,
        LocalLayoutDirection provides layoutDirection,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content,
        )
    }
}
