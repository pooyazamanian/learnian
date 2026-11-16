package com.learnian.core.ui.component.segmented

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.modifiers.clayActionRaised
import com.learnian.core.ui.modifiers.clayInsetGrooved
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme

/**
 * Model representing a single segment inside [ClaySegmentedSlider].
 */
data class ClaySegment<T>(
    val value: T,
    val label: String? = null,
    val icon: (@Composable (isSelected: Boolean) -> Unit)? = null,
    val customContent: (@Composable (isSelected: Boolean) -> Unit)? = null,
)

/**
 * Claymorphic 3D Segmented Slider / Animated Sliding Tab Control (clay-segmented-slider).
 *
 * Implements a sunken trench groove track with a plump 3D clay indicator pill
 * that smoothly slides under the selected segment with bouncy spring physics.
 */
@Composable
fun <T> ClaySegmentedSlider(
    segments: List<ClaySegment<T>>,
    selectedValue: T,
    onValueChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 46.dp,
    trackColor: Color = ClayTheme.colors.surfaceSunken,
    thumbColor: Color = ClayTheme.colors.primary,
    cornerRadius: Dp = AppCorners.Pill,
) {
    if (segments.isEmpty()) return

    val selectedIndex = segments.indexOfFirst { it.value == selectedValue }.coerceAtLeast(0)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clayInsetGrooved(
                backgroundColor = trackColor,
                cornerRadius = cornerRadius,
            )
            .padding(4.dp),
    ) {
        val availableWidth = maxWidth
        val segmentWidth = availableWidth / segments.size

        // نکته مهم در مورد جهت‌های RTL و LTR:
        // در Jetpack Compose، مودیفایر `Modifier.offset(x = ...)` به‌صورت پیش‌فرض `rtlAware = true` است
        // و نسبت به تراز پیش‌فرض کانتینر (`Alignment.TopStart`) اعمال می‌شود:
        // - در LTR (انگلیسی): نقطه Start لبه چپ است و آفست مثبت به سمت راست (Start -> End) حرکت می‌کند.
        // - در RTL (فارسی): نقطه Start لبه راست است و کامپوز به صورت خودکار جهت آفست را معکوس می‌کند (حرکت به چپ).
        // قبلاً با فرمول دستی `if (isRtl) (segments.size - 1 - selectedIndex)` معکوس‌سازی مضاعف (Double Inversion)
        // رخ می‌داد و جای آیتم اول و آخر در زبان فارسی جابجا می‌شد. با استفاده مستقیم از `segmentWidth * selectedIndex`
        // هر دو چیدمان LTR و RTL کاملاً متقارن و بدون خطا عمل می‌کنند.
        val targetOffset = segmentWidth * selectedIndex

        val animatedOffset by animateDpAsState(
            targetValue = targetOffset,
            animationSpec = spring(
                dampingRatio = 0.72f,
                stiffness = 380f,
            ),
            label = "segmented-thumb-offset",
        )

        // 1. Sliding 3D Plump Clay Thumb
        Box(
            modifier = Modifier
                .offset(x = animatedOffset)
                .width(segmentWidth)
                .fillMaxHeight()
                .clayActionRaised(
                    backgroundColor = thumbColor,
                    cornerRadius = cornerRadius - 2.dp,
                    shadowColor = if (ClayTheme.isDark) Color(0x66000000) else thumbColor.copy(alpha = 0.35f),
                ),
        )

        // 2. Interactive Segments Content Layer
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            segments.forEach { segment ->
                val isSelected = segment.value == selectedValue

                val textColor by animateColorAsState(
                    targetValue = if (isSelected) Color.White else ClayTheme.colors.textPrimary,
                    label = "segment-text-color",
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) {
                            onValueChange(segment.value)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    if (segment.customContent != null) {
                        segment.customContent.invoke(isSelected)
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            segment.icon?.invoke(isSelected)

                            if (segment.label != null) {
                                Text(
                                    text = segment.label,
                                    style = AppTypography.bodyMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                                        fontSize = 14.sp,
                                    ),
                                    color = textColor,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
