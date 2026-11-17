package com.learnian.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.home_owl_retention_label
import com.learnian.core.view.resources.home_owl_title
import com.learnian.core.view.resources.owl
import org.jetbrains.compose.resources.stringResource

/**
 * Custom Speech Bubble Shape featuring plump curved corners and a rightward triangular callout arrow
 * pointing directly at the mascot.
 */
class RightArrowSpeechBubbleShape(
    private val cornerRadius: Dp = 24.dp,
    private val arrowWidth: Dp = 9.dp,
    private val arrowHeight: Dp = 16.dp,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val r = with(density) { cornerRadius.toPx() }
        val aw = with(density) { arrowWidth.toPx() }
        val ah = with(density) { arrowHeight.toPx() }

        val bodyRight = size.width - aw
        val centerY = size.height / 2f

        val path = Path().apply {
            moveTo(r, 0f)
            lineTo(bodyRight - r, 0f)
            quadraticTo(bodyRight, 0f, bodyRight, r)

            // Arrow pointing right toward the owl
            lineTo(bodyRight, centerY - ah / 2f)
            lineTo(size.width, centerY)
            lineTo(bodyRight, centerY + ah / 2f)

            lineTo(bodyRight, size.height - r)
            quadraticTo(bodyRight, size.height, bodyRight - r, size.height)

            lineTo(r, size.height)
            quadraticTo(0f, size.height, 0f, size.height - r)

            lineTo(0f, r)
            quadraticTo(0f, 0f, r, 0f)
            close()
        }
        return Outline.Generic(path)
    }
}

/**
 * Learnyan Wise Owl Coach Card (جغد دانا لرنیان).
 *
 * Implements:
 * - Left side: Curvaceous, spherical white speech bubble with an integrated pointer arrow
 *   facing the owl, highlighting golden words, time, and retention badge.
 * - Right side: Soft lavender mascot container with centered title and a large, centered owl mascot image.
 */
@Composable
fun HomeOwlCoachCard(
    dueCount: Int,
    estimatedMinutes: Int,
    modifier: Modifier = Modifier,
    retentionRate: Int = 94,
    onClick: (() -> Unit)? = null,
) {
    val bubbleShape = RightArrowSpeechBubbleShape(
        cornerRadius = 24.dp,
        arrowWidth = 9.dp,
        arrowHeight = 16.dp,
    )

    val currentDirection = LocalLayoutDirection.current

    ClayCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = ClayTheme.colors.surface,
        cornerRadius = AppCorners.XL,
        padding = AppPaddings.CardPadding,
        shadowColor = ClayTheme.colors.shadowOuter,
        onClick = onClick,
    ) {
        // Enforce LTR positioning for Speech Bubble on Left, Owl on Right, while respecting content text direction
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                // 1. Curvaceous White Speech Bubble with Arrow on the Right
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .dropShadow(
                            shape = bubbleShape,
                            shadow = Shadow(
                                radius = 10.dp,
                                spread = 0.dp,
                                color = ClayTheme.colors.shadowOuter,
                                offset = DpOffset(0.dp, 3.dp),
                            ),
                        )
                        .background(
                            color = ClayTheme.colors.surface,
                            shape = bubbleShape,
                        )
                        .padding(start = 14.dp, end = 14.dp + 9.dp, top = 14.dp, bottom = 14.dp),
                ) {
                    // Restore natural RTL / LTR layout direction for speech content
                    CompositionLocalProvider(LocalLayoutDirection provides currentDirection) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween,
                        ) {
                            // Coach Speech Text with Highlights
                            val isPersian = currentDirection == LayoutDirection.Rtl
                            val speechText = if (isPersian) {
                                buildAnnotatedString {
                                    append("«امروز ")
                                    withStyle(
                                        SpanStyle(
                                            color = ClayTheme.colors.primary,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 14.sp,
                                        )
                                    ) {
                                        append("$dueCount لغت طلایی")
                                    }
                                    append(" در آستانه فراموشی داری! فقط ")
                                    withStyle(
                                        SpanStyle(
                                            color = ClayTheme.colors.primary,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 14.sp,
                                        )
                                    ) {
                                        append("$estimatedMinutes دقیقه")
                                    }
                                    append(" زمان می‌بره تا تثبیت بشن»")
                                }
                            } else {
                                buildAnnotatedString {
                                    append("Today you have ")
                                    withStyle(
                                        SpanStyle(
                                            color = ClayTheme.colors.primary,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 14.sp,
                                        )
                                    ) {
                                        append("$dueCount golden words")
                                    }
                                    append(" near forgetting! It only takes ")
                                    withStyle(
                                        SpanStyle(
                                            color = ClayTheme.colors.primary,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 14.sp,
                                        )
                                    ) {
                                        append("$estimatedMinutes min")
                                    }
                                    append(" to consolidate them")
                                }
                            }

                            Text(
                                text = speechText,
                                style = AppTypography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp,
                                    lineHeight = 22.sp,
                                ),
                                color = ClayTheme.colors.textPrimary,
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Footer: Retention Rate Label + Mint Pill Chip
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                ClayStatusChip(
                                    text = "$retentionRate%",
                                    style = ClayChipStyle.Resting,
                                    textColor = ClayTheme.colors.secondary,
                                    backgroundColor = ClayTheme.colors.secondaryContainer,
                                    borderColor = ClayTheme.colors.secondary.copy(alpha = 0.25f),
                                )

                                Text(
                                    text = stringResource(Res.string.home_owl_retention_label),
                                    style = AppTypography.bodySmall.copy(
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 11.5.sp,
                                    ),
                                    color = ClayTheme.colors.textSecondary,
                                )
                            }
                        }
                    }
                }

                // 2. Owl Mascot Container on the Right
                Column(
                    modifier = Modifier
                        .width(118.dp)
                        .fillMaxHeight()
                        .background(
                            color = ClayTheme.colors.surfaceSunken,
                            shape = RoundedCornerShape(AppCorners.Large),
                        )
                        .padding(vertical = 12.dp, horizontal = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(Res.string.home_owl_title),
                        style = AppTypography.titleSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.5.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )

                    // Large, centered owl image
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        BaseImage(
                            resource = Res.drawable.owl,
                            contentDescription = stringResource(Res.string.home_owl_title),
                            modifier = Modifier.size(96.dp),
                        )
                    }
                }
            }
        }
    }
}
