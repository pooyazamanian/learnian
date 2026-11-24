package com.learnian.review.presentation.viewmodel

import com.learnian.base.BaseViewModel
import com.learnian.base.UiText
import com.learnian.core.datastore.repository.UserSettingsRepository
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.review_error_bookmark
import com.learnian.core.view.resources.review_error_library
import com.learnian.core.view.resources.review_goal_updated_format
import com.learnian.core.view.resources.review_saved_added
import com.learnian.core.view.resources.review_saved_removed
import com.learnian.review.domain.model.buildReviewLibrary
import com.learnian.review.domain.usecase.GetReviewLibraryUseCase
import com.learnian.review.domain.usecase.ToggleBookmarkUseCase
import com.learnian.review.presentation.mvi.ReviewHubEffect
import com.learnian.review.presentation.mvi.ReviewHubIntent
import com.learnian.review.presentation.mvi.ReviewHubState
import kotlinx.coroutines.Job

/**
 * ViewModel of the review tab (notebook, dictionary and saved words).
 *
 * - Loads one library snapshot and keeps it on screen when a refresh fails.
 * - Filters are pure UI state; the words themselves are never refetched for
 *   a filter change.
 * - The daily new-words goal is read from and written to the shared user
 *   settings, so the review tab and the profile screen always agree.
 */
class ReviewHubViewModel(
    private val getReviewLibrary: GetReviewLibraryUseCase,
    private val toggleBookmark: ToggleBookmarkUseCase,
    private val userSettingsRepository: UserSettingsRepository,
) : BaseViewModel<ReviewHubState, ReviewHubIntent, ReviewHubEffect>(ReviewHubState()) {

    init {
        dispatch(ReviewHubIntent.LoadLibrary)
        observeDailyGoal()
    }

    override fun onIntent(intent: ReviewHubIntent) {
        when (intent) {
            ReviewHubIntent.LoadLibrary,
            ReviewHubIntent.Refresh,
            -> loadLibrary()

            ReviewHubIntent.SyncLibrary -> syncLibrary()

            is ReviewHubIntent.SelectSection -> updateState {
                copy(section = intent.section, searchQuery = "")
            }

            is ReviewHubIntent.SelectNotebookFilter -> updateState {
                copy(notebookFilter = intent.filter)
            }

            is ReviewHubIntent.SelectPartOfSpeech -> updateState {
                copy(partOfSpeech = intent.partOfSpeech)
            }

            is ReviewHubIntent.SelectTag -> updateState { copy(tag = intent.tag) }
            is ReviewHubIntent.Search -> updateState { copy(searchQuery = intent.query) }
            is ReviewHubIntent.SetDailyGoal -> updateDailyGoal(intent.goal)
            is ReviewHubIntent.ToggleSaved -> toggleSaved(intent.wordId)
            is ReviewHubIntent.OpenWord -> sendEffect(
                ReviewHubEffect.OpenWordDetail(intent.wordId),
            )

            is ReviewHubIntent.StartReview -> sendEffect(
                ReviewHubEffect.StartQuickReview(intent.wordId),
            )
        }
    }

    private fun observeDailyGoal() {
        launchWithHandler {
            userSettingsRepository.userSettingsFlow.collect { settings ->
                updateState { copy(dailyGoal = settings.dailyWordGoal) }
            }
        }
    }

    private var loadJob: Job? = null

    private fun loadLibrary() {
        updateState { copy(isLoading = true, errorMessage = null) }

        loadJob = launchWithHandler {
            getReviewLibrary().fold(
                onSuccess = { library ->
                    updateState {
                        copy(
                            isLoading = false,
                            data = library,
                            cachedData = library,
                            errorMessage = null,
                        )
                    }
                },
                onFailure = {
                    val message = UiText.Resource(Res.string.review_error_library).asStringAsync()
                    updateState { copy(isLoading = false, errorMessage = message) }
                },
            )
        }
    }

    /**
     * Silent reload used when the tab becomes visible again: the bookmark state
     * may have changed on the word detail screen, and a spinner would be noise
     * for an in-memory read.
     */
    private fun syncLibrary() {
        if (loadJob?.isActive == true) return

        loadJob = launchWithHandler {
            getReviewLibrary().onSuccess { library ->
                updateState {
                    copy(
                        isLoading = false,
                        data = library,
                        cachedData = library,
                        errorMessage = null,
                    )
                }
            }
        }
    }

    private fun updateDailyGoal(goal: Int) {
        if (goal == currentState.dailyGoal) return

        updateState { copy(dailyGoal = goal) }
        launchWithHandler {
            userSettingsRepository.setDailyWordGoal(goal)
            sendEffect(
                ReviewHubEffect.ShowToast(
                    message = UiText.Resource(Res.string.review_goal_updated_format, goal),
                    type = ClayToastType.Success,
                ),
            )
        }
    }

    private fun toggleSaved(wordId: String) {
        launchWithHandler {
            toggleBookmark(wordId).fold(
                onSuccess = { isSaved ->
                    updateState {
                        val library = data ?: return@updateState this
                        val words = library.words.map { word ->
                            if (word.wordId == wordId) word.copy(isSaved = isSaved) else word
                        }
                        val updated = buildReviewLibrary(words)
                        copy(data = updated, cachedData = updated)
                    }

                    sendEffect(
                        ReviewHubEffect.ShowToast(
                            message = UiText.Resource(
                                if (isSaved) {
                                    Res.string.review_saved_added
                                } else {
                                    Res.string.review_saved_removed
                                },
                            ),
                            type = ClayToastType.Success,
                        ),
                    )
                },
                onFailure = {
                    sendEffect(
                        ReviewHubEffect.ShowToast(
                            message = UiText.Resource(Res.string.review_error_bookmark),
                            type = ClayToastType.Error,
                        ),
                    )
                },
            )
        }
    }
}
