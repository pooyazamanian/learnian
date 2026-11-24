package com.learnian.intro.presentation.mvi

import com.learnian.base.LoadableUiState
import com.learnian.base.UiEffect
import com.learnian.base.UiIntent
import com.learnian.base.UiText
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.intro.domain.model.IntroSlide

/**
 * User actions and events for Intro onboarding.
 */
sealed interface IntroIntent : UiIntent {
    data object LoadSlides : IntroIntent
    data object NextSlide : IntroIntent
    data object PreviousSlide : IntroIntent
    data class SelectSlide(val pageIndex: Int) : IntroIntent
    data object SkipIntro : IntroIntent
    data object CompleteIntro : IntroIntent
    data object SignInExistingAccount : IntroIntent
}

/**
 * Immutable UI State for the Intro flow implementing [LoadableUiState].
 */
data class IntroState(
    override val isLoading: Boolean = false,
    override val data: List<IntroSlide>? = null,
    override val cachedData: List<IntroSlide>? = null,
    override val errorMessage: String? = null,
    override val supportsCache: Boolean = true,
    val currentSlideIndex: Int = 0,
) : LoadableUiState<List<IntroSlide>> {
    val slides: List<IntroSlide> get() = safeData ?: emptyList()
    val totalSlides: Int get() = slides.size.coerceAtLeast(1)
    val isLastSlide: Boolean get() = currentSlideIndex >= totalSlides - 1
    val currentSlide: IntroSlide? get() = slides.getOrNull(currentSlideIndex)
}

/**
 * One-off side effects emitted by the Shared ViewModel.
 */
sealed interface IntroEffect : UiEffect {
    data class ScrollToSlide(val pageIndex: Int) : IntroEffect
    data class NavigateToAuth(val isSignIn: Boolean = false) : IntroEffect
    data class ShowToast(val message: UiText, val type: ClayToastType = ClayToastType.Info) : IntroEffect
}
