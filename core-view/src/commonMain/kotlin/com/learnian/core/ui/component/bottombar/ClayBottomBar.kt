package com.learnian.core.ui.component.bottombar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.modifiers.clayActionRaised
import com.learnian.core.ui.modifiers.clayInsetGrooved
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme

/**
 * Claymorphism Sunken Dock Bottom Navigation Bar
 */
@Composable
fun ClayBottomBar(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = AppCorners.XXL,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clayInsetGrooved(
                backgroundColor = ClayTheme.colors.surfaceSunken,
                cornerRadius = cornerRadius
            )
            .height(72.dp)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
fun RowScope.ClayBottomNavItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    val animatedLabelColor by animateColorAsState(
        targetValue = if (selected) ClayTheme.colors.primary else ClayTheme.colors.textSecondary
    )

    Box(
        modifier = modifier
            .weight(1f)
            .clayPressable(onClick = onClick)
            .then(
                if (selected) {
                    Modifier.clayActionRaised(
                        backgroundColor = ClayTheme.colors.surface,
                        cornerRadius = AppCorners.Large
                    )
                } else Modifier
            )
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon()
            if (selected) {
                Text(
                    text = label,
                    style = AppTypography.labelMedium,
                    color = animatedLabelColor,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
}
