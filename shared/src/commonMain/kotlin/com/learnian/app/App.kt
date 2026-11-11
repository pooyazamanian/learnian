package com.learnian.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.learnian.app.di.initKoin
import com.learnian.core.view.auth.AuthScreen
import com.learnian.core.view.intro.IntroScreen
import com.learnian.core.view.lite.MainLiteAppScreen
import com.learnian.core.view.main.MainAppScreen
import com.learnian.core.view.splash.SplashScreen
import com.learnian.core.view.ui.navigation.AnimatedNavHost
import com.learnian.core.view.ui.navigation.AppRoute
import com.learnian.core.view.ui.navigation.rememberNavController
import com.learnian.core.view.ui.theme.AppPlatformTheme
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
                modifier = Modifier.fillMaxSize()
            ) { route ->
                when (route) {
                    is AppRoute.Splash -> {
                        SplashScreen(
                            navController = rootNavController,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is AppRoute.MainApp -> {
                        MainAppScreen(
                            currentRoute = route,
                            navController = rootNavController,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is AppRoute.Intro -> {
                        IntroScreen(
                            currentRoute = route,
                            navController = rootNavController,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is AppRoute.Auth -> {
                        AuthScreen(
                            currentRoute = route,
                            navController = rootNavController,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is AppRoute.LiteApp -> {
                        MainLiteAppScreen(
                            currentRoute = route,
                            navController = rootNavController,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
