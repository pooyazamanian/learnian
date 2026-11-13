package com.learnian.home.presentation.mvi

import com.learnian.base.UiEffect
import com.learnian.base.UiIntent
import com.learnian.base.UiState
import com.learnian.home.domain.model.HomeDashboard

data class HomeState(
    val isLoading: Boolean = true,
    val dashboard: HomeDashboard? = null,
    val errorMessage: String? = null
) : UiState

sealed interface HomeIntent : UiIntent {
    data object LoadDashboard : HomeIntent
    data object StartReview : HomeIntent
    data object Refresh : HomeIntent
}

sealed interface HomeEffect : UiEffect {
    data object NavigateToReview : HomeEffect
    data class ShowMessage(val text: String) : HomeEffect
}
