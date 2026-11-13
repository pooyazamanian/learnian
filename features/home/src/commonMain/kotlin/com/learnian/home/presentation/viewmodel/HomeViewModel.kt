package com.learnian.home.presentation.viewmodel

import com.learnian.base.BaseViewModel
import com.learnian.home.domain.usecase.GetHomeDashboardUseCase
import com.learnian.home.presentation.mvi.HomeEffect
import com.learnian.home.presentation.mvi.HomeIntent
import com.learnian.home.presentation.mvi.HomeState

class HomeViewModel(
    private val getHomeDashboardUseCase: GetHomeDashboardUseCase
) : BaseViewModel<HomeState, HomeIntent, HomeEffect>(
    initialState = HomeState()
) {

    init {
        dispatch(HomeIntent.LoadDashboard)
    }

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadDashboard, HomeIntent.Refresh -> loadDashboardData()
            HomeIntent.StartReview -> {
                sendEffect(HomeEffect.NavigateToReview)
            }
        }
    }

    private fun loadDashboardData() {
        updateState { copy(isLoading = true, errorMessage = null) }
        launchWithHandler(
            onError = { throwable ->
                updateState { copy(isLoading = false, errorMessage = throwable.message) }
                sendEffect(HomeEffect.ShowMessage(throwable.message ?: "خطا در دریافت اطلاعات"))
            }
        ) {
            val result = getHomeDashboardUseCase()
            val dashboard = result.getOrNull()
            updateState {
                copy(
                    isLoading = false,
                    dashboard = dashboard
                )
            }
        }
    }
}
