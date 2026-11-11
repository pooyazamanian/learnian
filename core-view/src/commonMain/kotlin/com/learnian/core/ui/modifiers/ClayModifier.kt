package com.learnian.core.ui.modifiers

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.theme.ClayTheme

/**
 * Surface 2 • Action Raised (clay-btn)
 * Interactive state for buttons with bottom shadow and top-left inset light highlight.
 */
@Composable
fun Modifier.clayActionRaised(
    backgroundColor: Color = ClayTheme.colors.primary,
    cornerRadius: Dp = 20.dp,
    shadowColor: Color = backgroundColor.copy(alpha = 0.35f),
    highlightColor: Color = Color.White.copy(alpha = 0.45f)
): Modifier {
    val shape = RoundedCornerShape(cornerRadius)
    return this
        .drawBehind {
            val cornerPx = cornerRadius.toPx()
            val strokePx = 2.dp.toPx()

            // Outer drop shadow
            drawRoundRect(
                color = shadowColor,
                topLeft = Offset(0f, 6.dp.toPx()),
                size = size,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerPx, cornerPx)
            )

            // Base clay surface
            drawRoundRect(
                color = backgroundColor,
                size = size,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerPx, cornerPx)
            )

            // Top-left inset highlight
            drawRoundRect(
                color = highlightColor,
                topLeft = Offset(strokePx, strokePx),
                size = size.copy(
                    width = (size.width - strokePx * 2).coerceAtLeast(0f),
                    height = (size.height - strokePx * 2).coerceAtLeast(0f)
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                    (cornerPx - strokePx).coerceAtLeast(0f),
                    (cornerPx - strokePx).coerceAtLeast(0f)
                ),
                style = Stroke(width = strokePx)
            )
        }
        .clip(shape)
}

/**
 * Surface 1 • Resting Float (clay-card)
 * Default elevation for flashcards and panels with top white highlight and bottom soft shadow spread.
 */
@Composable
fun Modifier.clayRestingFloat(
    backgroundColor: Color = ClayTheme.colors.surface,
    cornerRadius: Dp = 24.dp,
    shadowColor: Color = ClayTheme.colors.shadowOuter,
    highlightColor: Color = ClayTheme.colors.highlightTop
): Modifier {
    val shape = RoundedCornerShape(cornerRadius)
    return this
        .drawBehind {
            val cornerPx = cornerRadius.toPx()
            val highlightPx = 1.5.dp.toPx()

            // Soft floating spread shadow
            drawRoundRect(
                color = shadowColor,
                topLeft = Offset(0f, 8.dp.toPx()),
                size = size,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerPx, cornerPx)
            )

            // Clay Surface
            drawRoundRect(
                color = backgroundColor,
                size = size,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerPx, cornerPx)
            )

            // Top inset light highlight
            drawRoundRect(
                color = highlightColor,
                topLeft = Offset(highlightPx, highlightPx),
                size = size.copy(
                    width = (size.width - highlightPx * 2).coerceAtLeast(0f),
                    height = (size.height - highlightPx * 2).coerceAtLeast(0f)
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                    (cornerPx - highlightPx).coerceAtLeast(0f),
                    (cornerPx - highlightPx).coerceAtLeast(0f)
                ),
                style = Stroke(width = highlightPx)
            )
        }
        .clip(shape)
}

/**
 * Surface 0 • Inset Grooved (clay-sunken)
 * Deep trench effect for text inputs, progress tracks, and sunken docks.
 */
@Composable
fun Modifier.clayInsetGrooved(
    backgroundColor: Color = ClayTheme.colors.surfaceSunken,
    cornerRadius: Dp = 20.dp,
    darkShadowColor: Color = ClayTheme.colors.shadowInner,
    lightHighlightColor: Color = ClayTheme.colors.highlightTop
): Modifier {
    val shape = RoundedCornerShape(cornerRadius)
    return this
        .background(color = backgroundColor, shape = shape)
        .drawWithContent {
            drawContent()

            val cornerPx = cornerRadius.toPx()
            val strokeWidth = 3.dp.toPx()

            // Inner Top Shadow
            drawRoundRect(
                color = darkShadowColor,
                topLeft = Offset(0f, 0f),
                size = size,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerPx, cornerPx),
                style = Stroke(width = strokeWidth)
            )

            // Inner Bottom Reflex
            drawRoundRect(
                color = lightHighlightColor,
                topLeft = Offset(strokeWidth, strokeWidth),
                size = size.copy(
                    width = (size.width - strokeWidth * 2).coerceAtLeast(0f),
                    height = (size.height - strokeWidth * 2).coerceAtLeast(0f)
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                    (cornerPx - strokeWidth).coerceAtLeast(0f),
                    (cornerPx - strokeWidth).coerceAtLeast(0f)
                ),
                style = Stroke(width = strokeWidth / 2)
            )
        }
        .clip(shape)
}

/**
 * Scale down to 0.96 on interaction (:active spec)
 */
@Composable
fun Modifier.clayPressable(
    enabled: Boolean = true,
    pressedScale: Float = 0.96f,
    onClick: (() -> Unit)? = null
): Modifier {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) pressedScale else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    return this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .then(
            if (enabled && onClick != null) {
                Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                        },
                        onTap = {
                            onClick()
                        }
                    )
                }
            } else Modifier
        )
}
