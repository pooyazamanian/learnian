package com.learnian.core.ui.component.radio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.modifiers.clayPillRaised
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme

/**
 * Claymorphic 3D Radio Button indicator.
 */
@Composable
fun ClayRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = 24.dp,
    selectedColor: Color = ClayTheme.colors.primary,
    unselectedColor: Color = ClayTheme.colors.surfaceSunken,
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1f else 0.75f,
        animationSpec = spring(),
        label = "radio-scale",
    )

    val innerColor by animateColorAsState(
        targetValue = if (selected) selectedColor else Color.Transparent,
        label = "radio-color",
    )

    Box(
        modifier = modifier
            .size(size)
            .then(
                if ((onClick != null) && enabled) Modifier.clayPressable(onClick = onClick)
                else Modifier,
            )
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = if (selected) selectedColor else ClayTheme.colors.textSecondary.copy(alpha = 0.4f),
                shape = CircleShape,
            )
            .background(unselectedColor, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .size(size * 0.55f)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(innerColor, CircleShape),
            )
        }
    }
}

/**
 * Claymorphic Selectable Card Tile incorporating a Radio Button indicator.
 * Perfect for option grids such as language accents, theme pickers, and plan selection.
 */
@Composable
fun ClayRadioCard(
    selected: Boolean,
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    enabled: Boolean = true,
    selectedColor: Color = ClayTheme.colors.primary,
    selectedBackground: Color = if (ClayTheme.isDark) Color(0xFF262044) else Color(0xFFF1EEFC),
    unselectedBackground: Color = if (ClayTheme.isDark) Color(0xFF1E1C2B) else Color(0xFFF4F2F9),
) {
    val borderColor = if (selected) selectedColor.copy(alpha = 0.5f) else Color.Transparent
    val titleColor = if (selected) selectedColor else ClayTheme.colors.textPrimary

    Box(
        modifier = modifier
            .clayPressable(enabled = enabled, onClick = onClick)
            .then(
                if (selected) {
                    Modifier.clayPillRaised(
                        backgroundColor = selectedBackground,
                        cornerRadius = AppCorners.Large,
                        borderColor = borderColor,
                        borderWidth = 1.2.dp,
                        shadowColor = if (ClayTheme.isDark) Color(0x66000000) else selectedColor.copy(alpha = 0.22f),
                    )
                } else {
                    Modifier.clayRestingFloat(
                        backgroundColor = unselectedBackground,
                        cornerRadius = AppCorners.Large,
                    )
                },
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = title,
                    style = AppTypography.bodyMedium.copy(
                        fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Bold,
                        fontSize = 14.sp,
                    ),
                    color = titleColor,
                )
                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        style = AppTypography.bodySmall,
                        color = ClayTheme.colors.textSecondary,
                    )
                }
            }

            ClayRadioButton(
                selected = selected,
                onClick = onClick,
                selectedColor = selectedColor,
                size = 22.dp,
            )
        }
    }
}
