package com.learnian.core.ui.component.navrail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import org.jetbrains.compose.resources.DrawableResource

/**
 * Claymorphic Adaptive Navigation Rail (clay-nav-rail).
 *
 * Designed for tablets, foldables, landscape orientation, and desktop form factors.
 * Floats as a sleek 3D vertical pill dock along the start edge of the screen
 * (Right side in RTL / Persian, Left side in LTR / English).
 */
@Composable
fun ClayNavRail(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = AppCorners.XXL,
    header: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Vertical + WindowInsetsSides.Start))
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clayRestingFloat(
                backgroundColor = ClayTheme.colors.surface,
                cornerRadius = cornerRadius,
            )
            .width(86.dp)
            .padding(vertical = 12.dp, horizontal = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // Optional Top Header (e.g. App Logo)
            if (header != null) {
                Box(
                    modifier = Modifier.padding(bottom = 8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    header()
                }
            } else {
                Spacer(modifier = Modifier.height(2.dp))
            }

            // Scrollable Navigation Items column (prevents overflow on shorter landscape screens)
            Column(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = content,
            )

            // Optional Bottom Footer
            if (footer != null) {
                Box(
                    modifier = Modifier.padding(top = 8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    footer()
                }
            } else {
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}

/**
 * Navigation item for [ClayNavRail] with bouncy spring scale and glowing 3D pill indicator.
 */
@Composable
fun ColumnScope.ClayNavRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: DrawableResource,
    label: String,
    modifier: Modifier = Modifier,
) {
    val animatedColor by animateColorAsState(
        targetValue = if (selected) Color.White else ClayTheme.colors.textSecondary,
        label = "nav-rail-item-color",
    )

    val scale by animateFloatAsState(
        targetValue = if (selected) 1.05f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        label = "nav-rail-item-scale",
    )

    Box(
        modifier = modifier
            .clayPressable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .then(
                    if (selected) {
                        Modifier.clayRestingFloat(
                            backgroundColor = ClayTheme.colors.primary,
                            cornerRadius = AppCorners.Large,
                        )
                    } else {
                        Modifier
                    }
                )
                .size(width = 68.dp, height = 56.dp)
                .padding(horizontal = 4.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                BaseIcon(
                    resource = icon,
                    contentDescription = label,
                    tint = animatedColor,
                    modifier = Modifier.size(22.dp),
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = label,
                    style = AppTypography.labelMedium.copy(
                        fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Medium,
                        fontSize = 10.sp,
                    ),
                    color = animatedColor,
                    maxLines = 1,
                )
            }
        }
    }
}
