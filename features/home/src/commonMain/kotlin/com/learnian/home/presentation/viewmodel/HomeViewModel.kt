package com.learnian.home.presentation.viewmodel

import com.learnian.base.BaseViewModel
import com.learnian.home.domain.usecase.GetHomeDashboardUseCase
import com.learnian.home.presentation.mvi.HomeEffect
import com.learnian.home.presentation.mvi.HomeIntent
import com.learnian.home.presentation.mvi.HomeState

class HomeViewModel(
    private val getHomeDashboardUseCase: GetHomeDashboardUseCase,
) : BaseViewModel<HomeState, HomeIntent, HomeEffect>(
    initialState = HomeState(),
) {

    init {
        dispatch(HomeIntent.LoadDashboard)
    }

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadDashboard -> loadDashboardData(isRefresh = false)
            HomeIntent.Refresh -> loadDashboardData(isRefresh = true)
            HomeIntent.StartReview -> {
                sendEffect(HomeEffect.NavigateToReview)
            }

            HomeIntent.StartQuiz -> {
                sendEffect(HomeEffect.NavigateToQuiz)
            }
        }
    }

    private fun loadDashboardData(isRefresh: Boolean = false) {
        val current = currentState
        val existingData = current.safeData ?: current.cachedData

        updateState {
            copy(
                isLoading = !isRefresh,
                isRefreshing = isRefresh,
                errorMessage = null,
                cachedData = existingData, // Preserve cache during reload!
            )
        }

        launchWithHandler(
            onError = { throwable ->
                updateState {
                    copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = throwable.message ?: "خطا در دریافت اطلاعات",
                    )
                }
                sendEffect(HomeEffect.ShowMessage(throwable.message ?: "خطا در دریافت اطلاعات"))
            }
        ) {
            val result = getHomeDashboardUseCase()
            val dashboard = result.getOrNull()
            if (dashboard != null) {
                updateState {
                    copy(
                        isLoading = false,
                        isRefreshing = false,
                        data = dashboard,
                        cachedData = dashboard,
                        errorMessage = null,
                    )
                }
            } else {
                updateState {
                    copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = "داده‌ای یافت نشد",
                    )
                }
            }
        }
    }
}
