package com.learnian.app

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.bottombar.ClayBottomBar
import com.learnian.core.ui.component.bottombar.ClayBottomNavItem
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.component.scaffold.ClayScaffold
import com.learnian.core.ui.component.sidebar.ClaySidebar
import com.learnian.core.ui.component.sidebar.ClaySidebarItem
import com.learnian.core.ui.component.topbar.ClayTopBar
import com.learnian.core.ui.navigation.AppRoute
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.account
import com.learnian.core.view.resources.amar
import com.learnian.core.view.resources.app_name
import com.learnian.core.view.resources.cards
import com.learnian.core.view.resources.home
import com.learnian.core.view.resources.ic_learnian_eye_logo
import com.learnian.core.view.resources.learn
import com.learnian.core.view.resources.nav_home
import com.learnian.core.view.resources.nav_learn
import com.learnian.core.view.resources.nav_profile
import com.learnian.core.view.resources.nav_review
import com.learnian.core.view.resources.nav_stats
import com.learnian.core.view.resources.topbar_logo_desc
import com.learnian.home.presentation.ui.HomeScreen
import com.learnian.profile.presentation.ui.ProfileScreen
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Bottom Navigation Tabs definition with localized title resources.
 */
enum class MainBottomTab(
    val titleRes: StringResource,
    val icon: DrawableResource,
    val route: AppRoute.MainApp,
) {
    Home(
        titleRes = Res.string.nav_home,
        icon = Res.drawable.home,
        route = AppRoute.MainApp.Home,
    ),
    Services(
        titleRes = Res.string.nav_learn,
        icon = Res.drawable.learn,
        route = AppRoute.MainApp.Services,
    ),
    Wallet(
        titleRes = Res.string.nav_review,
        icon = Res.drawable.cards,
        route = AppRoute.MainApp.Wallet,
    ),
    Profile(
        titleRes = Res.string.nav_stats,
        icon = Res.drawable.amar,
        route = AppRoute.MainApp.Profile,
    ),
    Settings(
        titleRes = Res.string.nav_profile,
        icon = Res.drawable.account,
        route = AppRoute.MainApp.Settings,
    ),
}

/**
 * MainApp container hosting:
 * - Floating Bars layout:
 *   - Mobile Portrait: Floating [ClayBottomBar] + Floating [ClayTopBar] over the content canvas,
 *     allowing screens to scroll underneath both bars.
 *   - Tablet / Landscape / Desktop: Solid flat [ClaySidebar] docked on Start edge
 *     (Right for Persian RTL, Left for English LTR), with [ClayTopBar] floating over content.
 * - Swipeable [HorizontalPager] for smooth tab transitions.
 */
@Composable
fun MainAppScreen(
    modifier: Modifier = Modifier,
    initialRoute: AppRoute.MainApp = AppRoute.MainApp.Home,
    onNavigateToRoute: ((AppRoute) -> Unit)? = null,
) {
    val tabs = remember { MainBottomTab.entries }
    val initialIndex = remember(initialRoute) {
        tabs.indexOfFirst { it.route == initialRoute }.coerceAtLeast(0)
    }

    val pagerState = rememberPagerState(
        initialPage = initialIndex,
    ) { tabs.size }
    val coroutineScope = rememberCoroutineScope()

    // Sync pager if initialRoute changes externally
    LaunchedEffect(initialRoute) {
        val targetIndex = tabs.indexOfFirst { it.route == initialRoute }
        if ((targetIndex >= 0) && (targetIndex != pagerState.currentPage)) {
            pagerState.animateScrollToPage(targetIndex)
        }
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isExpanded = maxWidth >= 600.dp || (maxWidth > maxHeight && maxWidth >= 480.dp)

        ClayScaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                ClayTopBar(
                    flat = isExpanded,
                    showLogo = !isExpanded,
                    title = if (isExpanded) stringResource(tabs[pagerState.currentPage].titleRes) else null,
                    onProfileClick = {
                        val settingsIndex = tabs.indexOf(MainBottomTab.Settings)
                        if (settingsIndex >= 0) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    page = settingsIndex,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioLowBouncy,
                                        stiffness = Spring.StiffnessMediumLow,
                                    ),
                                )
                            }
                        }
                    },
                )
            },
            bottomBar = if (!isExpanded) {
                {
                    ClayBottomBar {
                        tabs.forEachIndexed { index, tab ->
                            ClayBottomNavItem(
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(
                                            page = index,
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioLowBouncy,
                                                stiffness = Spring.StiffnessMediumLow,
                                            ),
                                        )
                                    }
                                },
                                icon = tab.icon,
                                label = stringResource(tab.titleRes),
                            )
                        }
                    }
                }
            } else null,
            sidebar = if (isExpanded) {
                {
                    ClaySidebar(
                        width = 240.dp,
                        header = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                BaseImage(
                                    resource = Res.drawable.ic_learnian_eye_logo,
                                    contentDescription = stringResource(Res.string.topbar_logo_desc),
                                    modifier = Modifier.size(36.dp),
                                )
                                Text(
                                    text = stringResource(Res.string.app_name),
                                    style = AppTypography.headlineMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 18.sp,
                                    ),
                                    color = ClayTheme.colors.textPrimary,
                                )
                            }
                        },
                    ) {
                        tabs.forEachIndexed { index, tab ->
                            ClaySidebarItem(
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(
                                            page = index,
                                            animationSpec = spring(
                                                dampingRatio = Spring.DampingRatioLowBouncy,
                                                stiffness = Spring.StiffnessMediumLow,
                                            ),
                                        )
                                    }
                                },
                                icon = tab.icon,
                                label = stringResource(tab.titleRes),
                            )
                        }
                    }
                }
            } else null,
        ) { contentPadding ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .then(if (isExpanded) Modifier.widthIn(max = 840.dp) else Modifier)
                        .fillMaxWidth(),
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                        beyondViewportPageCount = 1,
                        verticalAlignment = Alignment.Top,
                    ) { pageIndex ->
                        when (tabs[pageIndex]) {
                            MainBottomTab.Home -> HomeScreen(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = contentPadding,
                                onNavigateToReview = {
                                    onNavigateToRoute?.invoke(AppRoute.WordReview())
                                },
                                onNavigateToQuiz = {
                                    onNavigateToRoute?.invoke(AppRoute.Quiz)
                                },
                            )
                            MainBottomTab.Services -> {}
                            MainBottomTab.Wallet -> {}
                            MainBottomTab.Profile -> {}
                            MainBottomTab.Settings -> ProfileScreen(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = contentPadding,
                            )
                        }
                    }
                }
            }
        }
    }
}
