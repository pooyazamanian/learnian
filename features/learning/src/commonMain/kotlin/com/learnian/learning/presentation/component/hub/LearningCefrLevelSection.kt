package com.learnian.learning.presentation.component.hub

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.button.ClayButtonVariant
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ChipIconPosition
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_check
import com.learnian.core.view.resources.ic_chevron_left
import com.learnian.core.view.resources.ic_compass
import com.learnian.core.view.resources.ic_tune
import com.learnian.core.view.resources.learning_level_start
import com.learnian.core.view.resources.learning_level_test_action
import com.learnian.core.view.resources.learning_level_test_desc
import com.learnian.core.view.resources.learning_level_test_title
import com.learnian.core.view.resources.learning_levels_subtitle
import com.learnian.core.view.resources.learning_levels_title
import com.learnian.learning.domain.model.CefrBand
import com.learnian.learning.domain.model.CefrLevel
import com.learnian.core.ui.component.header.ClaySectionHeader
import com.learnian.learning.presentation.mapper.codeRes
import com.learnian.learning.presentation.mapper.containerColor
import com.learnian.learning.presentation.mapper.contentColor
import com.learnian.learning.presentation.mapper.descriptionRes
import com.learnian.learning.presentation.mapper.glyphRes
import com.learnian.learning.presentation.mapper.titleRes
import org.jetbrains.compose.resources.stringResource

/** CEFR shortcut grid plus the placement-test call to action. */
@Composable
fun LearningCefrLevelSection(
    levels: List<CefrLevel>,
    onLevelClick: (CefrBand) -> Unit,
    onStartTest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
    ) {
        ClaySectionHeader(
            title = stringResource(Res.string.learning_levels_title),
            subtitle = stringResource(Res.string.learning_levels_subtitle),
            trailing = {
                BaseIcon(
                    resource = Res.drawable.ic_tune,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = ClayTheme.colors.textSecondary,
                )
            },
        )

        levels.chunked(2).forEach { rowLevels ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            ) {
                rowLevels.forEach { level ->
                    LearningCefrLevelCard(
                        level = level,
                        onClick = { onLevelClick(level.band) },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                }
                if (rowLevels.size == 1) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }

        LearningLevelTestBanner(onStartTest = onStartTest)
    }
}

@Composable
private fun LearningCefrLevelCard(
    level: CefrLevel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val band = level.band
    ClayCard(
        modifier = modifier,
        backgroundColor = band.containerColor(),
        cornerRadius = AppCorners.Large,
        padding = Spacing.Comfortable,
        shadowColor = ClayTheme.colors.shadowOuter,
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(Spacing.Compact),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(band.codeRes()),
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                    ),
                    color = band.contentColor(),
                )
                Spacer(Modifier.weight(1f))
                BaseIcon(
                    resource = band.glyphRes(),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = band.contentColor().copy(alpha = 0.75f),
                )
            }

            Text(
                text = stringResource(band.titleRes()),
                style = AppTypography.labelMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 11.sp,
                ),
                color = band.contentColor(),
            )
            Text(
                text = stringResource(band.descriptionRes(), level.wordCount),
                style = AppTypography.bodySmall.copy(fontSize = 9.sp, lineHeight = 13.sp),
                color = ClayTheme.colors.textSecondary,
            )

            Spacer(Modifier.weight(1f))

            ClayStatusChip(
                text = stringResource(Res.string.learning_level_start),
                style = ClayChipStyle.Resting,
                backgroundColor = ClayTheme.colors.surface,
                textColor = ClayTheme.colors.primary,
                textStyle = AppTypography.labelMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 9.sp,
                ),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                icon = {
                    BaseIcon(
                        resource = Res.drawable.ic_chevron_left,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = ClayTheme.colors.primary,
                    )
                },
                iconPosition = ChipIconPosition.End,
            )
        }
    }
}

@Composable
private fun LearningLevelTestBanner(
    onStartTest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = AppCorners.XL,
        padding = Spacing.Section,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clayRestingFloat(
                        backgroundColor = ClayTheme.colors.primaryContainer,
                        cornerRadius = AppCorners.Pill,
                        shadowColor = ClayTheme.colors.primary.copy(alpha = 0.22f),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                BaseIcon(
                    resource = Res.drawable.ic_compass,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = ClayTheme.colors.primary,
                )
            }
            Text(
                text = stringResource(Res.string.learning_level_test_title),
                style = AppTypography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                ),
                color = ClayTheme.colors.textPrimary,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(Res.string.learning_level_test_desc),
                style = AppTypography.bodySmall.copy(fontSize = 10.sp, lineHeight = 15.sp),
                color = ClayTheme.colors.textSecondary,
                textAlign = TextAlign.Center,
            )
            ClayButton(
                onClick = onStartTest,
                modifier = Modifier.fillMaxWidth(),
                variant = ClayButtonVariant.Primary,
                cornerRadius = AppCorners.Large,
            ) {
                BaseIcon(
                    resource = Res.drawable.ic_check,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp),
                    tint = ClayTheme.colors.onPrimary,
                )
                Spacer(Modifier.size(Spacing.Small))
                Text(
                    text = stringResource(Res.string.learning_level_test_action),
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
