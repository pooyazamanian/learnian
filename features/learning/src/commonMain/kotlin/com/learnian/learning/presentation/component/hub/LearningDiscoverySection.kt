package com.learnian.learning.presentation.component.hub

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.component.textfield.ClayTextField
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.book
import com.learnian.core.view.resources.ic_chevron_left
import com.learnian.core.view.resources.ic_search
import com.learnian.core.view.resources.learning_category_all
import com.learnian.core.view.resources.learning_cd_open_package
import com.learnian.core.view.resources.learning_cd_search
import com.learnian.core.view.resources.learning_details_enrolled
import com.learnian.core.view.resources.learning_discover_empty
import com.learnian.core.view.resources.learning_discover_title
import com.learnian.core.view.resources.learning_package_words
import com.learnian.core.view.resources.learning_search_placeholder
import com.learnian.learning.domain.model.LearningPackage
import com.learnian.learning.domain.model.PackageCategory
import com.learnian.core.ui.component.header.ClaySectionHeader
import com.learnian.learning.presentation.mapper.asGroupedCount
import com.learnian.learning.presentation.mapper.packageAccentOf
import com.learnian.learning.presentation.mapper.labelRes
import com.learnian.learning.presentation.mvi.LearningState
import org.jetbrains.compose.resources.stringResource

/**
 * Discovery block: free-text search, category chips and the matching packages.
 * Selecting a package opens its detail (enrollment) page.
 */
@Composable
fun LearningDiscoverySection(
    state: LearningState,
    onSearchChange: (String) -> Unit,
    onCategorySelected: (PackageCategory?) -> Unit,
    onPackageClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
    ) {
        ClayTextField(
            value = state.searchQuery,
            onValueChange = onSearchChange,
            placeholder = stringResource(Res.string.learning_search_placeholder),
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = AppCorners.Pill,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 11.dp),
            leadingIcon = {
                BaseIcon(
                    resource = Res.drawable.ic_search,
                    contentDescription = stringResource(Res.string.learning_cd_search),
                    modifier = Modifier.size(18.dp),
                    tint = ClayTheme.colors.textSecondary,
                )
            },
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            val chip: @Composable (label: String, selected: Boolean, onClick: () -> Unit) -> Unit =
                { label, selected, onClick ->
                    ClayStatusChip(
                        text = label,
                        style = if (selected) ClayChipStyle.Embossed else ClayChipStyle.Resting,
                        onClick = onClick,
                        backgroundColor = if (selected) {
                            ClayTheme.colors.primary
                        } else {
                            ClayTheme.colors.surface
                        },
                        textColor = if (selected) {
                            ClayTheme.colors.onPrimary
                        } else {
                            ClayTheme.colors.textSecondary
                        },
                        shadowColor = if (selected) {
                            ClayTheme.colors.primary.copy(alpha = 0.28f)
                        } else {
                            null
                        },
                        textStyle = AppTypography.labelMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                        ),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                    )
                }
            chip(stringResource(Res.string.learning_category_all), state.category == null) {
                onCategorySelected(null)
            }
            PackageCategory.entries.forEach { category ->
                chip(stringResource(category.labelRes()), state.category == category) {
                    onCategorySelected(if (state.category == category) null else category)
                }
            }
        }

        ClaySectionHeader(title = stringResource(Res.string.learning_discover_title))

        val enrolledIds = remember(state.data) {
            state.data?.myPackages?.mapTo(mutableSetOf()) { it.packageInfo.id }.orEmpty()
        }
        val packages = state.visiblePackages
        if (packages.isEmpty()) {
            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = AppCorners.Large,
                padding = Spacing.Large,
            ) {
                Text(
                    text = stringResource(Res.string.learning_discover_empty),
                    modifier = Modifier.fillMaxWidth(),
                    style = AppTypography.bodySmall.copy(fontSize = 11.sp),
                    color = ClayTheme.colors.textSecondary,
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            packages.forEach { pkg ->
                LearningPackageCard(
                    packageInfo = pkg,
                    isEnrolled = pkg.id in enrolledIds,
                    onClick = { onPackageClick(pkg.id) },
                )
            }
        }
    }
}

/** Compact catalog row used by the discovery list, tinted with the package accent. */
@Composable
private fun LearningPackageCard(
    packageInfo: LearningPackage,
    isEnrolled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val accent = packageAccentOf(packageInfo.category)
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = accent.container,
        cornerRadius = AppCorners.Large,
        padding = Spacing.Comfortable,
        shadowColor = accent.shadow,
        onClick = onClick,
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
                    modifier = Modifier.size(30.dp),
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.Compact),
            ) {
                Text(
                    text = packageInfo.title,
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                    ),
                    color = ClayTheme.colors.textPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ClayStatusChip(
                        text = packageInfo.levelLabel,
                        style = ClayChipStyle.Resting,
                        backgroundColor = accent.strong,
                        textColor = accent.onStrong,
                        textStyle = AppTypography.labelMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 9.sp,
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 3.dp),
                    )
                    Text(
                        text = stringResource(
                            Res.string.learning_package_words,
                            packageInfo.totalWords.asGroupedCount(),
                        ),
                        style = AppTypography.bodySmall.copy(fontSize = 10.sp),
                        color = ClayTheme.colors.textSecondary,
                    )
                    if (isEnrolled) {
                        ClayStatusChip(
                            text = stringResource(Res.string.learning_details_enrolled),
                            style = ClayChipStyle.Resting,
                            backgroundColor = ClayTheme.colors.surface,
                            textColor = accent.strong,
                            textStyle = AppTypography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 9.sp,
                            ),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 3.dp),
                        )
                    }
                }
            }

            BaseIcon(
                resource = Res.drawable.ic_chevron_left,
                contentDescription = stringResource(Res.string.learning_cd_open_package),
                modifier = Modifier.size(18.dp),
                tint = accent.strong,
            )
        }
    }
}