package com.learnian.learning.presentation.component.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.button.ClayButtonVariant
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.segmented.ClaySegment
import com.learnian.core.ui.component.segmented.ClaySegmentedSlider
import com.learnian.core.ui.component.tile.ClaySelectableTile
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_brain_ai
import com.learnian.core.view.resources.ic_check
import com.learnian.core.view.resources.ic_layers
import com.learnian.core.view.resources.ic_plus
import com.learnian.core.view.resources.ic_settings
import com.learnian.core.view.resources.learning_details_enroll
import com.learnian.core.view.resources.learning_details_enrolled
import com.learnian.core.view.resources.learning_details_enrolled_action
import com.learnian.core.view.resources.learning_details_plan_days
import com.learnian.core.view.resources.learning_details_plan_mode_label
import com.learnian.core.view.resources.learning_details_plan_subtitle
import com.learnian.core.view.resources.learning_details_plan_title
import com.learnian.core.view.resources.learning_details_plan_words_label
import com.learnian.core.view.resources.learning_details_plan_words_option
import com.learnian.core.view.resources.learning_details_price_free
import com.learnian.learning.domain.model.LearningPackage
import com.learnian.learning.domain.model.PracticeMode
import com.learnian.learning.presentation.mapper.descriptionRes
import com.learnian.learning.presentation.mapper.titleRes
import org.jetbrains.compose.resources.stringResource

/** Daily new-word presets offered before enrollment. */
private val QUOTA_PRESETS = listOf(10, 20, 30)

/** Enroll-time plan card: daily new words and the core practice mode. */
@Composable
fun PackagePlanCard(
    packageInfo: LearningPackage,
    selectedQuota: Int,
    selectedMode: PracticeMode,
    onQuotaSelected: (Int) -> Unit,
    onModeSelected: (PracticeMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    val quotaOptions = (QUOTA_PRESETS + selectedQuota).distinct().sorted()

    ClayCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = AppCorners.XL,
        padding = Spacing.Medium,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
        ) {
            Text(
                text = stringResource(Res.string.learning_details_plan_title),
                style = AppTypography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                ),
                color = ClayTheme.colors.textPrimary,
            )
            Text(
                text = stringResource(Res.string.learning_details_plan_subtitle),
                style = AppTypography.bodySmall.copy(fontSize = 9.5.sp),
                color = ClayTheme.colors.textSecondary,
            )

            PlanLabel(text = stringResource(Res.string.learning_details_plan_words_label))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            ) {
                quotaOptions.forEach { quota ->
                    ClaySelectableTile(
                        title = stringResource(Res.string.learning_details_plan_words_option, quota),
                        subtitle = stringResource(
                            Res.string.learning_details_plan_days,
                            packageInfo.estimatedDays(quota),
                        ),
                        selected = quota == selectedQuota,
                        onClick = { onQuotaSelected(quota) },
                        modifier = Modifier
                            .weight(1f)
                            .height(70.dp),
                    )
                }
            }

            PlanLabel(text = stringResource(Res.string.learning_details_plan_mode_label))
            ClaySegmentedSlider(
                segments = PracticeMode.entries.map { mode ->
                    ClaySegment(
                        value = mode,
                        label = stringResource(mode.titleRes()),
                        icon = { isSelected ->
                            BaseIcon(
                                resource = when (mode) {
                                    PracticeMode.FLASHCARDS -> Res.drawable.ic_layers
                                    PracticeMode.QUIZ -> Res.drawable.ic_brain_ai
                                },
                                contentDescription = null,
                                modifier = Modifier.size(15.dp),
                                tint = if (isSelected) {
                                    ClayTheme.colors.onPrimary
                                } else {
                                    ClayTheme.colors.textSecondary
                                },
                            )
                        },
                    )
                },
                selectedValue = selectedMode,
                onValueChange = onModeSelected,
            )
            Text(
                text = stringResource(selectedMode.descriptionRes()),
                style = AppTypography.bodySmall.copy(fontSize = 9.5.sp, lineHeight = 14.sp),
                color = ClayTheme.colors.textSecondary,
            )
        }
    }
}

@Composable
private fun PlanLabel(text: String) {
    Text(
        text = text,
        style = AppTypography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 10.5.sp,
        ),
        color = ClayTheme.colors.textPrimary,
    )
}

/**
 * Sticky action bar of the detail page: the estimated plan on one side and the
 * enroll (or settings) action on the other.
 */
@Composable
fun PackageActionBar(
    isEnrolled: Boolean,
    estimatedDays: Int,
    isSubmitting: Boolean,
    onEnroll: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = AppCorners.XL,
        padding = Spacing.Medium,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
            ) {
                if (isEnrolled) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Spacing.Compact),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BaseIcon(
                            resource = Res.drawable.ic_check,
                            contentDescription = null,
                            modifier = Modifier.size(13.dp),
                            tint = ClayTheme.colors.secondary,
                        )
                        Text(
                            text = stringResource(Res.string.learning_details_enrolled),
                            style = AppTypography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 10.sp,
                            ),
                            color = ClayTheme.colors.textPrimary,
                        )
                    }
                } else {
                    Text(
                        text = stringResource(Res.string.learning_details_plan_days, estimatedDays),
                        style = AppTypography.labelMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp,
                        ),
                        color = ClayTheme.colors.primary,
                    )
                    Text(
                        text = stringResource(Res.string.learning_details_price_free),
                        style = AppTypography.bodySmall.copy(fontSize = 9.sp),
                        color = ClayTheme.colors.textSecondary,
                    )
                }
            }

            ClayButton(
                onClick = if (isEnrolled) onOpenSettings else onEnroll,
                enabled = !isSubmitting,
                variant = ClayButtonVariant.Primary,
                cornerRadius = AppCorners.Large,
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
            ) {
                BaseIcon(
                    resource = if (isEnrolled) {
                        Res.drawable.ic_settings
                    } else {
                        Res.drawable.ic_plus
                    },
                    contentDescription = null,
                    modifier = Modifier.size(15.dp),
                    tint = ClayTheme.colors.onPrimary,
                )
                Spacer(Modifier.size(Spacing.Compact))
                Text(
                    text = stringResource(
                        if (isEnrolled) {
                            Res.string.learning_details_enrolled_action
                        } else {
                            Res.string.learning_details_enroll
                        },
                    ),
                    style = AppTypography.labelMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp,
                    ),
                    color = ClayTheme.colors.onPrimary,
                )
            }
        }
    }
}