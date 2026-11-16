package com.learnian.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Claymorphism Primary Palette (Light Clay Spec)
val ClayPrimaryLavender = Color(0xFF5949C0)
val ClaySecondaryMint = Color(0xFF006B5C)
val ClayTertiaryAmber = Color(0xFFF3BE67)
val ClayErrorPeach = Color(0xFFBA1A1A)

// Dark Clay Spec
val ClayDarkSurface = Color(0xFF1C1A29)
val ClayDarkContainer = Color(0xFF2A2739)
val ClayDarkVibrantNeon = Color(0xFF7666E3)
val ClayDarkSecondaryMint = Color(0xFF00A896)
val ClayDarkDeepTrench = Color(0xFF151320)

// Light Clay Surfaces
val ClayLightBackground = Color(0xFFF6F4FA)
val ClayLightContainer = Color(0xFFFFFFFF)
val ClayLightSunken = Color(0xFFEDE9F5)

// Standard Android Material 3 Color Definitions mapped to Clay
val md_theme_light_primary = ClayPrimaryLavender
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFE5E0FF)
val md_theme_light_onPrimaryContainer = Color(0xFF190065)
val md_theme_light_secondary = ClaySecondaryMint
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_tertiary = ClayTertiaryAmber
val md_theme_light_onTertiary = Color(0xFF1F1000)
val md_theme_light_error = ClayErrorPeach
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_background = ClayLightBackground
val md_theme_light_onBackground = Color(0xFF1C1A29)
val md_theme_light_surface = ClayLightContainer
val md_theme_light_onSurface = Color(0xFF1C1A29)

val md_theme_dark_primary = ClayDarkVibrantNeon
val md_theme_dark_onPrimary = Color(0xFFFFFFFF)
val md_theme_dark_primaryContainer = Color(0xFF4130A6)
val md_theme_dark_onPrimaryContainer = Color(0xFFE5E0FF)
val md_theme_dark_secondary = ClayDarkSecondaryMint
val md_theme_dark_onSecondary = Color(0xFFFFFFFF)
val md_theme_dark_tertiary = Color(0xFFFFD166)
val md_theme_dark_onTertiary = Color(0xFF3E2800)
val md_theme_dark_error = Color(0xFFFF5252)
val md_theme_dark_onError = Color(0xFFFFFFFF)
val md_theme_dark_background = ClayDarkSurface
val md_theme_dark_onBackground = Color(0xFFFFFFFF)
val md_theme_dark_surface = ClayDarkContainer
val md_theme_dark_onSurface = Color(0xFFFFFFFF)

// Clay Specific Theme Colors Object
@Immutable
data class ClayColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val error: Color,
    val background: Color,
    val surface: Color,
    val surfaceSunken: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val highlightTop: Color,
    val shadowOuter: Color,
    val shadowInner: Color,
    val isDark: Boolean = false,
)

val LightClayColors = ClayColors(
    primary = ClayPrimaryLavender,
    secondary = ClaySecondaryMint,
    tertiary = ClayTertiaryAmber,
    error = ClayErrorPeach,
    background = ClayLightBackground,
    surface = ClayLightContainer,
    surfaceSunken = ClayLightSunken,
    textPrimary = Color(0xFF1C1A29),
    textSecondary = Color(0xFF656275),
    highlightTop = Color(0xFFFFFFFF),
    shadowOuter = Color(0x1F3D3A4B),
    shadowInner = Color(0x14000000),
    isDark = false,
)

val DarkClayColors = ClayColors(
    primary = ClayDarkVibrantNeon,
    secondary = ClayDarkSecondaryMint,
    tertiary = Color(0xFFFFD166),
    error = Color(0xFFFF5252),
    background = ClayDarkSurface,
    surface = ClayDarkContainer,
    surfaceSunken = ClayDarkDeepTrench,
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFFA09CBD),
    highlightTop = Color(0x1AFFFFFF), // 8% white reflex for subtle elevation in dark mode
    shadowOuter = Color(0x66000000),
    shadowInner = Color(0xA6000000),
    isDark = true,
)

val LocalClayColors = staticCompositionLocalOf { LightClayColors }

object AppColors {
    val Primary = ClayPrimaryLavender
    val Background = md_theme_dark_background
    val Surface = md_theme_dark_surface

    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFF8E8E93)
    val TextTertiary = Color(0xFF636366)

    val Success = Color(0xFF34C759)
    val Error = Color(0xFFFF3B30)
    val Warning = Color(0xFFFF9500)
}
