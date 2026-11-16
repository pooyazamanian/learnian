package com.learnian.core.ui.component.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.theme.ClayTheme

/**
 * Claymorphic Screen Scaffold with floating overlays.
 *
 * Supports:
 * - Mobile portrait mode:
 *   - [topBar] floats at Alignment.TopCenter
 *   - [bottomBar] floats at Alignment.BottomCenter
 *   - Content extends full screen and scrolls *underneath* both bars ("زیر و رو رفتن صفحات")
 *   - [content] receives [PaddingValues] to clear the bars at rest position
 *
 * - Tablet / Landscape / Desktop mode:
 *   - [sidebar] docked flush on the Start edge (Right in RTL, Left in LTR).
 *     Content does not go under the sidebar.
 *   - [topBar] floats over the main content canvas, allowing content to scroll underneath it.
 */
@Composable
fun ClayScaffold(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    sidebar: (@Composable () -> Unit)? = null,
    navigationRail: (@Composable () -> Unit)? = null,
    containerColor: Color = ClayTheme.colors.background,
    content: @Composable (PaddingValues) -> Unit,
) {
    val activeSidebar = sidebar ?: navigationRail

    if (activeSidebar != null) {
        // Tablet / Landscape / Desktop:
        // Sidebar on Start edge (Right in RTL, Left in LTR), Content area alongside it.
        // Content goes UNDER the TopBar, but NOT under the Sidebar.
        Row(
            modifier = modifier
                .fillMaxSize()
                .background(containerColor),
        ) {
            // Sidebar docked to start edge
            activeSidebar()

            // Main Content Area with TopBar overlay
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            ) {
                // Content fills max size so scrolling items glide UNDER TopBar
                content(
                    PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 76.dp, // Space for TopBar at rest
                        bottom = 16.dp,
                    )
                )

                // TopBar floats over content
                if (topBar != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                    ) {
                        topBar()
                    }
                }
            }
        }
    } else {
        // Mobile Portrait:
        // TopBar floats on top, BottomBar floats on bottom.
        // Content fills entire screen and goes UNDER both bars!
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(containerColor),
        ) {
            // Content extends full screen so it scrolls UNDER TopBar and UNDER BottomBar
            content(
                PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 92.dp, // Space for floating TopBar at rest
                    bottom = 98.dp, // Space for floating BottomBar at rest
                )
            )

            // Floating TopBar
            if (topBar != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                ) {
                    topBar()
                }
            }

            // Floating BottomBar
            if (bottomBar != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                ) {
                    bottomBar()
                }
            }
        }
    }
}
