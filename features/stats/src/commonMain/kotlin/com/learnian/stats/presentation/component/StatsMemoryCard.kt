package com.learnian.stats.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_ribbon_medal
import com.learnian.core.view.resources.stats_memory_title
import com.learnian.core.view.resources.stats_memory_week_format
import com.learnian.stats.domain.model.MemorySummary
import com.learnian.stats.presentation.mapper.label
import org.jetbrains.compose.resources.stringResource

/**
 * Hero card of the stats tab: how fast long-term memory (FSRS) is consolidating.
 *
 * The medal only decorates the card; every word of the content comes from the server
 * snapshot through [MemorySummary].
 */
@Composable
fun StatsMemoryCard(
    memory: MemorySummary,
    modifier: Modifier = Modifier,
) {
    ClayCard(
        modifier = modifier,
        padding = AppPaddings.CardPaddingComfortable,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.Small),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Compact),
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(
                                color = ClayTheme.colors.secondaryContainer,
                                shape = CircleShape,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(color = ClayTheme.colors.secondary, shape = CircleShape),
                        )
                    }

                    Text(
                        text = stringResource(Res.string.stats_memory_title),
                        style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = ClayTheme.colors.textSecondary,
                    )
                }

                ClayStatusChip(
                    text = memory.momentum.label(),
                    style = ClayChipStyle.Resting,
                    textColor = ClayTheme.colors.onSecondaryContainer,
                    backgroundColor = ClayTheme.colors.secondaryContainer,
                    textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    contentPadding = PaddingValues(
                        horizontal = Spacing.Comfortable,
                        vertical = Spacing.Compact,
                    ),
                )

                Text(
                    text = stringResource(
                        Res.string.stats_memory_week_format,
                        memory.promotedThisWeek,
                    ),
                    style = AppTypography.bodySmall,
                    color = ClayTheme.colors.textSecondary,
                )
            }

            FsrsMedal()
        }
    }
}

/** Golden FSRS medallion; purely decorative, so it carries no content description. */
@Composable
private fun FsrsMedal(modifier: Modifier = Modifier) {
    val gold = ClayTheme.colors.tertiary

    Box(
        modifier = modifier
            .size(FSRS_MEDAL_SIZE)
            .background(color = gold.copy(alpha = 0.12f), shape = CircleShape)
            .border(width = 2.dp, color = gold.copy(alpha = 0.35f), shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        BaseIcon(
            resource = Res.drawable.ic_ribbon_medal,
            contentDescription = null,
            modifier = Modifier.size(38.dp),
            tint = gold,
        )
    }
}

private val FSRS_MEDAL_SIZE = 76.dp
