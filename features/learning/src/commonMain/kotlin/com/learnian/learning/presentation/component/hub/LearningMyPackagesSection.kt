package com.learnian.learning.presentation.component.hub

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayIconButton
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.progress.ClayProgressBar
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_settings
import com.learnian.core.view.resources.learning_my_packages_count
import com.learnian.core.view.resources.learning_my_packages_title
import com.learnian.core.view.resources.learning_package_due
import com.learnian.core.view.resources.learning_package_inactive
import com.learnian.core.view.resources.learning_package_read_of
import com.learnian.core.view.resources.learning_package_ready
import com.learnian.core.view.resources.learning_package_settings
import com.learnian.learning.domain.model.EnrolledPackage
import com.learnian.core.ui.component.header.ClaySectionHeader
import com.learnian.learning.presentation.mapper.asGroupedCount
import com.learnian.learning.presentation.mapper.packageAccentOf
import org.jetbrains.compose.resources.stringResource

/** "Packages in progress" block of the hub, with the settings shortcut per package. */
@Composable
fun LearningMyPackagesSection(
    packages: List<EnrolledPackage>,
    activeCount: Int,
    onPackageClick: (String) -> Unit,
    onSettingsClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
    ) {
        ClaySectionHeader(
            title = stringResource(Res.string.learning_my_packages_title),
            trailing = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Compact),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ClayStatusChip(
                        text = stringResource(Res.string.learning_my_packages_count, activeCount),
                        style = ClayChipStyle.Resting,
                        backgroundColor = ClayTheme.colors.surface,
                        textColor = ClayTheme.colors.textSecondary,
                        textStyle = AppTypography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
                    )
                }
            },
        )

        packages.forEach { enrolled ->
            LearningMyPackageCard(
                enrolled = enrolled,
                onClick = { onPackageClick(enrolled.packageInfo.id) },
                onSettingsClick = { onSettingsClick(enrolled.packageInfo.id) },
            )
        }
    }
}

/**
 * One enrolled package: progress, due reviews and the settings shortcut that lives
 * where the package illustration used to be, right next to the title.
 */
@Composable
fun LearningMyPackageCard(
    enrolled: EnrolledPackage,
    onClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val info = enrolled.packageInfo
    val progress = enrolled.progress
    val accent = packageAccentOf(info.category)

    ClayCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = accent.container,
        cornerRadius = AppCorners.Large,
        padding = Spacing.Comfortable,
        shadowColor = accent.shadow,
        onClick = onClick,
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
                ClayIconButton(
                    onClick = onSettingsClick,
                    size = 40.dp,
                    cornerRadius = AppCorners.Medium,
                    backgroundColor = ClayTheme.colors.surface,
                ) {
                    BaseIcon(
                        resource = Res.drawable.ic_settings,
                        contentDescription = stringResource(Res.string.learning_package_settings),
                        modifier = Modifier.size(19.dp),
                        tint = accent.strong,
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Compact),
                ) {
                    Text(
                        text = info.title,
                        style = AppTypography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp,
                            lineHeight = 19.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Compact)) {
                        ClayStatusChip(
                            text = info.levelLabel,
                            style = ClayChipStyle.Resting,
                            backgroundColor = accent.strong,
                            textColor = accent.onStrong,
                            textStyle = AppTypography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 9.sp,
                            ),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 3.dp),
                        )
                        if (enrolled.enrollment.isActive) {
                            ClayStatusChip(
                                text = if (progress.dueToday > 0) {
                                    stringResource(Res.string.learning_package_due, progress.dueToday)
                                } else {
                                    stringResource(Res.string.learning_package_ready)
                                },
                                style = ClayChipStyle.Resting,
                                backgroundColor = ClayTheme.colors.surface,
                                textColor = ClayTheme.colors.textPrimary,
                                textStyle = AppTypography.labelMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 9.sp,
                                ),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 3.dp),
                            )
                        } else {
                            ClayStatusChip(
                                text = stringResource(Res.string.learning_package_inactive),
                                style = ClayChipStyle.Resting,
                                backgroundColor = ClayTheme.colors.surface,
                                textColor = ClayTheme.colors.textSecondary,
                                textStyle = AppTypography.labelMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 9.sp,
                                ),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 3.dp),
                            )
                        }
                    }
                }
            }

            Text(
                text = stringResource(
                    Res.string.learning_package_read_of,
                    progress.learnedWords.asGroupedCount(),
                    progress.totalWords.asGroupedCount(),
                ),
                style = AppTypography.bodySmall.copy(fontSize = 10.sp),
                color = ClayTheme.colors.textSecondary,
            )

            ClayProgressBar(
                progress = progress.fraction,
                modifier = Modifier.fillMaxWidth(),
                trackColor = ClayTheme.colors.surface,
                progressColor = if (enrolled.enrollment.isActive) {
                    accent.strong
                } else {
                    ClayTheme.colors.textSecondary
                },
                height = 10.dp,
            )
        }
    }
}
