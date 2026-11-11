package com.learnian.core.ui.theme

import androidx.compose.ui.graphics.Color

// Standard Android Material 3 Color Definitions
val md_theme_light_primary = Color(0xFF0061A4)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFD1E4FF)
val md_theme_light_onPrimaryContainer = Color(0xFF001D36)
val md_theme_light_secondary = Color(0xFF535F70)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_background = Color(0xFFF8FDFF)
val md_theme_light_onBackground = Color(0xFF001F25)
val md_theme_light_surface = Color(0xFFF8FDFF)
val md_theme_light_onSurface = Color(0xFF001F25)

val md_theme_dark_primary = Color(0xFF007AFF)
val md_theme_dark_onPrimary = Color(0xFFFFFFFF)
val md_theme_dark_primaryContainer = Color(0xFF00497D)
val md_theme_dark_onPrimaryContainer = Color(0xFFD1E4FF)
val md_theme_dark_secondary = Color(0xFF8E8E93)
val md_theme_dark_onSecondary = Color(0xFFFFFFFF)
val md_theme_dark_background = Color(0xFF000000)
val md_theme_dark_onBackground = Color(0xFFFFFFFF)
val md_theme_dark_surface = Color(0xFF1C1C1E)
val md_theme_dark_onSurface = Color(0xFFFFFFFF)

// Glassmorphism Shared Theme Colors
object AppColors {
    val Primary = md_theme_dark_primary
    val Background = md_theme_dark_background
    val Surface = md_theme_dark_surface

    val GlassSurface = Color(0x1AFFFFFF)
    val GlassSurfacePressed = Color(0x33FFFFFF)
    val GlassBorder = Color(0x26FFFFFF)

    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFF8E8E93)
    val TextTertiary = Color(0xFF636366)

    val Success = Color(0xFF34C759)
    val Error = Color(0xFFFF3B30)
    val Warning = Color(0xFFFF9500)
}
