package com.learnian.learning.presentation.component.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.component.stats.ClayStatCard
import com.learnian.core.ui.component.stats.ClayStatCardVariant
import com.learnian.core.ui.component.stats.ClayStatsRow
import com.learnian.core.ui.modifiers.clayInsetGrooved
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.book
import com.learnian.core.view.resources.ic_brain_ai
import com.learnian.core.view.resources.ic_check
import com.learnian.core.view.resources.ic_group
import com.learnian.core.view.resources.ic_headset_support
import com.learnian.core.view.resources.ic_layers
import com.learnian.core.view.resources.ic_quote
import com.learnian.core.view.resources.ic_ribbon_medal
import com.learnian.core.view.resources.ic_school
import com.learnian.core.view.resources.ic_star
import com.learnian.core.view.resources.learning_details_metric_audio
import com.learnian.core.view.resources.learning_details_metric_fsrs
import com.learnian.core.view.resources.learning_details_metric_lessons
import com.learnian.core.view.resources.learning_details_metric_words
import com.learnian.core.view.resources.learning_details_rating
import com.learnian.core.view.resources.learning_details_rating_caption
import com.learnian.core.view.resources.learning_details_students_unit
import com.learnian.core.view.resources.learning_details_testimonial_title
import com.learnian.core.view.resources.learning_details_why_title
import com.learnian.core.view.resources.learning_settings_percent
import com.learnian.learning.domain.model.LearningPackage
import com.learnian.learning.presentation.mapper.asGroupedCount
import com.learnian.learning.presentation.mapper.asRating
import com.learnian.learning.presentation.mapper.labelRes
import com.learnian.learning.presentation.mapper.packageAccentOf
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

/** Hero of the package detail page: identity, category, level and promise. */
@Composable
fun PackageHeroCard(
    packageInfo: LearningPackage,
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
                verticalAlignment = Alignment.Top,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Small),
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Compact)) {
                        ClayStatusChip(
                            text = stringResource(packageInfo.category.labelRes()),
                            style = ClayChipStyle.Resting,
                            backgroundColor = ClayTheme.colors.surface,
                            textColor = accent.strong,
                            textStyle = AppTypography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 9.sp,
                            ),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 3.dp),
                        )
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
                    }
                    Text(
                        text = packageInfo.title,
                        style = AppTypography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 19.sp,
                            lineHeight = 26.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                    )
                    Text(
                        text = packageInfo.description,
                        style = AppTypography.bodySmall.copy(fontSize = 11.sp, lineHeight = 17.sp),
                        color = ClayTheme.colors.textSecondary,
                    )
                    Text(
                        text = packageInfo.updatedLabel,
                        style = AppTypography.bodySmall.copy(fontSize = 9.sp),
                        color = ClayTheme.colors.textSecondary.copy(alpha = 0.8f),
                    )
                }

                Box(
                    modifier = Modifier
                        .size(58.dp)
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
                        modifier = Modifier.size(36.dp),
                    )
                }
            }
        }
    }
}

/** Rating and community strip: expert score plus active learners. */
@Composable
fun PackageRatingStrip(
    packageInfo: LearningPackage,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clayInsetGrooved(
                backgroundColor = ClayTheme.colors.surfaceSunken,
                cornerRadius = AppCorners.Medium,
            )
            .padding(horizontal = Spacing.Comfortable, vertical = Spacing.Small),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BaseIcon(
            resource = Res.drawable.ic_star,
            contentDescription = null,
            modifier = Modifier.size(15.dp),
            tint = ClayTheme.colors.tertiary,
        )
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.Tiny)) {
            Text(
                text = stringResource(
                    Res.string.learning_details_rating,
                    packageInfo.rating.asRating(),
                    MAX_RATING_SCALE,
                ),
                style = AppTypography.labelMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                ),
                color = ClayTheme.colors.textPrimary,
            )
            Text(
                text = stringResource(
                    Res.string.learning_details_rating_caption,
                    packageInfo.ratingCount.asGroupedCount(),
                ),
                style = AppTypography.bodySmall.copy(fontSize = 9.sp),
                color = ClayTheme.colors.textSecondary,
            )
        }
        Spacer(Modifier.weight(1f))
        BaseIcon(
            resource = Res.drawable.ic_group,
            contentDescription = null,
            modifier = Modifier.size(15.dp),
            tint = ClayTheme.colors.textSecondary,
        )
        Text(
            text = packageInfo.studentCount.asGroupedCount() + " " +
                stringResource(Res.string.learning_details_students_unit),
            style = AppTypography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
            ),
            color = ClayTheme.colors.textSecondary,
        )
    }
}

