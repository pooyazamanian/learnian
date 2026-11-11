package com.learnian.core.ui.component.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing

/**
 * Claymorphism Container Card (clay-card)
 * Surface 1 Resting Float spec with top white highlight and bottom soft shadow spread.
 */
@Composable
fun ClayCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = ClayTheme.colors.surface,
    cornerRadius: Dp = AppCorners.XL,
    padding: Dp = Spacing.Medium,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .then(
                if (onClick != null) Modifier.clayPressable(onClick = onClick)
                else Modifier
            )
            .clayRestingFloat(
                backgroundColor = backgroundColor,
                cornerRadius = cornerRadius
            )
            .padding(padding),
        content = content
    )
}
