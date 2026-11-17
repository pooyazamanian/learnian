package com.learnian.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import com.learnian.app.di.initKoin
import com.learnian.app.viewmodel.AppThemeViewModel
import com.learnian.auth.presentation.ui.AuthScreen
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.mp.KoinPlatform

@OptIn(ExperimentalMaterial3Api::class)
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
                Scaffold(
                    modifier = Modifier
                        .background(

                                SolidColor(ClayTheme.colors.background)

                        )
                        .imePadding()
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .statusBarsPadding()
                        .windowInsetsPadding(WindowInsets.navigationBars),
                    containerColor = Color.Transparent,
                ) {
                    when (route) {
                        is AppRoute.Splash -> {
                            SplashScreen(
                                onSplashFinished = {
                                    // When backend auth is live: redirect to Auth if not logged in
                                    // val tokenStorage: com.learnian.core.network.auth.TokenStorage = org.koin.mp.KoinPlatform.getKoin().get()
                                    // val hasValidToken = tokenStorage.getToken() != null
                                    // rootNavController.replace(if (hasValidToken) AppRoute.MainApp.Home else AppRoute.Auth)
                                    rootNavController.replace(AppRoute.Auth.PhoneInput)
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
                            AuthScreen(
                                onAuthSuccess = {
                                    rootNavController.replace(AppRoute.MainApp.Home)
                                },
                            )
                        }

                        else -> {
                            // Root Pull-To-Refresh animation structure active across all app sections
                            var isRefreshing by remember { mutableStateOf(false) }
                            val coroutineScope = rememberCoroutineScope()

                            PullToRefreshBox(
                                isRefreshing = isRefreshing,
                                onRefresh = {
                                    coroutineScope.launch {
                                        isRefreshing = true
                                        delay(1000)
                                        isRefreshing = false
                                    }
                                },
                                modifier = Modifier.fillMaxSize(),
                            ) {
                                when (route) {
                                    is AppRoute.MainApp -> {
                                        MainAppScreen(
                                            initialRoute = route,
                                            onNavigateToRoute = { destination ->
                                                rootNavController.navigate(destination)
                                            },
                                        )
                                    }
                                    else -> {
                                        // Fallback for future routes
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
