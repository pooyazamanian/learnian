package com.learnian.home.presentation.mvi

import com.learnian.base.LoadableUiState
import com.learnian.base.UiEffect
import com.learnian.base.UiIntent
import com.learnian.home.domain.model.HomeDashboard

/**
 * State representing Home presentation layer with offline-first cache retention.
 */
data class HomeState(
    override val isLoading: Boolean = true,
    override val data: HomeDashboard? = null,
    override val cachedData: HomeDashboard? = null,
    override val errorMessage: String? = null,
    override val supportsCache: Boolean = true,
    override val isRefreshing: Boolean = false,
) : LoadableUiState<HomeDashboard> {
    /**
     * Backward-compatible accessor for [HomeDashboard].
     * Returns fresh data or fallback cached data if [supportsCache] is true.
     */
    val dashboard: HomeDashboard?
        get() = safeData
}

sealed interface HomeIntent : UiIntent {
    data object LoadDashboard : HomeIntent
    data object StartReview : HomeIntent
    data object StartQuiz : HomeIntent
    data object Refresh : HomeIntent
}

sealed interface HomeEffect : UiEffect {
    data object NavigateToReview : HomeEffect
    data object NavigateToQuiz : HomeEffect
    data class ShowMessage(val text: String) : HomeEffect
}
