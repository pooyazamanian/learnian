package com.learnian.review.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.learnian.base.UiText
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.core.ui.component.toast.rememberClayToastHostState
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.review_rated_success
import com.learnian.review.presentation.component.ReviewCardContent
import com.learnian.review.presentation.component.ReviewCoachBanner
import com.learnian.review.presentation.component.ReviewFsrsRatingBar
import com.learnian.review.presentation.component.ReviewTopHeader
import com.learnian.review.presentation.mvi.ReviewEffect
import com.learnian.review.presentation.mvi.ReviewIntent
import com.learnian.review.presentation.viewmodel.ReviewViewModel
import com.learnian.ui.base.BaseScreen
import org.koin.compose.viewmodel.koinViewModel

/**
 * Daily review flashcard screen.
 *
 * Receives the word id from navigation, loads it through [ReviewViewModel]
 * (server → offline catalog) and lets the user grade it with the FSRS bar or
 * open the full word-detail screen.
 */
@Composable
fun WordReviewScreen(
    wordId: String,
    onBackClick: () -> Unit,
    onNavigateToWordDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val toastHostState = rememberClayToastHostState()
    val toastScope = rememberCoroutineScope()

    LaunchedEffect(wordId) {
        viewModel.dispatch(ReviewIntent.LoadWord(wordId))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ReviewEffect.NavigateBack -> onBackClick()
                is ReviewEffect.NavigateToWordDetail -> onNavigateToWordDetail(effect.wordId)
                is ReviewEffect.WordRatedSuccessfully -> {
                    val message = UiText.Resource(Res.string.review_rated_success).asStringAsync()
                    toastHostState.show(toastScope, message, ClayToastType.Success)
                }

                is ReviewEffect.ShowToast -> {
                    val message = effect.message.asStringAsync()
                    toastHostState.show(toastScope, message, effect.type)
                }
            }
        }
    }

    BaseScreen(
        state = state,
        isRefreshing = state.isRefreshing,
        toastHostState = toastHostState,
        onRefresh = { viewModel.dispatch(ReviewIntent.RefreshWord) },
        onRetry = { viewModel.dispatch(ReviewIntent.LoadWord(wordId)) },
        modifier = modifier.fillMaxSize(),
    ) { word ->
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
                verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
            ) {
                ReviewTopHeader(
                    progressInfo = word.reviewProgress,
                    level = word.level,
                    lesson = word.lesson,
                    onBackClick = { viewModel.dispatch(ReviewIntent.NavigateBack) },
                )

                ReviewCoachBanner(progressInfo = word.reviewProgress)

                ReviewCardContent(
                    wordData = word,
                    onToggleBookmark = { viewModel.dispatch(ReviewIntent.ToggleBookmark) },
                    onPlayAudio = { locale -> viewModel.dispatch(ReviewIntent.PlayAudio(locale)) },
                    onViewDetailClick = { viewModel.dispatch(ReviewIntent.NavigateToWordDetail) },
                    bookmarkEnabled = !state.isBookmarkUpdating,
                )

                Spacer(modifier = Modifier.height(Spacing.Small))
            }

            ReviewFsrsRatingBar(
                intervals = word.fsrsIntervals,
                onRateClick = { rating -> viewModel.dispatch(ReviewIntent.RateWord(rating)) },
                enabled = !state.isRating,
                modifier = Modifier.padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
            )
        }
    }
}
