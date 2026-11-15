package com.learnian.app

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.learnian.core.ui.component.bottombar.ClayBottomBar
import com.learnian.core.ui.component.bottombar.ClayBottomNavItem
import com.learnian.core.ui.component.scaffold.ClayScaffold
import com.learnian.core.ui.component.topbar.ClayTopBar
import com.learnian.core.ui.navigation.AppRoute
import com.learnian.home.presentation.ui.HomeScreen
import kotlinx.coroutines.launch
import learnian.shared.generated.resources.Res
import learnian.shared.generated.resources.account
import learnian.shared.generated.resources.amar
import learnian.shared.generated.resources.cards
import learnian.shared.generated.resources.home
import learnian.shared.generated.resources.learn
import org.jetbrains.compose.resources.DrawableResource

/**
 * Bottom Navigation Tabs definition.
 */
enum class MainBottomTab(
    val title: String,
    val icon: DrawableResource,
    val route: AppRoute.MainApp,
) {
    Home(
        title = "خانه",
        icon = Res.drawable.home,
        route = AppRoute.MainApp.Home,
    ),
    Services(
        title = "یادگیری",
        icon = Res.drawable.learn,
        route = AppRoute.MainApp.Services,
    ),
    Wallet(
        title = "مرور",
        icon = Res.drawable.cards,
        route = AppRoute.MainApp.Wallet,
    ),
    Profile(
        title = "آمار",
        icon = Res.drawable.amar,
        route = AppRoute.MainApp.Profile,
    ),
    Settings(
        title = "پروفایل",
        icon = Res.drawable.account,
        route = AppRoute.MainApp.Settings,
    ),
}

/**
 * MainApp container hosting:
 * - TopBar with animated title matching the active tab
 * - BottomBar with claymorphic items
 * - Swipeable HorizontalPager allowing seamless gesture navigation (swipe left/right)
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

    val currentTab = tabs[pagerState.currentPage]

    ClayScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ClayTopBar(
//                title = currentTab.title
            )
        },
        bottomBar = {
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
                                        stiffness = Spring.StiffnessMediumLow
                                    )
                                )
                            }
                        },
                        icon = tab.icon,
                        label = tab.title
                    )
                }
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            beyondViewportPageCount = 1
        ) { pageIndex ->
            when (tabs[pageIndex]) {
                MainBottomTab.Home -> {
                    HomeScreen()
                }
                MainBottomTab.Services -> {

                }
                MainBottomTab.Wallet -> {

                }
                MainBottomTab.Profile -> {

                }
                MainBottomTab.Settings -> {

                }
            }
        }
    }
}