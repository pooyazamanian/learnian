package com.learnian.stats.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ChipIconPosition
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.stats.ClayBarChart
import com.learnian.core.ui.component.stats.ClayBarChartBar
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_clock
import com.learnian.core.view.resources.stats_weekly_average_format
import com.learnian.core.view.resources.stats_weekly_title
import com.learnian.core.view.resources.stats_weekly_total_format
import com.learnian.stats.domain.model.WeeklyActivitySummary
import com.learnian.stats.presentation.mapper.label
import org.jetbrains.compose.resources.stringResource

/**
 * Weekly review activity: total time chip plus one bar per day of the current week.
 *
 * The active day is highlighted by [ClayBarChart] itself; today's label is localized by
 * the presentation mapper, so no day name is hardcoded here.
 */
@Composable
fun StatsWeeklyActivityCard(
    activity: WeeklyActivitySummary,
    modifier: Modifier = Modifier,
) {
    ClayCard(
        modifier = modifier,
        padding = AppPaddings.CardPaddingComfortable,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.Medium)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
                ) {
                    Text(
                        text = stringResource(Res.string.stats_weekly_title),
                        style = AppTypography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                    )

                    Text(
                        text = stringResource(
                            Res.string.stats_weekly_average_format,
                            activity.averageMinutes,
                        ),
                        style = AppTypography.bodySmall,
                        color = ClayTheme.colors.textSecondary,
                    )
                }

                ClayStatusChip(
                    text = stringResource(
                        Res.string.stats_weekly_total_format,
                        activity.totalHours,
                        activity.remainingMinutes,
                    ),
                    icon = {
                        BaseIcon(
                            resource = Res.drawable.ic_clock,
                            contentDescription = null,
                            modifier = Modifier.size(13.dp),
                            tint = ClayTheme.colors.primary,
                        )
                    },
                    iconPosition = ChipIconPosition.Start,
                    textColor = ClayTheme.colors.primary,
                    backgroundColor = ClayTheme.colors.primaryContainer,
                    textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    contentPadding = PaddingValues(
                        horizontal = Spacing.Comfortable,
                        vertical = Spacing.Compact,
                    ),
                )
            }

            ClayBarChart(
                bars = activity.days.map { day ->
                    ClayBarChartBar(
                        label = day.label(),
                        value = day.minutes.toFloat(),
                        isHighlighted = day.isToday,
                    )
                },
                trackHeight = 132.dp,
            )
        }
    }
}
