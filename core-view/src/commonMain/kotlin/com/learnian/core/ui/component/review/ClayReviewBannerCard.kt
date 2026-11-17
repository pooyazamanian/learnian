package com.learnian.core.ui.component.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.modifiers.clayActionRaised
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.all_notebook
import com.learnian.core.view.resources.booster
import org.jetbrains.compose.resources.DrawableResource

/**
 * Reusable & Scalable FSRS Smart Review Banner Card (clay-review-banner-card).
 *
 * Designed for use on HomeScreen, Review Screen, and Learning Modules.
 * Features:
 * - Algorithmic status pill tag at the top.
 * - Bold title and motivational subtitle description.
 * - Tilted 3D visual illustration graphic on the side.
 * - Wide 3D Claymorphic Action Button with estimated time badge, title, and booster rocket icon.
 */
@Composable
fun ClayReviewBannerCard(
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    tagText: String? = "الگوریتم تکرار هوشمند FSRS v5",
    title: String = "مرور موشک‌وار واژگان روزانه",
    description: String = "یادآوری کلمات درست قبل از لحظه فراموشی مغز با ضریب پایداری پویا",
    actionText: String = "شروع مرور هوشمند FSRS",
    timeEstimate: String? = "۷ دقیقه",
    actionIcon: DrawableResource = Res.drawable.booster,
    illustration: (@Composable () -> Unit)? = {
        BaseImage(
            resource = Res.drawable.all_notebook,
            contentDescription = "دفترچه مرور",
            modifier = Modifier
                .size(110.dp)
                .rotate(-7f),
        )
    },
    cornerRadius: Dp = AppCorners.XL,
) {
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = ClayTheme.colors.surface,
        cornerRadius = cornerRadius,
        padding = AppPaddings.CardPaddingComfortable,
        shadowColor = ClayTheme.colors.shadowOuter,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
        ) {
            // 1. Top Tag Pill
            if (tagText != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    ClayStatusChip(
                        text = tagText,
                        style = ClayChipStyle.Resting,
                        textColor = ClayTheme.colors.primary,
                        backgroundColor = ClayTheme.colors.surfaceSunken,
                        icon = {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(ClayTheme.colors.primary, CircleShape),
                            )
                        },
                    )
                }
            }

            // 2. Middle Section: Title & Description alongside 3D Graphic Illustration
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // Text Column
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                ) {
                    Text(
                        text = title,
                        style = AppTypography.headlineMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp,
                            lineHeight = 28.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = description,
                        style = AppTypography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                        ),
                        color = ClayTheme.colors.textSecondary,
                    )
                }

                // Illustration Column
                if (illustration != null) {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        illustration()
                    }
                }
            }

            // 3. Bottom Hero Action Button with Time Badge and Rocket Icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clayPressable(onClick = onActionClick)
                    .clayActionRaised(
                        backgroundColor = ClayTheme.colors.primary,
                        cornerRadius = AppCorners.Large,
                        shadowColor = ClayTheme.colors.shadowOuter,
                    )
                    .defaultMinSize(minHeight = 54.dp)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // Time Estimate Pill Badge (at the start)
                if (timeEstimate != null) {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.28f),
                                        Color.White.copy(alpha = 0.15f),
                                    )
                                ),
                                shape = CircleShape,
                            )
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = timeEstimate,
                            style = AppTypography.titleSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp,
                            ),
                            color = Color.White,
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                // Central Action Text
                Text(
                    text = actionText,
                    style = AppTypography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                    ),
                    color = Color.White,
                )

                // End Rocket Icon
                BaseImage(
                    resource = actionIcon,
                    contentDescription = actionText,
                    modifier = Modifier.size(26.dp),
                )
            }
        }
    }
}
