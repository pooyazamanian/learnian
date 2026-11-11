package com.learnian.splash.presentation.viewmodel

import com.learnian.base.BaseViewModel
import com.learnian.splash.domain.usecase.GetSplashConfigUseCase
import com.learnian.splash.presentation.mvi.SplashEffect
import com.learnian.splash.presentation.mvi.SplashIntent
import com.learnian.splash.presentation.mvi.SplashState
import kotlinx.coroutines.delay

class SplashViewModel(
    private val getSplashConfigUseCase: GetSplashConfigUseCase
) : BaseViewModel<SplashState, SplashIntent, SplashEffect>(
    initialState = SplashState()
) {

    init {
        dispatch(SplashIntent.StartInitialization)
    }

    override fun onIntent(intent: SplashIntent) {
        when (intent) {
            SplashIntent.StartInitialization -> initializeSplash()
            SplashIntent.OnRetry -> initializeSplash()
        }
    }

    private fun initializeSplash() {
        updateState { copy(isLoading = true, errorMessage = null, progress = 0f) }
        launchWithHandler(
            onError = { throwable ->
                updateState { copy(isLoading = false, errorMessage = throwable.message) }
                sendEffect(SplashEffect.ShowErrorToast(throwable.message ?: "Initialization error"))
            }
        ) {
            val result = getSplashConfigUseCase()
            val config = result.getOrNull()

            val totalSteps = 20
            val delayMs = 100L
            for (step in 1..totalSteps) {
                delay(delayMs)
                updateState { copy(progress = step.toFloat() / totalSteps) }
            }

            delay(300)
            updateState { copy(isLoading = false, isCompleted = true, config = config) }
            sendEffect(SplashEffect.NavigateToNext)
        }
    }
}
