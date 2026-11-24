package com.learnian.stats.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.stats.ClayStatCard
import com.learnian.core.ui.component.stats.ClayStatCardVariant
import com.learnian.core.ui.component.stats.ClayStatsRow
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_bookmark
import com.learnian.core.view.resources.ic_streak_flame
import com.learnian.core.view.resources.ic_target
import com.learnian.core.view.resources.ic_update_check
import com.learnian.core.view.resources.stats_accuracy_met_format
import com.learnian.core.view.resources.stats_accuracy_target_format
import com.learnian.core.view.resources.stats_accuracy_title
import com.learnian.core.view.resources.stats_days_unit
import com.learnian.core.view.resources.stats_mastered_percent_format
import com.learnian.core.view.resources.stats_mastered_title
import com.learnian.core.view.resources.stats_percent_unit
import com.learnian.core.view.resources.stats_streak_record_format
import com.learnian.core.view.resources.stats_streak_title
import com.learnian.core.view.resources.stats_words_added_format
import com.learnian.core.view.resources.stats_words_title
import com.learnian.core.view.resources.stats_words_unit
import com.learnian.stats.domain.model.AccuracySummary
import com.learnian.stats.domain.model.StreakSummary
import com.learnian.stats.domain.model.VocabularySummary
import org.jetbrains.compose.resources.stringResource

/**
 * The four metric cards of the stats tab.
 *
 * Numbers, units and targets all come from the snapshot; the screen only groups them
 * into two equal-height rows of [ClayStatCard]s.
 */
@Composable
fun StatsMetricsSection(
    vocabulary: VocabularySummary,
    accuracy: AccuracySummary,
    streak: StreakSummary,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Standard),
    ) {
        ClayStatsRow(horizontalSpacing = Spacing.Standard) {
            ClayStatCard(
                title = stringResource(Res.string.stats_words_title),
                value = vocabulary.totalWords.toString(),
                valueSuffix = stringResource(Res.string.stats_words_unit),
                subtitle = stringResource(
                    Res.string.stats_words_added_format,
                    vocabulary.addedThisWeek,
                ),
                icon = {
                    BaseIcon(
                        resource = Res.drawable.ic_bookmark,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = ClayTheme.colors.onPrimaryContainer,
                    )
                },
                variant = ClayStatCardVariant.Purple,
                modifier = Modifier.weight(1f),
            )

            ClayStatCard(
                title = stringResource(Res.string.stats_mastered_title),
                value = vocabulary.masteredWords.toString(),
                valueSuffix = stringResource(Res.string.stats_words_unit),
                subtitle = stringResource(
                    Res.string.stats_mastered_percent_format,
                    vocabulary.masteredPercent,
                ),
                icon = {
                    BaseIcon(
                        resource = Res.drawable.ic_update_check,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = ClayTheme.colors.secondary,
                    )
                },
                variant = ClayStatCardVariant.Mint,
                modifier = Modifier.weight(1f),
            )
        }

        ClayStatsRow(horizontalSpacing = Spacing.Standard) {
            ClayStatCard(
                title = stringResource(Res.string.stats_accuracy_title),
                value = accuracy.correctPercent.toString(),
                valueSuffix = stringResource(Res.string.stats_percent_unit),
                subtitle = stringResource(
                    if (accuracy.isTargetMet) {
                        Res.string.stats_accuracy_met_format
                    } else {
                        Res.string.stats_accuracy_target_format
                    },
                    accuracy.targetPercent,
                ),
                icon = {
                    BaseIcon(
                        resource = Res.drawable.ic_target,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = ClayTheme.colors.onPrimaryContainer,
                    )
                },
                variant = ClayStatCardVariant.Purple,
                modifier = Modifier.weight(1f),
            )

            ClayStatCard(
                title = stringResource(Res.string.stats_streak_title),
                value = streak.currentDays.toString(),
                valueSuffix = stringResource(Res.string.stats_days_unit),
                subtitle = stringResource(
                    Res.string.stats_streak_record_format,
                    streak.recordDays,
                ),
                icon = {
                    BaseIcon(
                        resource = Res.drawable.ic_streak_flame,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = ClayTheme.colors.tertiary,
                    )
                },
                variant = ClayStatCardVariant.Amber,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
