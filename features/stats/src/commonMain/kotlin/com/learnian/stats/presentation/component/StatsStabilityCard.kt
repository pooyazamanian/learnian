package com.learnian.stats.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.progress.ClayProgressBar
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.stats_days_format
import com.learnian.core.view.resources.stats_percent_value_format
import com.learnian.core.view.resources.stats_stability_r_label
import com.learnian.core.view.resources.stats_stability_s_label
import com.learnian.core.view.resources.stats_stability_title
import com.learnian.stats.domain.model.MemorySecurity
import com.learnian.stats.domain.model.StabilitySummary
import com.learnian.stats.presentation.mapper.label
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

/**
 * Memory stability card: tomorrow's retrieval chance (R) and the stability horizon (S).
 *
 * Both bars are plain [ClayProgressBar]s; the target of S comes from the snapshot, so a
 * longer horizon does not require a UI change.
 */
@Composable
fun StatsStabilityCard(
    stability: StabilitySummary,
    modifier: Modifier = Modifier,
) {
    val badgeBackground = when (stability.security) {
        MemorySecurity.SECURE -> ClayTheme.colors.secondaryContainer
        MemorySecurity.MODERATE -> ClayTheme.colors.tertiaryContainer
        MemorySecurity.AT_RISK -> ClayTheme.colors.errorContainer
    }
    val badgeContent = when (stability.security) {
        MemorySecurity.SECURE -> ClayTheme.colors.onSecondaryContainer
        MemorySecurity.MODERATE -> ClayTheme.colors.onTertiaryContainer
        MemorySecurity.AT_RISK -> ClayTheme.colors.onErrorContainer
    }

    ClayCard(
        modifier = modifier,
        padding = AppPaddings.CardPaddingComfortable,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.Medium)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            ) {
                Text(
                    text = stringResource(Res.string.stats_stability_title),
                    modifier = Modifier.weight(1f),
                    style = AppTypography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    ),
                    color = ClayTheme.colors.textPrimary,
                )

                ClayStatusChip(
                    text = stability.security.label(),
                    textColor = badgeContent,
                    backgroundColor = badgeBackground,
                    textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    contentPadding = PaddingValues(
                        horizontal = Spacing.Small,
                        vertical = Spacing.Compact,
                    ),
                )
            }

            StabilityRow(
                label = stringResource(Res.string.stats_stability_r_label),
                value = stringResource(
                    Res.string.stats_percent_value_format,
                    formatDecimal(stability.retrievabilityPercent),
                ),
                progress = (stability.retrievabilityPercent / FULL_PERCENT).coerceIn(0f, 1f),
                color = ClayTheme.colors.primary,
            )

            StabilityRow(
                label = stringResource(Res.string.stats_stability_s_label),
                value = stringResource(
                    Res.string.stats_days_format,
                    formatDecimal(stability.stabilityDays),
                ),
                progress = stability.stabilityProgress,
                color = ClayTheme.colors.secondary,
            )
        }
    }
}

@Composable
private fun StabilityRow(
    label: String,
    value: String,
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Compact),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                style = AppTypography.labelSmall,
                color = ClayTheme.colors.textSecondary,
                maxLines = 2,
            )

            Text(
                text = value,
                style = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = ClayTheme.colors.textPrimary,
                maxLines = 1,
            )
        }

        ClayProgressBar(
            progress = progress,
            height = 10.dp,
            progressColor = color,
        )
    }
}

/** Formats 93.4f as "93.4" but 30f as "30". */
private fun formatDecimal(value: Float): String {
    val rounded = (value * 10f).roundToInt() / 10f
    return if (rounded % 1f == 0f) rounded.toInt().toString() else rounded.toString()
}

private const val FULL_PERCENT = 100f
