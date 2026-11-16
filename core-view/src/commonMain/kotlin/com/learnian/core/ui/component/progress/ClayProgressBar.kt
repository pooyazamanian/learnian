package com.learnian.core.ui.component.progress

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.ClayTheme

/**
 * Claymorphism 3D Progress Bar
 *
 * Implements a sunken carved track with inner top shadow and bottom reflex,
 * containing a floating 3D clay progress indicator with top-left highlight
 * and soft drop shadow.
 */
@Composable
fun ClayProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = ClayTheme.colors.surfaceSunken,
    progressColor: Color = ClayTheme.colors.primary,
    height: Dp = 22.dp,
    cornerRadius: Dp = AppCorners.Pill
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f)
    )

    val shadowInner = ClayTheme.colors.shadowInner
    val highlightTop = ClayTheme.colors.highlightTop
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Ltr
    ) {
        // Sunken Grooved Track Container
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
                .drawBehind {
                    val r = if (cornerRadius == AppCorners.Pill) size.height / 2f else cornerRadius.toPx()
                    val cornerRadiusObj = CornerRadius(r, r)

                    // 1. Base track background
                    drawRoundRect(
                        color = trackColor,
                        size = size,
                        cornerRadius = cornerRadiusObj
                    )

                    // 2. Inner top-left dark shadow stroke for sunken trench depth
                    drawRoundRect(
                        color = shadowInner.copy(alpha = 0.18f),
                        topLeft = Offset(0f, 0f),
                        size = size,
                        cornerRadius = cornerRadiusObj,
                        style = Stroke(width = 2.5.dp.toPx())
                    )

                    // 3. Inner bottom-right light reflex highlight
                    drawRoundRect(
                        color = highlightTop.copy(alpha = 0.7f),
                        topLeft = Offset(1.dp.toPx(), 1.dp.toPx()),
                        size = Size(
                            (size.width - 2.dp.toPx()).coerceAtLeast(0f),
                            (size.height - 2.dp.toPx()).coerceAtLeast(0f)
                        ),
                        cornerRadius = CornerRadius((r - 1.dp.toPx()).coerceAtLeast(0f), (r - 1.dp.toPx()).coerceAtLeast(0f)),
                        style = Stroke(width = 1.dp.toPx())
                    )
                }
                .clip(RoundedCornerShape(percent = 50))
                .padding(3.dp),
            contentAlignment = Alignment.CenterStart
        )
        {
            if (animatedProgress > 0f) {
                // Plump 3D Clay Active Progress Indicator
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(animatedProgress)
                        .drawBehind {
                            val r = size.height / 2f
                            val cornerRadiusObj = CornerRadius(r, r)

                            val clayGradient = Brush.verticalGradient(
                                colors = listOf(
                                    progressColor.copy(alpha = 0.95f),
                                    progressColor,
                                    progressColor.copy(alpha = 0.9f)
                                )
                            )

                            // 1. Soft ambient drop shadow underneath active bar
                            drawRoundRect(
                                color = progressColor.copy(alpha = 0.35f),
                                topLeft = Offset(0f, 1.dp.toPx()),
                                size = size,
                                cornerRadius = cornerRadiusObj
                            )

                            // 2. Main 3D Clay Body
                            drawRoundRect(
                                brush = clayGradient,
                                size = size,
                                cornerRadius = cornerRadiusObj
                            )

                            // 3. Top Inset Highlight Curve (Inner Glow)
                            drawRoundRect(
                                color = Color.White.copy(alpha = 0.65f),
                                topLeft = Offset(0.8.dp.toPx(), 0.8.dp.toPx()),
                                size = Size(
                                    (size.width - 1.6.dp.toPx()).coerceAtLeast(0f),
                                    (size.height - 1.6.dp.toPx()).coerceAtLeast(0f)
                                ),
                                cornerRadius = CornerRadius(
                                    (r - 0.8.dp.toPx()).coerceAtLeast(0f),
                                    (r - 0.8.dp.toPx()).coerceAtLeast(0f)
                                ),
                                style = Stroke(width = 1.2.dp.toPx())
                            )

                            // 4. Bottom Inset Shadow Curve for 3D Volume
                            drawRoundRect(
                                color = Color.Black.copy(alpha = 0.2f),
                                topLeft = Offset(1.5.dp.toPx(), 1.5.dp.toPx()),
                                size = Size(
                                    (size.width - 3.dp.toPx()).coerceAtLeast(0f),
                                    (size.height - 2.5.dp.toPx()).coerceAtLeast(0f)
                                ),
                                cornerRadius = CornerRadius(
                                    (r - 1.5.dp.toPx()).coerceAtLeast(0f),
                                    (r - 1.5.dp.toPx()).coerceAtLeast(0f)
                                ),
                                style = Stroke(width = 1.dp.toPx())
                            )
                        }
                )
            }
        }
    }

}
