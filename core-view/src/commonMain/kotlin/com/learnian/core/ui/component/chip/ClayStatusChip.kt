package com.learnian.core.ui.component.chip

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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

enum class ChipIconPosition {
    Start,
    End,
}

enum class ClayChipStyle {
    /**
     * Soft floating clay capsule with gentle wide ambient shadow.
     * Ideal for TopBar (Gems, Streaks) and clean resting badges.
     */
    Resting,

    /**
     * Prominent plump 3D embossed capsule with contact + ambient shadows,
     * lit gradient body, and inner highlight rims.
     * Ideal for feature badges (Pro badge, Vocab Level, achievements).
     */
    Embossed,
}

/**
 * Claymorphism Status Pill Chip (clay-pill-chip)
 *
 * Supports two distinct surface styles:
 * - [ClayChipStyle.Resting]: Soft floating pill with gentle ambient shadow (used in TopBar).
 * - [ClayChipStyle.Embossed]: Raised, plump 3D capsule with dual shadows, gradient and highlights (used in Profile & Badges).
 */
@Composable
fun ClayStatusChip(
    text: String,
    modifier: Modifier = Modifier,
    style: ClayChipStyle = ClayChipStyle.Resting,
    icon: (@Composable () -> Unit)? = null,
    iconPosition: ChipIconPosition = ChipIconPosition.End,
    textColor: Color = ClayTheme.colors.textPrimary,
    backgroundColor: Color = ClayTheme.colors.surface,
    borderColor: Color? = null,
    borderWidth: Dp = 1.dp,
    shadowColor: Color? = null,
    textStyle: TextStyle = AppTypography.titleMedium.copy(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 14.sp,
    ),
    contentPadding: PaddingValues? = null,
    onClick: (() -> Unit)? = null,
) {
    val padding = contentPadding ?: when (style) {
        ClayChipStyle.Resting -> PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        ClayChipStyle.Embossed -> PaddingValues(horizontal = 14.dp, vertical = 7.dp)
    }

    ClayStatusChip(
        modifier = modifier,
        style = style,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        borderWidth = borderWidth,
        shadowColor = shadowColor,
        contentPadding = padding,
        onClick = onClick,
    ) {
        if ((icon != null) && (iconPosition == ChipIconPosition.Start)) {
            icon()
        }

        Text(
            text = text,
            style = textStyle,
            color = textColor,
        )

        if ((icon != null) && (iconPosition == ChipIconPosition.End)) {
            icon()
        }
    }
}

/**
 * Generic Claymorphic Chip Container accepting arbitrary composable content.
 */
@Composable
fun ClayStatusChip(
    modifier: Modifier = Modifier,
    style: ClayChipStyle = ClayChipStyle.Resting,
    backgroundColor: Color = ClayTheme.colors.surface,
    borderColor: Color? = null,
    borderWidth: Dp = 1.dp,
    shadowColor: Color? = null,
    cornerRadius: Dp = AppCorners.Pill,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val chipModifier = when (style) {
        ClayChipStyle.Resting -> {
            modifier
                .then(
                    if (onClick != null) Modifier.clayPressable(onClick = onClick)
                    else Modifier,
                )
                .clayRestingFloat(
                    backgroundColor = backgroundColor,
                    cornerRadius = cornerRadius,
                    shadowColor = shadowColor ?: ClayTheme.colors.shadowOuter,
                )
                .then(
                    if ((borderColor != null) && (borderWidth > 0.dp)) {
                        Modifier.border(borderWidth, borderColor, RoundedCornerShape(cornerRadius))
                    } else Modifier,
                )
                .padding(contentPadding)
        }
        ClayChipStyle.Embossed -> {
            modifier
                .then(
                    if (onClick != null) Modifier.clayPressable(onClick = onClick)
                    else Modifier,
                )
                .clayPillRaised(
                    backgroundColor = backgroundColor,
                    cornerRadius = cornerRadius,
                    borderColor = borderColor,
                    borderWidth = borderWidth,
                    shadowColor = shadowColor ?: ClayTheme.colors.shadowOuter,
                )
                .padding(contentPadding)
        }
    }

    Box(
        modifier = chipModifier,
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            content()
        }
    }
}
