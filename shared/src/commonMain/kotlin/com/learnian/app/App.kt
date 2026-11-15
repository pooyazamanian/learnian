package com.learnian.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.learnian.app.di.initKoin
import com.learnian.core.ui.component.bottombar.ClayBottomBar
import com.learnian.core.ui.component.bottombar.ClayBottomNavItem
import com.learnian.core.ui.component.scaffold.ClayScaffold
import com.learnian.core.ui.component.topbar.ClayTopBar
import com.learnian.core.ui.navigation.AnimatedNavHost
import com.learnian.core.ui.navigation.AppRoute
import com.learnian.core.ui.navigation.rememberNavController
import com.learnian.core.ui.theme.AppPlatformTheme
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.home.presentation.ui.HomeScreen
import com.learnian.splash.presentation.ui.SplashScreen
import learnian.shared.generated.resources.Res
import learnian.shared.generated.resources.account
import learnian.shared.generated.resources.cards
import learnian.shared.generated.resources.amar
import learnian.shared.generated.resources.home
import learnian.shared.generated.resources.learn
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinContext
import org.koin.mp.KoinPlatform

@Composable
fun App() {
    if (KoinPlatform.getKoinOrNull() == null) {
        initKoin()
    }

    AppPlatformTheme {
        KoinContext {
            val rootNavController = rememberNavController<AppRoute>(AppRoute.Splash)
            val currentRoute = rootNavController.currentRoute
            val isMainAppScreen = currentRoute is AppRoute.MainApp

            ClayScaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    if (currentRoute is AppRoute.MainApp) {
                        ClayTopBar(
                            title = currentRoute.title
                        )
                    }
                },
                bottomBar = {
                    if (isMainAppScreen) {
                        ClayBottomBar {
                            ClayBottomNavItem(
                                selected = currentRoute == AppRoute.MainApp.Home,
                                onClick = { rootNavController.replace(AppRoute.MainApp.Home) },
                                icon = Res.drawable.home,
                                label = "خانه"
                            )
                            ClayBottomNavItem(
                                selected = currentRoute == AppRoute.MainApp.Services,
                                onClick = { rootNavController.replace(AppRoute.MainApp.Services) },
                                icon = Res.drawable.learn,
                                label = "یادگیری"
                            )
                            ClayBottomNavItem(
                                selected = currentRoute == AppRoute.MainApp.Wallet,
                                onClick = { rootNavController.replace(AppRoute.MainApp.Wallet) },
                                icon = Res.drawable.cards,
                                label = "مرور"
                            )
                            ClayBottomNavItem(
                                selected = currentRoute == AppRoute.MainApp.Profile,
                                onClick = { rootNavController.replace(AppRoute.MainApp.Profile) },
                                icon =Res.drawable.amar,
                                label = "آمار"
                            )
                            ClayBottomNavItem(
                                selected = currentRoute == AppRoute.MainApp.Settings,
                                onClick = { rootNavController.replace(AppRoute.MainApp.Settings) },
                                icon =  Res.drawable.account,
                                label = "پروفایل"
                            )
                        }
                    }
                }
            ) { paddingValues ->
                AnimatedNavHost(
                    currentRoute = currentRoute,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) { route ->
                    when (route) {
                        is AppRoute.Splash -> {
                            SplashScreen(
                                onSplashFinished = {
                                    rootNavController.replace(AppRoute.MainApp.Home)
                                }
                            )
                        }

                        is AppRoute.MainApp -> {
                            when (route) {
                                AppRoute.MainApp.Home, AppRoute.MainApp.Map -> {
                                    HomeScreen()
                                }
                                else -> {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = route.title,
                                            style = AppTypography.headlineLarge,
                                            color = ClayTheme.colors.textPrimary
                                        )
                                    }
                                }
                            }
                        }

                        is AppRoute.Intro -> {

                        }

                        is AppRoute.Auth -> {

                        }
                    }
                }
            }
        }
    }
}
