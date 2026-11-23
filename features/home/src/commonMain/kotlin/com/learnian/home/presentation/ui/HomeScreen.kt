package com.learnian.home.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.review.ClayReviewBannerCard
import com.learnian.core.ui.component.stats.ClayStatCard
import com.learnian.core.ui.component.stats.ClayStatCardVariant
import com.learnian.core.ui.component.stats.ClayStatsRow
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.core.ui.component.toast.rememberClayToastHostState
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.home_fsrs_btn_action
import com.learnian.core.view.resources.home_fsrs_desc
import com.learnian.core.view.resources.home_fsrs_tag
import com.learnian.core.view.resources.home_fsrs_time_estimate
import com.learnian.core.view.resources.home_fsrs_title
import com.learnian.core.view.resources.home_stat_daily_goal
import com.learnian.core.view.resources.home_stat_daily_goal_progress_format
import com.learnian.core.view.resources.home_stat_new_words
import com.learnian.core.view.resources.home_stat_new_words_unit
import com.learnian.core.view.resources.home_stat_review_today
import com.learnian.core.view.resources.home_stat_review_unit
import com.learnian.core.view.resources.ic_brain_ai
import com.learnian.core.view.resources.ic_clock
import com.learnian.home.presentation.component.HomeGreetingHeader
import com.learnian.home.presentation.component.HomeOwlCoachCard
import com.learnian.home.presentation.mvi.HomeEffect
import com.learnian.home.presentation.mvi.HomeIntent
import com.learnian.home.presentation.viewmodel.HomeViewModel
import com.learnian.ui.base.BaseScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Claymorphic Home Screen built on top of [BaseScreen].
 *
 * Utilizes [LoadableUiState] with cache retention:
 * 1. [HomeGreetingHeader]: Top user greeting on canvas (avatar, greeting, level, streak badge).
 * 2. [HomeOwlCoachCard]: Learnyan Wise Owl coach speech bubble card.
 * 3. [ClayStatsRow]: 3 Scalable metric cards (Review Today, New Words, Daily Goal with progress).
 * 4. [ClayReviewBannerCard]: Scalable FSRS Smart Daily Review action hero banner.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onNavigateToReview: () -> Unit = {},
    viewModel: HomeViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val toastHostState = rememberClayToastHostState()
    val currentOnNavigateToReview by rememberUpdatedState(onNavigateToReview)

    // Listen for one-shot effects from ViewModel
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.ShowMessage -> {
                    toastHostState.showToast(message = effect.text, type = ClayToastType.Info)
                }
                is HomeEffect.NavigateToReview -> {
                    currentOnNavigateToReview()
                }
            }
        }
    }

    BaseScreen(
        state = uiState,
        isRefreshing = uiState.isRefreshing,
        toastHostState = toastHostState,
        onRefresh = { viewModel.dispatch(HomeIntent.Refresh) },
        onRetry = { viewModel.dispatch(HomeIntent.LoadDashboard) },
        modifier = modifier.fillMaxSize(),
    ) { dashboard ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
        ) {
            // 1. Top User Greeting Header (on canvas, directly on screen)
            HomeGreetingHeader(
                userName = dashboard.userName,
                streakDays = dashboard.streakDays,
                userLevel = dashboard.userLevel,
                modifier = Modifier.fillMaxWidth(),
            )

            // 2. Learnyan Wise Owl Mascot Coach Card (inside ClayCard)
            HomeOwlCoachCard(
                dueCount = dashboard.dueReviewsCount,
                estimatedMinutes = dashboard.estimatedReviewMinutes,
                retentionRate = dashboard.retentionRate,
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.dispatch(HomeIntent.StartReview) },
            )

            // 3. Scalable Quick Stats Row (Review Today, New Words, Daily Goal)
            val dailyGoalRatio = if (dashboard.dailyGoalTarget > 0) {
                (dashboard.dailyGoalCompleted.toFloat() / dashboard.dailyGoalTarget.toFloat()).coerceIn(0f, 1f)
            } else 0f
            val dailyGoalPercent = (dailyGoalRatio * 100).toInt()

            ClayStatsRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalSpacing = Spacing.Standard,
            ) {
                // Card A: Review Today (Purple)
                ClayStatCard(
                    title = stringResource(Res.string.home_stat_review_today),
                    value = dashboard.dueReviewsCount.toString(),
                    subtitle = stringResource(Res.string.home_stat_review_unit),
                    icon = {
                        BaseIcon(
                            resource = Res.drawable.ic_clock,
                            contentDescription = stringResource(Res.string.home_stat_review_today),
                            tint = ClayStatCardVariant.Purple.primaryColor,
                            modifier = Modifier.size(14.dp),
                        )
                    },
                    variant = ClayStatCardVariant.Purple,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.dispatch(HomeIntent.StartReview) },
                )

                // Card B: New Words (Mint)
                ClayStatCard(
                    title = stringResource(Res.string.home_stat_new_words),
                    value = dashboard.newWordsCount.toString(),
                    subtitle = stringResource(Res.string.home_stat_new_words_unit),
                    icon = {
                        BaseIcon(
                            resource = Res.drawable.ic_brain_ai,
                            contentDescription = stringResource(Res.string.home_stat_new_words),
                            tint = ClayStatCardVariant.Mint.primaryColor,
                            modifier = Modifier.size(14.dp),
                        )
                    },
                    variant = ClayStatCardVariant.Mint,
                    modifier = Modifier.weight(1f),
                )

                // Card C: Daily Goal (Amber)
                ClayStatCard(
                    title = stringResource(Res.string.home_stat_daily_goal),
                    trailingBadge = {
                        Text(
                            text = "$dailyGoalPercent٪",
                            style = AppTypography.titleSmall.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                            ),
                            color = ClayStatCardVariant.Amber.primaryColor,
                        )
                    },
                    subtitle = stringResource(
                        Res.string.home_stat_daily_goal_progress_format,
                        dashboard.dailyGoalCompleted.toString(),
                        dashboard.dailyGoalTarget.toString(),
                    ),
                    progress = dailyGoalRatio,
                    variant = ClayStatCardVariant.Amber,
                    modifier = Modifier.weight(1f),
                )
            }

            // 4. Scalable FSRS Daily Smart Review Hero Card
            ClayReviewBannerCard(
                tagText = stringResource(Res.string.home_fsrs_tag),
                title = stringResource(Res.string.home_fsrs_title),
                description = stringResource(Res.string.home_fsrs_desc),
                timeEstimate = stringResource(Res.string.home_fsrs_time_estimate, dashboard.estimatedReviewMinutes.toString()),
                actionText = stringResource(Res.string.home_fsrs_btn_action),
                onActionClick = { viewModel.dispatch(HomeIntent.StartReview) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
