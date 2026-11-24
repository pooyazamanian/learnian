package com.learnian.stats.presentation.viewmodel

import com.learnian.base.BaseViewModel
import com.learnian.base.UiText
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.stats_error_load
import com.learnian.stats.domain.usecase.GetStatsDashboardUseCase
import com.learnian.stats.presentation.mvi.StatsEffect
import com.learnian.stats.presentation.mvi.StatsIntent
import com.learnian.stats.presentation.mvi.StatsState

/**
 * Loads the stats snapshot (API → bundled catalog) and keeps the last successful one in
 * state while a refresh is running.
 */
class StatsViewModel(
    private val getStatsDashboard: GetStatsDashboardUseCase,
) : BaseViewModel<StatsState, StatsIntent, StatsEffect>(StatsState()) {

    init {
        dispatch(StatsIntent.LoadDashboard)
    }

    override fun onIntent(intent: StatsIntent) {
        when (intent) {
            StatsIntent.LoadDashboard,
            StatsIntent.Refresh,
            -> loadDashboard()
        }
    }

    private fun loadDashboard() {
        updateState { copy(isLoading = true, errorMessage = null) }

        launchWithHandler {
            getStatsDashboard().fold(
                onSuccess = { dashboard ->
                    updateState {
                        copy(
                            isLoading = false,
                            data = dashboard,
                            cachedData = dashboard,
                            errorMessage = null,
                        )
                    }
                },
                onFailure = {
                    val message = UiText.Resource(Res.string.stats_error_load).asStringAsync()
                    updateState { copy(isLoading = false, errorMessage = message) }
                },
            )
        }
    }
}
