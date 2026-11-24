package com.learnian.intro.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.learnian.base.BaseSharedViewModel
import com.learnian.intro.domain.usecase.CompleteIntroUseCase
import com.learnian.intro.domain.usecase.GetIntroSlidesUseCase
import com.learnian.intro.presentation.mvi.IntroEffect
import com.learnian.intro.presentation.mvi.IntroIntent
import com.learnian.intro.presentation.mvi.IntroState
import kotlinx.coroutines.launch

/**
 * Shared ViewModel coordinating the 3 onboarding slides, pager state, and persistence.
 *
 * Inherits [BaseSharedViewModel] to allow sharing state across multi-step intro pages.
 */
class IntroSharedViewModel(
    private val getIntroSlidesUseCase: GetIntroSlidesUseCase,
    private val completeIntroUseCase: CompleteIntroUseCase,
) : BaseSharedViewModel<IntroState, IntroIntent, IntroEffect>(IntroState()) {

    init {
        dispatch(IntroIntent.LoadSlides)
    }

    override fun onIntent(intent: IntroIntent) {
        when (intent) {
            is IntroIntent.LoadSlides -> loadSlides()
            is IntroIntent.NextSlide -> onNextSlide()
            is IntroIntent.PreviousSlide -> onPreviousSlide()
            is IntroIntent.SelectSlide -> updateState { copy(currentSlideIndex = intent.pageIndex) }
            is IntroIntent.SkipIntro -> completeAndNavigate(isSignIn = false)
            is IntroIntent.CompleteIntro -> completeAndNavigate(isSignIn = false)
            is IntroIntent.SignInExistingAccount -> completeAndNavigate(isSignIn = true)
        }
    }

    private fun loadSlides() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, errorMessage = null) }
            getIntroSlidesUseCase().fold(
                onSuccess = { slides ->
                    updateState {
                        copy(
                            isLoading = false,
                            data = slides,
                            cachedData = slides,
                            errorMessage = null,
                        )
                    }
                },
                onFailure = { error ->
                    updateState {
                        copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Failed to load intro slides",
                        )
                    }
                },
            )
        }
    }

    private fun onNextSlide() {
        val nextIndex = currentState.currentSlideIndex + 1
        if (nextIndex < currentState.totalSlides) {
            updateState { copy(currentSlideIndex = nextIndex) }
            broadcastEvent(IntroEffect.ScrollToSlide(nextIndex))
        } else {
            completeAndNavigate(isSignIn = false)
        }
    }

    private fun onPreviousSlide() {
        val prevIndex = currentState.currentSlideIndex - 1
        if (prevIndex >= 0) {
            updateState { copy(currentSlideIndex = prevIndex) }
            broadcastEvent(IntroEffect.ScrollToSlide(prevIndex))
        }
    }

    private fun completeAndNavigate(isSignIn: Boolean) {
        viewModelScope.launch {
            completeIntroUseCase()
            broadcastEvent(IntroEffect.NavigateToAuth(isSignIn = isSignIn))
        }
    }
}
