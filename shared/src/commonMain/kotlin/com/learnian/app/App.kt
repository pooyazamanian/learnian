package com.learnian.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.learnian.app.di.initKoin
import com.learnian.core.ui.navigation.AnimatedNavHost
import com.learnian.core.ui.navigation.AppRoute
import com.learnian.core.ui.navigation.appRouteTransitionSpec
import com.learnian.core.ui.navigation.rememberNavController
import com.learnian.core.ui.theme.AppPlatformTheme
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.splash.presentation.ui.SplashScreen
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

            AnimatedNavHost(
                currentRoute = currentRoute,
                transitionSpec = appRouteTransitionSpec,
                modifier = Modifier.fillMaxSize()
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
                        MainAppScreen(
                            initialRoute = route,
                            onNavigateToRoute = { destination ->
                                rootNavController.navigate(destination)
                            }
                        )
                    }

                    is AppRoute.Intro -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "راهنما و معرفی",
                                style = AppTypography.headlineLarge,
                                color = ClayTheme.colors.textPrimary
                            )
                        }
                    }

                    is AppRoute.Auth -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "احراز هویت و ورود",
                                style = AppTypography.headlineLarge,
                                color = ClayTheme.colors.textPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}
