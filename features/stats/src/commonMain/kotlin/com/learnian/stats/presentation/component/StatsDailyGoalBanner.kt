package com.learnian.stats.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_ribbon_medal
import com.learnian.core.view.resources.ic_target
import com.learnian.core.view.resources.stats_goal_achieved_desc_format
import com.learnian.core.view.resources.stats_goal_achieved_title
import com.learnian.core.view.resources.stats_goal_progress_desc_format
import com.learnian.core.view.resources.stats_goal_title
import com.learnian.stats.domain.model.DailyGoalSummary
import org.jetbrains.compose.resources.stringResource

/**
 * Today's goal banner.
 *
 * Goes from "in progress" to the celebration state purely from [DailyGoalSummary], so the
 * server decides when the goal is done and how many cards are queued for tomorrow.
 */
@Composable
fun StatsDailyGoalBanner(
    goal: DailyGoalSummary,
    modifier: Modifier = Modifier,
) {
    ClayCard(
        modifier = modifier,
        backgroundColor = ClayTheme.colors.primary,
        padding = AppPaddings.CardPaddingComfortable,
        shadowColor = ClayTheme.colors.primary.copy(alpha = 0.40f),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.Medium),
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        color = ClayTheme.colors.onPrimary.copy(alpha = 0.16f),
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                BaseIcon(
                    resource = if (goal.isAchieved) {
                        Res.drawable.ic_ribbon_medal
                    } else {
                        Res.drawable.ic_target
                    },
                    contentDescription = null,
                    modifier = Modifier.size(26.dp),
                    tint = ClayTheme.colors.onPrimary,
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
            ) {
                Text(
                    text = stringResource(
                        if (goal.isAchieved) {
                            Res.string.stats_goal_achieved_title
                        } else {
                            Res.string.stats_goal_title
                        },
                    ),
                    style = AppTypography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    ),
                    color = ClayTheme.colors.onPrimary,
                )

                Text(
                    text = if (goal.isAchieved) {
                        stringResource(
                            Res.string.stats_goal_achieved_desc_format,
                            goal.queuedNewCards,
                        )
                    } else {
                        stringResource(
                            Res.string.stats_goal_progress_desc_format,
                            goal.progressPercent,
                        )
                    },
                    style = AppTypography.bodySmall,
                    color = ClayTheme.colors.onPrimary.copy(alpha = 0.86f),
                )
            }
        }
    }
}
