package com.learnian.core.ui.component.switch

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.modifiers.clayInsetGrooved
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.ClayTheme

/**
 * Claymorphic 3D Toggle Switch (clay-switch).
 *
 * Implements a sunken grooved track and a sliding plump 3D thumb.
 */
@Composable
fun ClaySwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checkedTrackColor: Color = Color(0xFF00A86B), // Mint Emerald
    uncheckedTrackColor: Color = ClayTheme.colors.surfaceSunken,
    thumbColor: Color = Color.White,
    width: Dp = 54.dp,
    height: Dp = 32.dp,
) {
    val thumbSize = height - 8.dp
    val maxOffset = width - thumbSize - 8.dp

    val animatedOffset by animateDpAsState(
        targetValue = if (checked) maxOffset else 0.dp,
        animationSpec = spring(dampingRatio = 0.75f, stiffness = 400f),
        label = "switch-thumb-offset",
    )

    val animatedTrackColor by animateColorAsState(
        targetValue = if (checked) checkedTrackColor else uncheckedTrackColor,
        label = "switch-track-color",
    )

    // Force LTR for the switch track layout so the offset calculation is consistent
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Box(
            modifier = modifier
                .size(width = width, height = height)
                .clayPressable(
                    enabled = enabled,
                    onClick = { onCheckedChange(!checked) },
                )
                .clayInsetGrooved(
                    backgroundColor = animatedTrackColor,
                    cornerRadius = AppCorners.Pill,
                )
                .padding(4.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            // Plump 3D Clay Thumb
            Box(
                modifier = Modifier
                    .offset(x = animatedOffset)
                    .size(thumbSize)
                    .dropShadow(
                        shape = CircleShape,
                        shadow = Shadow(
                            radius = 4.dp,
                            spread = 0.dp,
                            color = Color(0x38000000),
                            offset = DpOffset(0.dp, 2.dp),
                        ),
                    )
                    .background(thumbColor, CircleShape)
                    .clip(CircleShape)
                    .innerShadow(
                        shape = CircleShape,
                        shadow = Shadow(
                            radius = 2.dp,
                            spread = 0.dp,
                            color = Color.White,
                            offset = DpOffset(0.dp, 1.dp),
                        ),
                    )
                    .innerShadow(
                        shape = CircleShape,
                        shadow = Shadow(
                            radius = 2.5.dp,
                            spread = 0.dp,
                            color = Color.Black.copy(alpha = 0.10f),
                            offset = DpOffset(0.dp, (-1).dp),
                        ),
                    ),
            )
        }
    }
}
