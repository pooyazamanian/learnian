package com.learnian.splash.presentation.mvi

import com.learnian.base.UiEffect
import com.learnian.base.UiIntent
import com.learnian.base.UiState
import com.learnian.splash.domain.model.SplashConfig

data class SplashState(
    val isLoading: Boolean = true,
    val progress: Float = 0f,
    val config: SplashConfig? = null,
    val isCompleted: Boolean = false,
    val errorMessage: String? = null
) : UiState

sealed interface SplashIntent : UiIntent {
    data object StartInitialization : SplashIntent
    data object OnRetry : SplashIntent
}

sealed interface SplashEffect : UiEffect {
    data object NavigateToNext : SplashEffect
    data class ShowErrorToast(val message: String) : SplashEffect
}
