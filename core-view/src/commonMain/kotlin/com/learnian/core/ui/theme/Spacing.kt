package com.learnian.core.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Spacing {
    val None: Dp = 0.dp
    val ExtraSmall: Dp = 4.dp
    val Small: Dp = 8.dp
    val Medium: Dp = 16.dp
    val Large: Dp = 24.dp
    val ExtraLarge: Dp = 32.dp
    val Huge: Dp = 48.dp
}

object AppPaddings {
    val ScreenHorizontal: Dp = 16.dp
    val ScreenVertical: Dp = 16.dp
    val CardPadding: Dp = 16.dp
    val ItemPadding: Dp = 12.dp
    val TopBarPadding: Dp = 16.dp
}

/**
 * Ergonomic Radius Matrix (No Sharp Edges) - 8pt Grid System
 */
object AppCorners {
    val XS: Dp = 8.dp        // Icon caps & micro tags
    val Small: Dp = 12.dp     // Tag padding & badges
    val Medium: Dp = 16.dp    // Small card padding & radius
    val Large: Dp = 20.dp     // Primary action buttons (clay-btn)
    val XL: Dp = 24.dp        // Flashcard radius (clay-card)
    val XXL: Dp = 32.dp       // Modal radius (clay-modal)
    val Pill: Dp = 9999.dp    // Full rounded tags & pill chips
}
