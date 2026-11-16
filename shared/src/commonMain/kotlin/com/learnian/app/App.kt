package com.learnian.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.learnian.app.di.initKoin
import com.learnian.app.viewmodel.AppThemeViewModel
import com.learnian.core.ui.navigation.AnimatedNavHost
import com.learnian.core.ui.navigation.AppRoute
import com.learnian.core.ui.navigation.appRouteTransitionSpec
import com.learnian.core.ui.navigation.rememberNavController
import com.learnian.core.ui.theme.AppPlatformTheme
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res as CoreRes
import com.learnian.core.view.resources.auth_title
import com.learnian.core.view.resources.intro_title
import com.learnian.splash.presentation.ui.SplashScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.mp.KoinPlatform

@Composable
fun App() {
    if (KoinPlatform.getKoinOrNull() == null) {
        initKoin()
    }

    KoinContext {
        val themeViewModel: AppThemeViewModel = koinViewModel()
        val userSettings by themeViewModel.userSettings.collectAsState()
        val rootNavController = rememberNavController<AppRoute>(AppRoute.Splash)
        val currentRoute = rootNavController.currentRoute

        AppPlatformTheme(userSettings = userSettings) {
            AnimatedNavHost(
                currentRoute = currentRoute,
                transitionSpec = appRouteTransitionSpec,
                modifier = Modifier.fillMaxSize(),
            ) { route ->
                when (route) {
                    is AppRoute.Splash -> {
                        SplashScreen(
                            onSplashFinished = {
                                rootNavController.replace(AppRoute.MainApp.Home)
                            },
                        )
                    }

                    is AppRoute.MainApp -> {
                        MainAppScreen(
                            initialRoute = route,
                            onNavigateToRoute = { destination ->
                                rootNavController.navigate(destination)
                            },
                        )
                    }

                    is AppRoute.Intro -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = stringResource(CoreRes.string.intro_title),
                                style = AppTypography.headlineLarge,
                                color = ClayTheme.colors.textPrimary,
                            )
                        }
                    }

                    is AppRoute.Auth -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = stringResource(CoreRes.string.auth_title),
                                style = AppTypography.headlineLarge,
                                color = ClayTheme.colors.textPrimary,
                            )
                        }
                    }
                }
            }
        }
    }
}
