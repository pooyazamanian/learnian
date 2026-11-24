package com.learnian.stats.presentation.mvi

import com.learnian.base.LoadableUiState
import com.learnian.base.UiEffect
import com.learnian.base.UiIntent
import com.learnian.stats.domain.model.StatsDashboard

/**
 * Stats tab state.
 *
 * The dashboard snapshot is kept in [LoadableUiState] with cache retention, so a refresh
 * or a transient network error never wipes the numbers already on screen.
 */
data class StatsState(
    override val isLoading: Boolean = true,
    override val data: StatsDashboard? = null,
    override val cachedData: StatsDashboard? = null,
    override val errorMessage: String? = null,
    override val supportsCache: Boolean = true,
) : LoadableUiState<StatsDashboard>

sealed interface StatsIntent : UiIntent {
    data object LoadDashboard : StatsIntent
    data object Refresh : StatsIntent
}

sealed interface StatsEffect : UiEffect
