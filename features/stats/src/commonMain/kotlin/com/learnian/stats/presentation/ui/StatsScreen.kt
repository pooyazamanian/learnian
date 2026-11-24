package com.learnian.stats.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.learnian.core.ui.component.toast.rememberClayToastHostState
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.Spacing
import com.learnian.stats.presentation.component.StatsDailyGoalBanner
import com.learnian.stats.presentation.component.StatsMasteryCard
import com.learnian.stats.presentation.component.StatsMemoryCard
import com.learnian.stats.presentation.component.StatsMetricsSection
import com.learnian.stats.presentation.component.StatsStabilityCard
import com.learnian.stats.presentation.component.StatsWeeklyActivityCard
import com.learnian.stats.presentation.mvi.StatsIntent
import com.learnian.stats.presentation.viewmodel.StatsViewModel
import com.learnian.ui.base.BaseScreen
import org.koin.compose.viewmodel.koinViewModel

/**
 * Stats tab: memory status, four metric cards, weekly activity, mastery split, stability
 * and the daily goal banner — in that order, all driven by one server snapshot.
 *
 * The global top bar and bottom navigation are provided by the host scaffold, so this
 * screen only scrolls inside the content inset it receives.
 */
@Composable
fun StatsScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = AppPaddings.ScreenHorizontal,
        vertical = AppPaddings.ScreenVertical,
    ),
    viewModel: StatsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val toastHostState = rememberClayToastHostState()

    BaseScreen(
        state = uiState,
        isRefreshing = uiState.isRefreshing,
        toastHostState = toastHostState,
        onRefresh = { viewModel.dispatch(StatsIntent.Refresh) },
        onRetry = { viewModel.dispatch(StatsIntent.LoadDashboard) },
        modifier = modifier.fillMaxSize(),
    ) { dashboard ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
        ) {
            StatsMemoryCard(
                memory = dashboard.memory,
                modifier = Modifier.fillMaxWidth(),
            )

            StatsMetricsSection(
                vocabulary = dashboard.vocabulary,
                accuracy = dashboard.accuracy,
                streak = dashboard.streak,
                modifier = Modifier.fillMaxWidth(),
            )

            StatsWeeklyActivityCard(
                activity = dashboard.weeklyActivity,
                modifier = Modifier.fillMaxWidth(),
            )

            StatsMasteryCard(
                mastery = dashboard.mastery,
                modifier = Modifier.fillMaxWidth(),
            )

            StatsStabilityCard(
                stability = dashboard.stability,
                modifier = Modifier.fillMaxWidth(),
            )

            StatsDailyGoalBanner(
                goal = dashboard.dailyGoal,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(Spacing.Small))
        }
    }
}
