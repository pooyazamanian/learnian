package com.learnian.learning.presentation.mapper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.learning.domain.model.PackageCategory

/**
 * Color identity of a package surface.
 *
 * The accent follows the package category so a card keeps the same color on the hub,
 * the detail page and the settings page: general → lavender, exams → amber,
 * migration → mint. Every value is a theme token, so light/dark are covered.
 */
@Immutable
data class PackageAccent(
    val container: Color,
    val strong: Color,
    val onStrong: Color,
) {
    val shadow: Color get() = strong.copy(alpha = 0.22f)
}

@Composable
fun packageAccentOf(category: PackageCategory): PackageAccent = when (category) {
    PackageCategory.GENERAL -> PackageAccent(
        container = ClayTheme.colors.primaryContainer,
        strong = ClayTheme.colors.primary,
        onStrong = ClayTheme.colors.onPrimary,
    )

    PackageCategory.EXAMS -> PackageAccent(
        container = ClayTheme.colors.tertiaryContainer,
        strong = ClayTheme.colors.tertiary,
        onStrong = ClayTheme.colors.onTertiary,
    )

    PackageCategory.MIGRATION -> PackageAccent(
        container = ClayTheme.colors.secondaryContainer,
        strong = ClayTheme.colors.secondary,
        onStrong = ClayTheme.colors.onSecondary,
    )
}