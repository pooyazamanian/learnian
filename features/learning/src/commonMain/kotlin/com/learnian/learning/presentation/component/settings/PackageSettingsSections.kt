package com.learnian.learning.presentation.component.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.learnian.core.ui.component.button.ClayIconButton
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.tile.ClaySelectableTile
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.component.progress.ClayProgressBar
import com.learnian.core.ui.component.switch.ClaySwitch
import com.learnian.core.ui.modifiers.clayInsetGrooved
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.book
import com.learnian.core.view.resources.ic_info
import com.learnian.core.view.resources.ic_minus
import com.learnian.core.view.resources.ic_plus
import com.learnian.core.view.resources.ic_tune
import com.learnian.core.view.resources.learning_cd_decrease
import com.learnian.core.view.resources.learning_cd_increase
import com.learnian.core.view.resources.learning_settings_active_chip
import com.learnian.core.view.resources.learning_settings_capacity_hint
import com.learnian.core.view.resources.learning_settings_capacity_label
import com.learnian.core.view.resources.learning_settings_capacity_value
import com.learnian.core.view.resources.learning_settings_exact_desc
import com.learnian.core.view.resources.learning_settings_exact_label
import com.learnian.core.view.resources.learning_settings_inactive_note
import com.learnian.core.view.resources.learning_settings_option_words
import com.learnian.core.view.resources.learning_settings_percent
import com.learnian.core.view.resources.learning_settings_quota_title
import com.learnian.core.view.resources.learning_settings_read_of
import com.learnian.core.view.resources.learning_settings_reset
import com.learnian.core.view.resources.learning_settings_save
import com.learnian.core.view.resources.learning_settings_share
import com.learnian.core.view.resources.learning_settings_toggle_desc
import com.learnian.core.view.resources.learning_settings_toggle_title
import com.learnian.core.view.resources.learning_settings_total_label
import com.learnian.core.view.resources.learning_settings_version
import com.learnian.learning.domain.model.LearningPackage
import com.learnian.learning.presentation.mapper.asGroupedCount
import com.learnian.learning.presentation.mapper.packageAccentOf
import org.jetbrains.compose.resources.stringResource

/** Percentage presets offered by the smart quota selector. */
private val QUOTA_SHARE_PRESETS = listOf(15, 30, 50, 70)

/** Identity and volume summary of the package being configured. */
@Composable
fun PackageSettingsSummaryCard(
    packageInfo: LearningPackage,
    learnedWords: Int,
    learnedPercent: Int,
    modifier: Modifier = Modifier,
) {
    val accent = packageAccentOf(packageInfo.category)
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = accent.container,
        cornerRadius = AppCorners.XL,
        padding = Spacing.Medium,
        shadowColor = accent.shadow,
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
                            backgroundColor = ClayTheme.colors.surface,
                            cornerRadius = AppCorners.Medium,
                            shadowColor = accent.shadow,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    BaseImage(
                        resource = Res.drawable.book,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
                ) {
                    Text(
                        text = packageInfo.title,
                        style = AppTypography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp,
                            lineHeight = 19.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                    )
                    Text(
                        text = stringResource(
                            Res.string.learning_settings_version,
                            packageInfo.version,
                        ),
                        style = AppTypography.bodySmall.copy(fontSize = 9.sp),
                        color = ClayTheme.colors.textSecondary,
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(Res.string.learning_settings_total_label),
                    style = AppTypography.bodySmall.copy(fontSize = 10.sp),
                    color = ClayTheme.colors.textSecondary,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = packageInfo.totalWords.asGroupedCount(),
                    style = AppTypography.labelMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp,
                    ),
                    color = ClayTheme.colors.textPrimary,
                )
            }

            Text(
                text = stringResource(
                    Res.string.learning_settings_read_of,
                    learnedWords.asGroupedCount(),
                    learnedPercent,
                ),
                style = AppTypography.bodySmall.copy(fontSize = 9.5.sp),
                color = ClayTheme.colors.textSecondary,
            )

            ClayProgressBar(
                progress = if (packageInfo.totalWords <= 0) {
                    0f
                } else {
                    learnedWords.toFloat() / packageInfo.totalWords
                },
                modifier = Modifier.fillMaxWidth(),
                trackColor = ClayTheme.colors.surface,
                progressColor = accent.strong,
                height = 12.dp,
            )
        }
    }
}

/** Active-in-the-daily-cycle toggle. */
@Composable
fun PackageActiveToggleCard(
    isActive: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = AppCorners.XL,
        padding = Spacing.Medium,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.Small),
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
                    Text(
                        text = stringResource(Res.string.learning_settings_toggle_title),
                        style = AppTypography.labelMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.5.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                    )
                    Text(
                        text = stringResource(Res.string.learning_settings_toggle_desc),
                        style = AppTypography.bodySmall.copy(fontSize = 9.sp, lineHeight = 13.sp),
                        color = ClayTheme.colors.textSecondary,
                    )
                }
                ClaySwitch(checked = isActive, onCheckedChange = onToggle)
            }
            if (isActive) {
                Text(
                    text = stringResource(Res.string.learning_settings_active_chip),
                    style = AppTypography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp,
                    ),
                    color = ClayTheme.colors.secondary,
                )
            } else {
                Text(
                    text = stringResource(Res.string.learning_settings_inactive_note),
                    style = AppTypography.bodySmall.copy(fontSize = 9.sp),
                    color = ClayTheme.colors.onTertiaryContainer,
                )
            }
        }
    }
}

