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
import com.learnian.intro.domain.usecase.CheckIntroCompletedUseCase
import com.learnian.intro.presentation.ui.IntroScreen
import com.learnian.learning.presentation.ui.PackageDetailsScreen
import com.learnian.learning.presentation.ui.PackageSettingsScreen
import com.learnian.quiz.presentation.ui.QuizScreen
import com.learnian.review.presentation.ui.WordDetailScreen
import com.learnian.review.presentation.ui.WordReviewScreen
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
        val appScope = rememberCoroutineScope()

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
                                    appScope.launch {
                                        val checkIntroUseCase: CheckIntroCompletedUseCase = KoinPlatform.getKoin().get()
                                        val isCompleted = checkIntroUseCase()
                                        if (!isCompleted) {
                                            rootNavController.replace(AppRoute.Intro.Welcome)
                                        } else {
                                            rootNavController.replace(AppRoute.Auth.PhoneInput)
                                        }
                                    }
                                },
                            )
                        }

                        is AppRoute.Intro -> {
                            IntroScreen(
                                onCompleteIntro = { _ ->
                                    rootNavController.replace(AppRoute.Auth.PhoneInput)
                                },
                            )
                        }

                        is AppRoute.Auth -> {
                            AuthScreen(
                                onAuthSuccess = {
                                    rootNavController.replace(AppRoute.MainApp.Home)
                                },
                            )
                        }

                        is AppRoute.WordReview -> {
                            WordReviewScreen(
                                wordId = route.wordId,
                                onBackClick = { rootNavController.pop() },
                                onNavigateToWordDetail = { wordId ->
                                    rootNavController.navigate(AppRoute.WordDetail(wordId = wordId))
                                },
                            )
                        }

                        is AppRoute.WordDetail -> {
                            WordDetailScreen(
                                wordId = route.wordId,
                                onBackClick = { rootNavController.pop() },
                            )
                        }

                        is AppRoute.Quiz -> {
                            QuizScreen(
                                onBackClick = { rootNavController.pop() },
                            )
                        }

                        is AppRoute.PackageDetails -> {
                            PackageDetailsScreen(
                                packageId = route.packageId,
                                onBackClick = { rootNavController.pop() },
                                onOpenSettings = { packageId ->
                                    rootNavController.navigate(AppRoute.PackageSettings(packageId))
                                },
                            )
                        }

                        is AppRoute.PackageSettings -> {
                            PackageSettingsScreen(
                                packageId = route.packageId,
                                onBackClick = { rootNavController.pop() },
                            )
                        }

                        is AppRoute.MainApp -> {
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
                                MainAppScreen(
                                    initialRoute = route,
                                    onNavigateToRoute = { destination ->
                                        rootNavController.navigate(destination)
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
