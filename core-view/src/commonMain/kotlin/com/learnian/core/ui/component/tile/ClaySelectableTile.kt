package com.learnian.core.ui.component.tile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing

/**
 * Compact selectable clay tile: a title with an optional caption, used in option rows
 * (daily word presets, quota shares, plan choices) where a full [ClayRadioCard] row
 * would not fit.
 *
 * The selected state is carried by the brand color plus a tinted shadow, so the choice
 * stays readable in both themes without any dark-mode branch.
 */
@Composable
fun ClaySelectableTile(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
) {
    ClayCard(
        modifier = modifier,
        backgroundColor = if (selected) ClayTheme.colors.primary else ClayTheme.colors.surface,
        cornerRadius = AppCorners.Medium,
        padding = Spacing.Small,
        shadowColor = if (selected) {
            ClayTheme.colors.primary.copy(alpha = 0.30f)
        } else {
            ClayTheme.colors.shadowOuter
        },
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = AppTypography.labelMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                ),
                color = if (selected) ClayTheme.colors.onPrimary else ClayTheme.colors.textPrimary,
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    textAlign = TextAlign.Center,
                    style = AppTypography.bodySmall.copy(fontSize = 9.sp),
                    color = if (selected) {
                        ClayTheme.colors.onPrimary.copy(alpha = 0.78f)
                    } else {
                        ClayTheme.colors.textSecondary
                    },
                )
            }
        }
    }
}