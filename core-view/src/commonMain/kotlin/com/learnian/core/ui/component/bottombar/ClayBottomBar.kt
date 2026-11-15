package com.learnian.core.ui.component.bottombar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.modifiers.clayActionRaised
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * Claymorphism Floating Pill Dock Bottom Navigation Bar
 */
@Composable
fun ClayBottomBar(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = AppCorners.Pill,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clayRestingFloat(
                backgroundColor = ClayTheme.colors.surface,
                cornerRadius = cornerRadius
            )
            .height(72.dp)
            .padding(horizontal = 6.dp),
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
    icon: DrawableResource,
    label: String,
    modifier: Modifier = Modifier
) {
    val animatedLabelColor by animateColorAsState(
        targetValue = if (selected) Color.White else ClayTheme.colors.textSecondary
    )

    Box(
        modifier = modifier
            .weight(1f)
            .clayPressable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {


        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 2.dp).let{
                    if (selected) {
                        it.clayRestingFloat(
                            backgroundColor = ClayTheme.colors.primary,
                            cornerRadius = AppCorners.Pill
                        )
                    } else {
                        it
                    }
                }
                .size(55.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "خانه",
                    tint = animatedLabelColor
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = label,
                    style = AppTypography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    ),
                    color =animatedLabelColor
                )
            }
        }
    }
}
