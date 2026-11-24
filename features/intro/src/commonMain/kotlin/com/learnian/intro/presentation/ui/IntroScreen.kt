package com.learnian.intro.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.component.toast.rememberClayToastHostState
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.intro.presentation.component.IntroBottomActions
import com.learnian.intro.presentation.component.IntroHeaderSection
import com.learnian.intro.presentation.component.IntroSlideContent
import com.learnian.intro.presentation.mvi.IntroEffect
import com.learnian.intro.presentation.mvi.IntroIntent
import com.learnian.intro.presentation.viewmodel.IntroSharedViewModel
import com.learnian.ui.base.BaseScreen
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

/**
 * Responsive 3D Claymorphic Onboarding Intro Screen with 3 multi-step slides,
 * pager indicators, and shared viewmodel state.
 */
@Composable
fun IntroScreen(
    onCompleteIntro: (isSignIn: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: IntroSharedViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val toastHostState = rememberClayToastHostState()
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.totalSlides },
    )

    // Sync Pager swipes back to ViewModel
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page != state.currentSlideIndex) {
                viewModel.dispatch(IntroIntent.SelectSlide(page))
            }
        }
    }

    // Collect ViewModel side effects
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is IntroEffect.ScrollToSlide -> {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(effect.pageIndex)
                    }
                }
                is IntroEffect.NavigateToAuth -> {
                    onCompleteIntro(effect.isSignIn)
                }
                is IntroEffect.ShowToast -> {
                    toastHostState.showToast(effect.message.asStringAsync(), effect.type)
                }
            }
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        BaseScreen(
            modifier = modifier
                .fillMaxSize()
                .background(ClayTheme.colors.background),
            isLoading = state.isLoading && state.slides.isEmpty(),
            isRefreshing = false,
            hasContent = state.slides.isNotEmpty(),
            isEmpty = false,
            toastHostState = toastHostState,
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ClayTheme.colors.background)
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .imePadding(),
                contentAlignment = Alignment.TopCenter,
            ) {
                val isWideScreen = maxWidth >= 600.dp

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (isWideScreen) Modifier.widthIn(max = 480.dp)
                            else Modifier.fillMaxWidth()
                        )
                        .padding(horizontal = AppPaddings.ScreenHorizontal)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // 1. Top Header Row (Skip + Indicator Dots)
                    IntroHeaderSection(
                        currentPage = pagerState.currentPage,
                        totalPages = state.totalSlides,
                        onSkipClick = { viewModel.dispatch(IntroIntent.SkipIntro) },
                        onDotClick = { page ->
                            viewModel.dispatch(IntroIntent.SelectSlide(page))
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page)
                            }
                        },
                        modifier = Modifier.padding(top = Spacing.Small),
                    )

                    Spacer(modifier = Modifier.height(Spacing.Small))

                    // 2. Horizontal Pager with 3 Slides
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(0.dp),
                    ) { pageIndex ->
                        val slide = state.slides.getOrNull(pageIndex)
                        if (slide != null) {
                            IntroSlideContent(
                                slide = slide,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(Spacing.Medium))

                    // 3. Bottom Actions (Primary + Secondary)
                    IntroBottomActions(
                        isLastSlide = pagerState.currentPage >= state.totalSlides - 1,
                        onPrimaryClick = { viewModel.dispatch(IntroIntent.NextSlide) },
                        onSignInClick = { viewModel.dispatch(IntroIntent.SignInExistingAccount) },
                        modifier = Modifier.padding(bottom = Spacing.Medium),
                    )
                }
            }
        }
    }
}