/** Two by two metric grid of the package volume and its learning features. */
@Composable
fun PackageMetricsGrid(
    packageInfo: LearningPackage,
    modifier: Modifier = Modifier,
) {
    val metrics = listOf(
        PackageMetric(
            value = packageInfo.totalWords.asGroupedCount(),
            label = stringResource(Res.string.learning_details_metric_words),
            icon = Res.drawable.ic_layers,
        ),
        PackageMetric(
            value = packageInfo.lessonCount.asGroupedCount(),
            label = stringResource(Res.string.learning_details_metric_lessons),
            icon = Res.drawable.ic_school,
        ),
        PackageMetric(
            value = stringResource(
                Res.string.learning_settings_percent,
                packageInfo.audioCoveragePercent.asGroupedCount(),
            ),
            label = stringResource(Res.string.learning_details_metric_audio),
            icon = Res.drawable.ic_headset_support,
        ),
        PackageMetric(
            value = packageInfo.fsrsVersion,
            label = stringResource(Res.string.learning_details_metric_fsrs),
            icon = Res.drawable.ic_brain_ai,
        ),
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Small),
    ) {
        val variants = listOf(
            ClayStatCardVariant.Purple,
            ClayStatCardVariant.Mint,
            ClayStatCardVariant.Amber,
        )
        metrics.chunked(2).forEachIndexed { rowIndex, rowMetrics ->
            ClayStatsRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalSpacing = Spacing.Small,
            ) {
                rowMetrics.forEachIndexed { columnIndex, metric ->
                    val variant = variants[(rowIndex * 2 + columnIndex) % variants.size]
                    ClayStatCard(
                        title = metric.label,
                        value = metric.value,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        icon = {
                            BaseIcon(
                                resource = metric.icon,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = variant.primaryColor,
                            )
                        },
                        variant = variant,
                    )
                }
            }
        }
    }
}

private data class PackageMetric(
    val value: String,
    val label: String,
    val icon: DrawableResource,
)
/** "Why study this package": the promise plus its concrete highlights. */
@Composable
fun PackageWhyCard(
    packageInfo: LearningPackage,
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
                horizontalArrangement = Arrangement.spacedBy(Spacing.Compact),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BaseIcon(
                    resource = Res.drawable.ic_ribbon_medal,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp),
                    tint = ClayTheme.colors.primary,
                )
                Text(
                    text = stringResource(Res.string.learning_details_why_title),
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                    ),
                    color = ClayTheme.colors.textPrimary,
                )
            }
            Text(
                text = packageInfo.whyStudy,
                style = AppTypography.bodySmall.copy(fontSize = 10.5.sp, lineHeight = 17.sp),
                color = ClayTheme.colors.textSecondary,
            )
            packageInfo.highlights.forEach { highlight ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                    verticalAlignment = Alignment.Top,
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .clayInsetGrooved(
                                backgroundColor = ClayTheme.colors.secondaryContainer,
                                cornerRadius = AppCorners.Pill,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        BaseIcon(
                            resource = Res.drawable.ic_check,
                            contentDescription = null,
                            modifier = Modifier.size(10.dp),
                            tint = ClayTheme.colors.onSecondaryContainer,
                        )
                    }
                    Text(
                        text = highlight,
                        modifier = Modifier.weight(1f),
                        style = AppTypography.bodySmall.copy(fontSize = 10.sp, lineHeight = 16.sp),
                        color = ClayTheme.colors.textPrimary,
                    )
                }
            }
        }
    }
}

/** Social proof card: the quote of a high-scoring learner. */
@Composable
fun PackageTestimonialCard(
    packageInfo: LearningPackage,
    modifier: Modifier = Modifier,
) {
    val testimonial = packageInfo.testimonial ?: return
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = ClayTheme.colors.primaryContainer,
        cornerRadius = AppCorners.XL,
        padding = Spacing.Medium,
        shadowColor = ClayTheme.colors.primary.copy(alpha = 0.16f),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            Text(
                text = stringResource(Res.string.learning_details_testimonial_title),
                style = AppTypography.labelMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 10.sp,
                ),
                color = ClayTheme.colors.onPrimaryContainer,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Tiny)) {
                repeat(MAX_TESTIMONIAL_STARS) {
                    BaseIcon(
                        resource = Res.drawable.ic_star,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = ClayTheme.colors.tertiary,
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.Compact),
                verticalAlignment = Alignment.Top,
            ) {
                BaseIcon(
                    resource = Res.drawable.ic_quote,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = ClayTheme.colors.primary.copy(alpha = 0.6f),
                )
                Text(
                    text = testimonial.quote,
                    modifier = Modifier.weight(1f),
                    style = AppTypography.bodySmall.copy(
                        fontSize = 10.5.sp,
                        lineHeight = 17.sp,
                    ),
                    color = ClayTheme.colors.textPrimary,
                    textAlign = TextAlign.Start,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clayRestingFloat(
                            backgroundColor = ClayTheme.colors.surface,
                            cornerRadius = AppCorners.Pill,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = testimonial.authorInitials,
                        style = AppTypography.labelMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                        ),
                        color = ClayTheme.colors.primary,
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
                ) {
                    Text(
                        text = testimonial.authorName,
                        style = AppTypography.labelMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                    )
                    Text(
                        text = testimonial.authorRank,
                        style = AppTypography.bodySmall.copy(fontSize = 9.sp),
                        color = ClayTheme.colors.textSecondary,
                    )
                }
                ClayStatusChip(
                    text = testimonial.authorBadge,
                    style = ClayChipStyle.Resting,
                    backgroundColor = ClayTheme.colors.surface,
                    textColor = ClayTheme.colors.primary,
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

private const val MAX_RATING_SCALE = "5"
private const val MAX_TESTIMONIAL_STARS = 5