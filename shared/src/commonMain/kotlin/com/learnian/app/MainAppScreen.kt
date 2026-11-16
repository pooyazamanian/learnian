package com.learnian.app

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.learnian.core.ui.component.bottombar.ClayBottomBar
import com.learnian.core.ui.component.bottombar.ClayBottomNavItem
import com.learnian.core.ui.component.scaffold.ClayScaffold
import com.learnian.core.ui.component.topbar.ClayTopBar
import com.learnian.core.ui.navigation.AppRoute
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.account
import com.learnian.core.view.resources.amar
import com.learnian.core.view.resources.cards
import com.learnian.core.view.resources.home
import com.learnian.core.view.resources.learn
import com.learnian.core.view.resources.nav_home
import com.learnian.core.view.resources.nav_learn
import com.learnian.core.view.resources.nav_profile
import com.learnian.core.view.resources.nav_review
import com.learnian.core.view.resources.nav_stats
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
 * - TopBar with claymorphic status badges and branding
 * - BottomBar with localized claymorphic items
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

    ClayScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ClayTopBar(
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
        },
    ) { paddingValues ->


            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()).padding(paddingValues),
                beyondViewportPageCount = 1,
                verticalAlignment = Alignment.Top
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
                            ProfileScreen()
                        }
                    }

        }
    }
}
