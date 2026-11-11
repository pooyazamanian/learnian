package com.learnian.core.ui.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.modifiers.clayActionRaised
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.ClayTheme

/**
 * Claymorphism Action Icon Button
 */
@Composable
fun ClayIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = 48.dp,
    cornerRadius: Dp = AppCorners.Large,
    backgroundColor: Color = ClayTheme.colors.primary,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(size)
            .clayPressable(enabled = enabled, onClick = onClick)
            .clayActionRaised(
                backgroundColor = if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.4f),
                cornerRadius = cornerRadius
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
