package com.learnian.stats.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.stats.ClayDonutChart
import com.learnian.core.ui.component.stats.ClayDonutSegment
import com.learnian.core.ui.modifiers.clayPillRaised
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.amar
import com.learnian.core.view.resources.stats_mastery_count_format
import com.learnian.core.view.resources.stats_mastery_subtitle
import com.learnian.core.view.resources.stats_mastery_title
import com.learnian.core.view.resources.stats_mastery_total_label
import com.learnian.core.view.resources.stats_percent_value_format
import com.learnian.stats.domain.model.MasterySegment
import com.learnian.stats.domain.model.MasterySummary
import com.learnian.stats.presentation.mapper.color
import com.learnian.stats.presentation.mapper.label
import org.jetbrains.compose.resources.stringResource

/**
 * Vocabulary split by FSRS mastery: a clay donut with the total on a raised medallion and
 * one embossed clay row per bucket. Slice, row and badge colors all come from the same
 * semantic mapper, so they can never drift apart.
 */
@Composable
fun StatsMasteryCard(
    mastery: MasterySummary,
    modifier: Modifier = Modifier,
) {
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
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
                ) {
                    Text(
                        text = stringResource(Res.string.stats_mastery_title),
                        style = AppTypography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Text(
                        text = stringResource(Res.string.stats_mastery_subtitle),
                        style = AppTypography.bodySmall,
                        color = ClayTheme.colors.textSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                ClayStatusChip(
                    style = ClayChipStyle.Embossed,
                    backgroundColor = ClayTheme.colors.primaryContainer,
                    shadowColor = ClayTheme.colors.primary.copy(alpha = 0.28f),
                    contentPadding = PaddingValues(Spacing.Small),
                ) {
                    BaseIcon(
                        resource = Res.drawable.amar,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = ClayTheme.colors.primary,
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                ClayDonutChart(
                    segments = mastery.segments.map { segment ->
                        ClayDonutSegment(
                            value = segment.count.toFloat(),
                            color = segment.tier.color(),
                        )
                    },
                    modifier = Modifier.size(214.dp),
                    strokeWidth = 26.dp,
                    gapDegrees = 8f,
                    trackColor = ClayTheme.colors.surfaceSunken,
                ) {
                    ClayStatusChip(
                        style = ClayChipStyle.Embossed,
                        backgroundColor = ClayTheme.colors.surface,
                        shadowColor = ClayTheme.colors.shadowOuter,
                        contentPadding = PaddingValues(
                            horizontal = Spacing.Section,
                            vertical = Spacing.Comfortable,
                        ),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
                        ) {
                            Text(
                                text = mastery.totalWords.toString(),
                                style = AppTypography.headlineSmall.copy(
                                    fontWeight = FontWeight.Black,
                                ),
                                color = ClayTheme.colors.textPrimary,
                            )

                            Text(
                                text = stringResource(Res.string.stats_mastery_total_label),
                                style = AppTypography.labelSmall,
                                color = ClayTheme.colors.textSecondary,
                            )
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.Small)) {
                mastery.segments.forEach { segment ->
                    MasteryLegendRow(
                        segment = segment,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

/** One embossed legend row: clay pill, bucket name, count and percent badge. */
@Composable
private fun MasteryLegendRow(
    segment: MasterySegment,
    modifier: Modifier = Modifier,
) {
    val accent = segment.tier.color()
    val rowBackground = lerp(ClayTheme.colors.surface, accent, 0.12f)
    val badgeBackground = lerp(rowBackground, accent, 0.30f)

    Row(
        modifier = modifier
            .clayPillRaised(
                backgroundColor = rowBackground,
                cornerRadius = AppCorners.Pill,
                shadowColor = accent.copy(alpha = 0.20f),
                highlightColor = Color.White.copy(alpha = 0.65f),
            )
            .padding(
                horizontal = Spacing.Comfortable,
                vertical = Spacing.Small,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color = accent, shape = CircleShape),
        )

        Text(
            text = segment.tier.label(),
            modifier = Modifier.weight(1f),
            style = AppTypography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = ClayTheme.colors.textPrimary,
            maxLines = 1,
        )

        Text(
            text = stringResource(Res.string.stats_mastery_count_format, segment.count),
            style = AppTypography.labelSmall,
            color = ClayTheme.colors.textSecondary,
            maxLines = 1,
        )

        ClayStatusChip(
            text = stringResource(Res.string.stats_percent_value_format, segment.percent),
            style = ClayChipStyle.Embossed,
            backgroundColor = badgeBackground,
            shadowColor = accent.copy(alpha = 0.28f),
            textColor = accent,
            textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
            contentPadding = PaddingValues(
                horizontal = Spacing.Small,
                vertical = 3.dp,
            ),
        )
    }
}
