package com.learnian.core.ui.component.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.progress.ClayProgressBar
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing

/**
 * Color variant specification for [ClayStatCard] tied directly to [ClayTheme.colors].
 */
sealed interface ClayStatCardVariant {
    val primaryColor: Color @Composable get
    val containerColor: Color @Composable get
    val iconBackgroundColor: Color @Composable get
    val shadowColor: Color @Composable get

    data object Purple : ClayStatCardVariant {
        override val primaryColor: Color @Composable get() = ClayTheme.colors.onPrimaryContainer
        override val containerColor: Color @Composable get() = ClayTheme.colors.primaryContainer
        override val iconBackgroundColor: Color @Composable get() = ClayTheme.colors.surfaceSunken
        override val shadowColor: Color @Composable get() = ClayTheme.colors.shadowOuter
    }

    data object Mint : ClayStatCardVariant {
        override val primaryColor: Color @Composable get() = ClayTheme.colors.secondary
        override val containerColor: Color @Composable get() = ClayTheme.colors.secondaryContainer
        override val iconBackgroundColor: Color @Composable get() = ClayTheme.colors.surfaceSunken
        override val shadowColor: Color @Composable get() = ClayTheme.colors.shadowOuter
    }

    data object Amber : ClayStatCardVariant {
        override val primaryColor: Color @Composable get() = ClayTheme.colors.tertiary
        override val containerColor: Color @Composable get() = ClayTheme.colors.tertiaryContainer
        override val iconBackgroundColor: Color @Composable get() = ClayTheme.colors.surfaceSunken
        override val shadowColor: Color @Composable get() = ClayTheme.colors.shadowOuter
    }

    class Custom(
        private val customPrimaryColor: Color,
        private val customContainerColor: Color,
        private val customIconBackgroundColor: Color? = null,
        private val customShadowColor: Color? = null,
    ) : ClayStatCardVariant {
        override val primaryColor: Color @Composable get() = customPrimaryColor
        override val containerColor: Color @Composable get() = customContainerColor
        override val iconBackgroundColor: Color @Composable get() = customIconBackgroundColor ?: ClayTheme.colors.surfaceSunken
        override val shadowColor: Color @Composable get() = customShadowColor ?: ClayTheme.colors.shadowOuter
    }
}

/**
 * Reusable & Scalable Claymorphic Metric / Stat Card (clay-stat-card).
 *
 * Designed for modular use on HomeScreen, Review Screen, Deck Details, and Stats screens.
 * Features:
 * - Top header with circular icon, title, and optional trailing badge (e.g. progress percentage).
 * - Central metric body (either big bold number + subtitle, or subtitle + progress bar).
 * - Matches row heights smoothly with [IntrinsicSize.Max].
 */
@Composable
fun ClayStatCard(
    title: String,
    modifier: Modifier = Modifier,
    value: String? = null,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    trailingBadge: (@Composable () -> Unit)? = null,
    progress: Float? = null,
    variant: ClayStatCardVariant = ClayStatCardVariant.Purple,
    cornerRadius: Dp = AppCorners.XL,
    onClick: (() -> Unit)? = null,
) {
    val effectiveContainerColor = variant.containerColor
    val effectiveShadowColor = variant.shadowColor
    val primaryTextColor = variant.primaryColor

    ClayCard(
        modifier = modifier
            .fillMaxHeight()
            .defaultMinSize(minHeight = 120.dp),
        backgroundColor = effectiveContainerColor,
        cornerRadius = cornerRadius,
        padding = AppPaddings.CardPaddingCompact,
        shadowColor = effectiveShadowColor,
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 1. Header: Icon + Title + Trailing Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier.weight(1f, fill = false),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Compact),
                ) {
                    if (icon != null) {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .background(
                                    color = variant.iconBackgroundColor,
                                    shape = CircleShape,
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            icon()
                        }
                    }

                    Text(
                        text = title,
                        style = AppTypography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.5.sp,
                        ),
                        color = primaryTextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                if (trailingBadge != null) {
                    trailingBadge()
                }
            }

            // 2. Middle Body: Vertically Centered Metric Display
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = Spacing.ExtraSmall),
                contentAlignment = Alignment.Center,
            ) {
                if (value != null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = value,
                            style = AppTypography.headlineLarge.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 28.sp,
                                lineHeight = 30.sp,
                            ),
                            color = primaryTextColor,
                            textAlign = TextAlign.Center,
                        )

                        if (subtitle != null) {
                            Spacer(modifier = Modifier.height(Spacing.Tiny))
                            Text(
                                text = subtitle,
                                style = AppTypography.bodySmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 11.sp,
                                ),
                                color = ClayTheme.colors.textSecondary,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                } else {
                    // Alternative mode: Subtitle + mini progress bar (e.g. Daily Goal)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        if (subtitle != null) {
                            Text(
                                text = subtitle,
                                style = AppTypography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                ),
                                color = primaryTextColor,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                        if (progress != null) {
                            Spacer(modifier = Modifier.height(Spacing.Small))
                            ClayProgressBar(
                                progress = progress,
                                height = 8.dp,
                                progressColor = variant.primaryColor,
                                trackColor = ClayTheme.colors.surfaceSunken,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = Spacing.Tiny),
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Responsive / Balanced Row container for placing multiple [ClayStatCard]s side-by-side
 * with guaranteed equal height across all items.
 */
@Composable
fun ClayStatsRow(
    modifier: Modifier = Modifier,
    horizontalSpacing: Dp = 10.dp,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}
