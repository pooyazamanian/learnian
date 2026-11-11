package com.learnian.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.learnian.app.di.initKoin
import com.learnian.core.ui.navigation.AnimatedNavHost
import com.learnian.core.ui.navigation.AppRoute
import com.learnian.core.ui.navigation.rememberNavController
import com.learnian.core.ui.theme.AppPlatformTheme
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
                modifier = Modifier.fillMaxSize()
            ) { route ->
                when (route) {
                    is AppRoute.Splash -> {
                        SplashScreen(
                            onSplashFinished = {
                                rootNavController.replace(AppRoute.Intro.Welcome)
                            }
                        )
                    }

                    is AppRoute.MainApp -> {

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
