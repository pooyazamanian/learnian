package com.learnian.core.ui.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.modifiers.clayActionRaised
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme

enum class ClayButtonVariant {
    Primary,
    Secondary,
    Tertiary,
    Error,
    Surface,
}

/**
 * Claymorphism Action Button (clay-btn)
 * Surface 2 Action Raised specification with active press animation (scale to 0.96).
 *
 * Supports semantic variants:
 * - [ClayButtonVariant.Primary]: Primary brand lavender
 * - [ClayButtonVariant.Secondary]: Mint green
 * - [ClayButtonVariant.Tertiary]: Warm amber
 * - [ClayButtonVariant.Error]: Peach / Red
 * - [ClayButtonVariant.Surface]: White / Light surface 3D raised (e.g. Google SSO, secondary cards)
 */
@Composable
fun ClayButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: ClayButtonVariant = ClayButtonVariant.Primary,
    cornerRadius: Dp = AppCorners.Large,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
    content: @Composable RowScope.() -> Unit
) {
    val backgroundColor = when (variant) {
        ClayButtonVariant.Primary -> ClayTheme.colors.primary
        ClayButtonVariant.Secondary -> ClayTheme.colors.secondary
        ClayButtonVariant.Tertiary -> ClayTheme.colors.tertiary
        ClayButtonVariant.Error -> ClayTheme.colors.error
        ClayButtonVariant.Surface -> ClayTheme.colors.surface
    }

    val contentColor = when (variant) {
        ClayButtonVariant.Tertiary -> Color(0xFF1F1000)
        ClayButtonVariant.Surface -> ClayTheme.colors.textPrimary
        else -> Color.White
    }

    val finalBackgroundColor = if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.4f)

    Row(
        modifier = modifier
            .clayPressable(enabled = enabled, onClick = onClick)
            .clayActionRaised(
                backgroundColor = finalBackgroundColor,
                cornerRadius = cornerRadius,
                shadowColor = ClayTheme.colors.shadowOuter,
            )
            .defaultMinSize(minHeight = 48.dp)
            .padding(contentPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProvideTextStyle(value = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold, color = contentColor)) {
            content()
        }
    }
}

/**
 * Convenience ClayButton with text, optional leading and trailing icons.
 */
@Composable
fun ClayButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: ClayButtonVariant = ClayButtonVariant.Primary,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    cornerRadius: Dp = AppCorners.Large,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
) {
    ClayButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        variant = variant,
        cornerRadius = cornerRadius,
        contentPadding = contentPadding,
    ) {
        if (leadingIcon != null) {
            leadingIcon()
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text = text)
        if (trailingIcon != null) {
            Spacer(modifier = Modifier.width(8.dp))
            trailingIcon()
        }
    }
}
