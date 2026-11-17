package com.learnian.core.ui.component.sidebar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.VerticalDivider
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
import com.learnian.core.ui.modifiers.clayActionRaised
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import org.jetbrains.compose.resources.DrawableResource

/**
 * Claymorphic Dashboard Sidebar Drawer (clay-sidebar).
 *
 * A flat, solid dashboard panel for tablets, landscape mode, and desktop.
 * Sits flush against the start edge of the screen (Right in RTL / Persian, Left in LTR / English),
 * replacing the bottom bar with a full-height solid dashboard navigation panel.
 */
@Composable
fun ClaySidebar(
    modifier: Modifier = Modifier,
    width: Dp = 250.dp,
    header: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    backgroundColor: Color = ClayTheme.colors.surface,
    dividerColor: Color = ClayTheme.colors.surfaceSunken.copy(alpha = 0.6f),
    content: @Composable ColumnScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxHeight()
            .width(width)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(backgroundColor)
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Vertical + WindowInsetsSides.Start))
                .padding(vertical = 16.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // Top Section (Header + Scrollable Nav items)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            ) {
                if (header != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    ) {
                        header()
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    content = content,
                )
            }

            // Bottom Section (Footer / Account / Info)
            if (footer != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    footer()
                }
            }
        }

        // Clean border dividing sidebar from the main content canvas
        VerticalDivider(
            color = dividerColor,
            thickness = 1.dp,
            modifier = Modifier.fillMaxHeight(),
        )
    }
}

/**
 * Horizontal Row Item for [ClaySidebar], featuring icon + title side by side
 * and an active claymorphic raised pill when selected.
 */
@Composable
fun ColumnScope.ClaySidebarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: DrawableResource,
    label: String,
    modifier: Modifier = Modifier,
    badge: (@Composable () -> Unit)? = null,
) {
    val animatedTextColor by animateColorAsState(
        targetValue = if (selected) Color.White else ClayTheme.colors.textPrimary,
        label = "sidebar-item-text-color",
    )

    val animatedIconColor by animateColorAsState(
        targetValue = if (selected) Color.White else ClayTheme.colors.primary,
        label = "sidebar-item-icon-color",
    )

    val scale by animateFloatAsState(
        targetValue = if (selected) 1.02f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        label = "sidebar-item-scale",
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clayPressable(onClick = onClick)
            .then(
                if (selected) {
                    Modifier.clayActionRaised(
                        backgroundColor = ClayTheme.colors.primary,
                        cornerRadius = AppCorners.Large,
                        shadowColor = ClayTheme.colors.shadowOuter,
                    )
                } else {
                    Modifier
                }
            )
            .height(50.dp)
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                BaseIcon(
                    resource = icon,
                    contentDescription = label,
                    tint = animatedIconColor,
                    modifier = Modifier.size(22.dp),
                )

                Text(
                    text = label,
                    style = AppTypography.titleMedium.copy(
                        fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Bold,
                        fontSize = 15.sp,
                    ),
                    color = animatedTextColor,
                )
            }

            if (badge != null) {
                badge()
            }
        }
    }
}
