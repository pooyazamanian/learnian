package com.learnian.core.ui.modifiers

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.border
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.ClayTheme

private fun Color.lighten(f: Float): Color = lerp(this, Color.White, f)
private fun Color.darken(f: Float): Color = lerp(this, Color.Black, f)

/**
 * Surface 3 • Embossed Raised Pill (clay-pill)
 * Compact plump 3D pill with ambient and contact shadows, lit gradient body,
 * and top inner highlight rim for gamification badges and status chips.
 */
@Composable
fun Modifier.clayPillRaised(
    backgroundColor: Color = ClayTheme.colors.surface,
    cornerRadius: Dp = AppCorners.Pill,
    borderColor: Color? = null,
    borderWidth: Dp = 1.dp,
    shadowColor: Color = if (ClayTheme.isDark) Color(0x66000000) else Color(0x22000000),
    highlightColor: Color = if (ClayTheme.isDark) Color.White.copy(alpha = 0.16f) else Color.White.copy(alpha = 0.75f),
): Modifier {
    val shape = RoundedCornerShape(cornerRadius)
    return this
        // Ambient soft drop shadow
        .dropShadow(
            shape = shape,
            shadow = Shadow(
                radius = 10.dp,
                spread = (-1).dp,
                color = shadowColor,
                offset = DpOffset(0.dp, 4.dp),
            )
        )
        // Contact shadow for sharp elevation
        .dropShadow(
            shape = shape,
            shadow = Shadow(
                radius = 3.dp,
                spread = 0.dp,
                color = shadowColor.copy(alpha = shadowColor.alpha * 0.75f),
                offset = DpOffset(0.dp, 1.5.dp),
            )
        )
        // Plump lit clay gradient
        .background(
            brush = Brush.verticalGradient(
                0f to backgroundColor.lighten(0.12f),
                0.55f to backgroundColor,
                1f to backgroundColor.darken(0.04f),
            ),
            shape = shape,
        )
        .then(
            if ((borderColor != null) && (borderWidth > 0.dp)) {
                Modifier.border(borderWidth, borderColor, shape)
            } else Modifier
        )
        .clip(shape)
        // Top crisp highlight rim
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = 2.5.dp,
                spread = 0.dp,
                color = highlightColor,
                offset = DpOffset(0.dp, 1.2.dp),
            )
        )
        // Bottom inner shadow for 3D plump volume
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = 3.dp,
                spread = 0.dp,
                color = Color.Black.copy(alpha = 0.08f),
                offset = DpOffset(0.dp, (-1.5).dp),
            )
        )
}

/**
 * Surface 2 • Action Raised (clay-btn)
 * Real blurred ambient + contact shadow, lit gradient body, soft inner rims.
 */
@Composable
fun Modifier.clayActionRaised(
    backgroundColor: Color = ClayTheme.colors.primary,
    cornerRadius: Dp = 28.dp,
    shadowColor: Color = backgroundColor.copy(alpha = 0.28f),
    highlightColor: Color = Color.White.copy(alpha = 0.45f)
): Modifier {
    val shape = RoundedCornerShape(cornerRadius)
    return this
        // Ambient glow — tighter & weaker: reads as shadow, not fog
        .dropShadow(
            shape = shape,
            shadow = Shadow(
                radius = 14.dp,
                spread = (-4).dp,
                color = shadowColor,
                offset = DpOffset(0.dp, 8.dp)
            )
        )
        // Contact shadow — keeps it grounded
        .dropShadow(
            shape = shape,
            shadow = Shadow(
                radius = 4.dp,
                spread = 0.dp,
                color = shadowColor.copy(alpha = shadowColor.alpha * 0.5f),
                offset = DpOffset(0.dp, 2.dp)
            )
        )
        // Solid clay body
        .background(
            brush = Brush.verticalGradient(
                0f to backgroundColor.lighten(0.10f),
                0.55f to backgroundColor,
                1f to backgroundColor.darken(0.06f)
            ),
            shape = shape
        )
        .clip(shape)
        // Thin, crisp light rim on top
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = 3.dp,
                spread = 0.dp,
                color = highlightColor,
                offset = DpOffset(0.dp, 1.dp)
            )
        )
        // Thin dark rim at bottom
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = 4.dp,
                spread = 0.dp,
                color = Color.Black.copy(alpha = 0.12f),
                offset = DpOffset(0.dp, (-2).dp)
            )
        )
}

/**
 * Surface 1 • Resting Float (clay-card)
 * Wide soft neutral shadow + barely-there top light.
 */
@Composable
fun Modifier.clayRestingFloat(
    backgroundColor: Color = ClayTheme.colors.surface,
    cornerRadius: Dp = 24.dp,
    shadowColor: Color = ClayTheme.colors.shadowOuter, // give this ~0.18–0.25 alpha in the theme
    highlightColor: Color = ClayTheme.colors.highlightTop
): Modifier {
    val shape = RoundedCornerShape(cornerRadius)
    return this
        .dropShadow(
            shape = shape,
            shadow = Shadow(
                radius = 28.dp,
                spread = (-4).dp,
                color = shadowColor,
                offset = DpOffset(0.dp, 14.dp)
            )
        )
        .dropShadow(
            shape = shape,
            shadow = Shadow(
                radius = 8.dp,
                spread = 0.dp,
                color = shadowColor.copy(alpha = shadowColor.alpha * 0.5f),
                offset = DpOffset(0.dp, 4.dp)
            )
        )
        .background(
            brush = Brush.verticalGradient(
                0f to backgroundColor.lighten(0.2f),
                1f to backgroundColor.darken(0.03f)
            ),
            shape = shape
        )
        .clip(shape)
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = 5.dp,
                spread = 0.dp,
                color = highlightColor,
                offset = DpOffset(0.dp, 2.dp)
            )
        )
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = 10.dp,
                spread = 0.dp,
                color = Color.Black.copy(alpha = 0.05f),
                offset = DpOffset(0.dp, (-4).dp)
            )
        )
}

/**
 * Surface 0 • Inset Grooved (clay-sunken)
 * Dark soft rim at top, light reflex at bottom, faint light lip below the groove.
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
        .dropShadow(
            shape = shape,
            shadow = Shadow(
                radius = 4.dp,
                spread = 0.dp,
                color = lightHighlightColor.copy(alpha = 0.6f),
                offset = DpOffset(0.dp, 2.dp)
            )
        )
        .background(
            brush = Brush.verticalGradient(
                0f to backgroundColor.darken(0.05f),
                1f to backgroundColor.lighten(0.04f)
            ),
            shape = shape
        )
        .clip(shape)
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = 8.dp,
                spread = 1.dp,
                color = darkShadowColor,
                offset = DpOffset(0.dp, 3.dp)
            )
        )
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = 6.dp,
                spread = 0.dp,
                color = lightHighlightColor,
                offset = DpOffset(0.dp, (-2).dp)
            )
        )
}

/**
 * Scale down on press. Apply BEFORE the clay surface modifier so the
 * shadow scales together with the body.
 */
@Composable
fun Modifier.clayPressable(
    enabled: Boolean = true,
    pressedScale: Float = 0.96f,
    onClick: (() -> Unit)? = null
): Modifier {
    var isPressed by remember { mutableStateOf(false) }
    val currentOnClick by rememberUpdatedState(onClick)

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) pressedScale else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "clay-press-scale"
    )

    return this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .then(
            if (enabled && onClick != null) {
                Modifier.pointerInput(enabled) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                        },
                        onTap = { currentOnClick?.invoke() }
                    )
                }
            } else Modifier
        )
}