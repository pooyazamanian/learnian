package com.learnian.review.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.learnian.core.ui.component.toast.rememberClayToastHostState
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.Spacing
import com.learnian.review.presentation.component.hub.ReviewDictionarySection
import com.learnian.review.presentation.component.hub.ReviewGoalCard
import com.learnian.review.presentation.component.hub.ReviewNotebookSection
import com.learnian.review.presentation.component.hub.ReviewSavedSection
import com.learnian.review.presentation.component.hub.ReviewSectionTabs
import com.learnian.review.presentation.mvi.ReviewHubEffect
import com.learnian.review.presentation.mvi.ReviewHubIntent
import com.learnian.review.presentation.mvi.ReviewSection
import com.learnian.review.presentation.viewmodel.ReviewHubViewModel
import com.learnian.ui.base.BaseScreen
import org.koin.compose.viewmodel.koinViewModel

/**
 * Review tab: daily new-word goal, the three sub-pages (notebook, dictionary,
 * saved words) and their word cards.
 *
 * The global top bar and bottom navigation belong to the host scaffold, so this
 * screen only renders inside the content inset it receives. Every word card is
 * opened by id through [onNavigateToWordDetail]; the quick-review action opens
 * the existing flashcard flow through [onStartReview].
 */
@Composable
fun ReviewHubScreen(
    onNavigateToWordDetail: (String) -> Unit,
    onStartReview: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = AppPaddings.ScreenHorizontal,
        vertical = AppPaddings.ScreenVertical,
    ),
    viewModel: ReviewHubViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val toastHostState = rememberClayToastHostState()
    val toastScope = rememberCoroutineScope()

    // Coming back from a word detail can change a bookmark, so re-read silently.
    LaunchedEffect(Unit) {
        viewModel.dispatch(ReviewHubIntent.SyncLibrary)
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ReviewHubEffect.OpenWordDetail -> onNavigateToWordDetail(effect.wordId)
                is ReviewHubEffect.StartQuickReview -> onStartReview(effect.wordId)
                is ReviewHubEffect.ShowToast -> toastHostState.show(
                    toastScope,
                    effect.message.asStringAsync(),
                    effect.type,
                )
            }
        }
    }

    BaseScreen(
        state = uiState,
        isRefreshing = uiState.isRefreshing,
        toastHostState = toastHostState,
        onRefresh = { viewModel.dispatch(ReviewHubIntent.Refresh) },
        onRetry = { viewModel.dispatch(ReviewHubIntent.LoadLibrary) },
        modifier = modifier.fillMaxSize(),
    ) { library ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
        ) {
            ReviewGoalCard(
                dailyGoal = uiState.dailyGoal,
                onDailyGoalChange = { goal ->
                    viewModel.dispatch(ReviewHubIntent.SetDailyGoal(goal))
                },
            )

            ReviewSectionTabs(
                selectedSection = uiState.section,
                onSectionSelected = { section ->
                    viewModel.dispatch(ReviewHubIntent.SelectSection(section))
                },
            )

            when (uiState.section) {
                ReviewSection.NOTEBOOK -> ReviewNotebookSection(
                    library = library,
                    filter = uiState.notebookFilter,
                    onFilterChange = { filter ->
                        viewModel.dispatch(ReviewHubIntent.SelectNotebookFilter(filter))
                    },
                    onWordClick = { wordId ->
                        viewModel.dispatch(ReviewHubIntent.OpenWord(wordId))
                    },
                    onQuickReview = { wordId ->
                        viewModel.dispatch(ReviewHubIntent.StartReview(wordId))
                    },
                )

                ReviewSection.DICTIONARY -> ReviewDictionarySection(
                    library = library,
                    partOfSpeech = uiState.partOfSpeech,
                    query = uiState.searchQuery,
                    onPartOfSpeechChange = { partOfSpeech ->
                        viewModel.dispatch(ReviewHubIntent.SelectPartOfSpeech(partOfSpeech))
                    },
                    onQueryChange = { query ->
                        viewModel.dispatch(ReviewHubIntent.Search(query))
                    },
                    onWordClick = { wordId ->
                        viewModel.dispatch(ReviewHubIntent.OpenWord(wordId))
                    },
                )

                ReviewSection.SAVED -> ReviewSavedSection(
                    library = library,
                    tag = uiState.tag,
                    query = uiState.searchQuery,
                    onTagChange = { tag ->
                        viewModel.dispatch(ReviewHubIntent.SelectTag(tag))
                    },
                    onQueryChange = { query ->
                        viewModel.dispatch(ReviewHubIntent.Search(query))
                    },
                    onToggleSaved = { wordId ->
                        viewModel.dispatch(ReviewHubIntent.ToggleSaved(wordId))
                    },
                    onWordClick = { wordId ->
                        viewModel.dispatch(ReviewHubIntent.OpenWord(wordId))
                    },
                    onStartReview = { wordId ->
                        viewModel.dispatch(ReviewHubIntent.StartReview(wordId))
                    },
                )
            }

            Spacer(modifier = Modifier.height(Spacing.Small))
        }
    }
}
