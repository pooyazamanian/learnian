package com.learnian.review.presentation.component.hub

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.segmented.ClaySegment
import com.learnian.core.ui.component.segmented.ClaySegmentedSlider
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_flag
import com.learnian.core.view.resources.review_goal_current_format
import com.learnian.core.view.resources.review_goal_option_format
import com.learnian.core.view.resources.review_goal_title
import org.jetbrains.compose.resources.stringResource

/** Daily new-words goals offered by the selector. */
private val DAILY_GOAL_OPTIONS = listOf(10, 20, 30)

/**
 * Daily new-word goal card: the current target plus a clay segmented selector.
 *
 * The goal is persisted through the shared user settings, so this card and the
 * profile learning settings always show the same number.
 */
@Composable
fun ReviewGoalCard(
    dailyGoal: Int,
    onDailyGoalChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Keep the stored value selectable even when it is not one of the presets.
    val options = (DAILY_GOAL_OPTIONS + dailyGoal).distinct().sorted()

    ClayCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = AppCorners.XL,
        padding = Spacing.Medium,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clayRestingFloat(
                            backgroundColor = ClayTheme.colors.primaryContainer,
                            cornerRadius = AppCorners.Medium,
                            shadowColor = ClayTheme.colors.primary.copy(alpha = 0.22f),
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    BaseIcon(
                        resource = Res.drawable.ic_flag,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = ClayTheme.colors.primary,
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
                ) {
                    Text(
                        text = stringResource(Res.string.review_goal_title),
                        style = AppTypography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                    )
                    Text(
                        text = stringResource(Res.string.review_goal_current_format, dailyGoal),
                        style = AppTypography.bodySmall.copy(fontSize = 11.sp),
                        color = ClayTheme.colors.textSecondary,
                    )
                }
            }

            ClaySegmentedSlider(
                segments = options.map { goal ->
                    ClaySegment(
                        value = goal,
                        label = stringResource(Res.string.review_goal_option_format, goal),
                    )
                },
                selectedValue = dailyGoal,
                onValueChange = onDailyGoalChange,
                modifier = Modifier.fillMaxWidth(),
                height = 44.dp,
                thumbColor = ClayTheme.colors.primary,
            )
        }
    }
}