/**
 * Smart quota card: capacity, the share presets, the exact word counter and the
 * explanation of what the quota does.
 */
@Composable
fun PackageQuotaCard(
    dailyCapacity: Int,
    quota: Int,
    canDecrease: Boolean,
    canIncrease: Boolean,
    onQuotaSelected: (Int) -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BaseIcon(
                    resource = Res.drawable.ic_tune,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp),
                    tint = ClayTheme.colors.primary,
                )
                Text(
                    text = stringResource(Res.string.learning_settings_quota_title),
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp,
                    ),
                    color = ClayTheme.colors.textPrimary,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(Res.string.learning_settings_capacity_label),
                    style = AppTypography.bodySmall.copy(fontSize = 9.5.sp),
                    color = ClayTheme.colors.textSecondary,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(
                        Res.string.learning_settings_capacity_value,
                        dailyCapacity,
                    ),
                    style = AppTypography.labelMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 10.sp,
                    ),
                    color = ClayTheme.colors.primary,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            ) {
                QUOTA_SHARE_PRESETS.forEach { preset ->
                    val presetWords = (dailyCapacity * preset) / 100
                    ClaySelectableTile(
                        title = stringResource(Res.string.learning_settings_percent, preset),
                        subtitle = stringResource(Res.string.learning_settings_option_words, presetWords),
                        selected = quota == presetWords,
                        onClick = { onQuotaSelected(presetWords) },
                        modifier = Modifier
                            .weight(1f)
                            .height(62.dp),
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(Res.string.learning_settings_share, quota * 100 / dailyCapacity.coerceAtLeast(1)),
                    style = AppTypography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.5.sp,
                    ),
                    color = ClayTheme.colors.onSecondaryContainer,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(Res.string.learning_settings_capacity_hint),
                    style = AppTypography.bodySmall.copy(fontSize = 9.sp),
                    color = ClayTheme.colors.textSecondary,
                )
            }

            Text(
                text = stringResource(Res.string.learning_settings_exact_label),
                style = AppTypography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                ),
                color = ClayTheme.colors.textPrimary,
            )

            PackageQuotaStepper(
                quota = quota,
                canDecrease = canDecrease,
                canIncrease = canIncrease,
                onDecrease = onDecrease,
                onIncrease = onIncrease,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clayInsetGrooved(
                        backgroundColor = ClayTheme.colors.surfaceSunken,
                        cornerRadius = AppCorners.Medium,
                    )
                    .padding(Spacing.Small),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                verticalAlignment = Alignment.Top,
            ) {
                BaseIcon(
                    resource = Res.drawable.ic_info,
                    contentDescription = null,
                    modifier = Modifier.size(13.dp),
                    tint = ClayTheme.colors.primary,
                )
                Text(
                    text = stringResource(Res.string.learning_settings_exact_desc),
                    modifier = Modifier.weight(1f),
                    style = AppTypography.bodySmall.copy(fontSize = 9.sp, lineHeight = 14.sp),
                    color = ClayTheme.colors.textSecondary,
                )
            }
        }
    }
}

@Composable
private fun PackageQuotaStepper(
    quota: Int,
    canDecrease: Boolean,
    canIncrease: Boolean,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ClayIconButton(
            onClick = onDecrease,
            enabled = canDecrease,
            size = 44.dp,
            cornerRadius = AppCorners.Medium,
            backgroundColor = ClayTheme.colors.surface,
        ) {
            BaseIcon(
                resource = Res.drawable.ic_minus,
                contentDescription = stringResource(Res.string.learning_cd_decrease),
                modifier = Modifier.size(16.dp),
                tint = ClayTheme.colors.textPrimary,
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(44.dp)
                .clayInsetGrooved(
                    backgroundColor = ClayTheme.colors.surfaceSunken,
                    cornerRadius = AppCorners.Medium,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(Res.string.learning_settings_option_words, quota),
                style = AppTypography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                ),
                color = ClayTheme.colors.textPrimary,
            )
        }

        ClayIconButton(
            onClick = onIncrease,
            enabled = canIncrease,
            size = 44.dp,
            cornerRadius = AppCorners.Medium,
            backgroundColor = ClayTheme.colors.primary,
        ) {
            BaseIcon(
                resource = Res.drawable.ic_plus,
                contentDescription = stringResource(Res.string.learning_cd_increase),
                modifier = Modifier.size(16.dp),
                tint = ClayTheme.colors.onPrimary,
            )
        }
    }
}

/** Save and reset actions of the settings page. */
@Composable
fun PackageSettingsActions(
    canSave: Boolean,
    isSaving: Boolean,
    onSave: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Small),
    ) {
        ClayButton(
            onClick = onSave,
            enabled = canSave,
            modifier = Modifier.fillMaxWidth(),
            variant = ClayButtonVariant.Primary,
            cornerRadius = AppCorners.Large,
        ) {
            Text(
                text = stringResource(Res.string.learning_settings_save),
                style = AppTypography.labelMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                ),
                color = ClayTheme.colors.onPrimary,
            )
        }
        ClayButton(
            onClick = onReset,
            enabled = !isSaving,
            modifier = Modifier.fillMaxWidth(),
            variant = ClayButtonVariant.Surface,
            cornerRadius = AppCorners.Large,
        ) {
            Text(
                text = stringResource(Res.string.learning_settings_reset),
                style = AppTypography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                ),
                color = ClayTheme.colors.textSecondary,
            )
        }
    }
}