package com.learnian.learning.presentation.component.hub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.button.ClayButtonVariant
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.component.progress.ClayProgressBar
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_lightning
import com.learnian.core.view.resources.ic_play
import com.learnian.core.view.resources.learning_settings_percent
import com.learnian.core.view.resources.learning_today_plan_badge
import com.learnian.core.view.resources.learning_today_plan_continue
import com.learnian.core.view.resources.learning_today_plan_desc
import com.learnian.core.view.resources.learning_today_plan_empty
import com.learnian.core.view.resources.learning_today_plan_headline
import com.learnian.core.view.resources.learning_today_plan_progress_caption
import com.learnian.core.view.resources.learning_today_plan_remaining
import com.learnian.core.view.resources.notebook
import com.learnian.learning.domain.model.TodayPlan
import org.jetbrains.compose.resources.stringResource

/**
 * Headline card of the learning hub: today's aggregated plan across active packages,
 * its completion and the primary "continue" action.
 */
@Composable
fun LearningTodayPlanCard(
    plan: TodayPlan,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = ClayTheme.colors.primaryContainer,
        cornerRadius = AppCorners.XL,
        padding = Spacing.Medium,
        shadowColor = ClayTheme.colors.primary.copy(alpha = 0.18f),
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
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Compact),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Spacing.Compact),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BaseIcon(
                            resource = Res.drawable.ic_lightning,
                            contentDescription = null,
                            modifier = Modifier.size(13.dp),
                            tint = ClayTheme.colors.primary,
                        )
                        Text(
                            text = stringResource(Res.string.learning_today_plan_badge),
                            style = AppTypography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 10.sp,
                            ),
                            color = ClayTheme.colors.onPrimaryContainer,
                        )
                    }

                    if (plan.hasActivePackage) {
                        Text(
                            text = stringResource(
                                Res.string.learning_today_plan_headline,
                                plan.activePackageQuota,
                                plan.activePackageTitle,
                            ),
                            style = AppTypography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp,
                                lineHeight = 22.sp,
                            ),
                            color = ClayTheme.colors.onPrimaryContainer,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = stringResource(Res.string.learning_today_plan_desc),
                            style = AppTypography.bodySmall.copy(fontSize = 10.sp),
                            color = ClayTheme.colors.textSecondary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    } else {
                        Text(
                            text = stringResource(Res.string.learning_today_plan_empty),
                            style = AppTypography.bodySmall.copy(fontSize = 11.sp),
                            color = ClayTheme.colors.onPrimaryContainer,
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clayRestingFloat(
                            backgroundColor = ClayTheme.colors.surface,
                            cornerRadius = AppCorners.Medium,
                            shadowColor = ClayTheme.colors.primary.copy(alpha = 0.16f),
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    BaseImage(
                        resource = Res.drawable.notebook,
                        contentDescription = null,
                        modifier = Modifier.size(34.dp),
                    )
                }
            }

            if (plan.hasActivePackage) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(Res.string.learning_settings_percent, plan.percent),
                        style = AppTypography.labelMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp,
                        ),
                        color = ClayTheme.colors.primary,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = stringResource(
                            Res.string.learning_today_plan_progress_caption,
                            plan.completedNewWords,
                            plan.totalNewWords,
                        ),
                        style = AppTypography.bodySmall.copy(fontSize = 10.sp),
                        color = ClayTheme.colors.textSecondary,
                    )
                }

                ClayProgressBar(
                    progress = plan.fraction,
                    modifier = Modifier.fillMaxWidth(),
                    trackColor = ClayTheme.colors.surface.copy(alpha = 0.85f),
                    progressColor = ClayTheme.colors.primary,
                    height = 14.dp,
                )

                ClayButton(
                    onClick = onContinue,
                    modifier = Modifier.fillMaxWidth(),
                    variant = ClayButtonVariant.Primary,
                    cornerRadius = AppCorners.Large,
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
                ) {
                    BaseIcon(
                        resource = Res.drawable.ic_play,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = ClayTheme.colors.onPrimary,
                    )
                    Spacer(Modifier.size(Spacing.Small))
                    Text(
                        text = stringResource(Res.string.learning_today_plan_continue),
                        style = AppTypography.labelMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                        ),
                        color = ClayTheme.colors.onPrimary,
                    )
                    Spacer(Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .background(
                                color = ClayTheme.colors.onPrimary.copy(alpha = 0.20f),
                                shape = RoundedCornerShape(AppCorners.Pill),
                            )
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                    ) {
                        Text(
                            text = stringResource(
                                Res.string.learning_today_plan_remaining,
                                plan.remainingNewWords,
                            ),
                            style = AppTypography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp,
                            ),
                            color = ClayTheme.colors.onPrimary,
                        )
                    }
                }
            }
        }
    }
}
