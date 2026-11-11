package com.learnian.core.ui.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    Error
}

/**
 * Claymorphism Action Button (clay-btn)
 * Surface 2 Action Raised specification with active press animation (scale to 0.96).
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
    }

    val contentColor = when (variant) {
        ClayButtonVariant.Tertiary -> Color(0xFF1F1000)
        else -> Color.White
    }

    val finalBackgroundColor = if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.4f)

    Row(
        modifier = modifier
            .clayPressable(enabled = enabled, onClick = onClick)
            .clayActionRaised(
                backgroundColor = finalBackgroundColor,
                cornerRadius = cornerRadius
            )
            .defaultMinSize(minHeight = 48.dp)
            .padding(contentPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProvideTextStyle(value = AppTypography.titleLarge.copy(color = contentColor)) {
            content()
        }
    }
}
