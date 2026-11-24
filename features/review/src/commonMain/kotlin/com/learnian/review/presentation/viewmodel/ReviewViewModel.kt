package com.learnian.review.presentation.viewmodel

import com.learnian.base.BaseViewModel
import com.learnian.base.UiText
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.review_audio_playing
import com.learnian.core.view.resources.review_bookmark_added
import com.learnian.core.view.resources.review_bookmark_removed
import com.learnian.core.view.resources.review_error_bookmark
import com.learnian.core.view.resources.review_error_load
import com.learnian.core.view.resources.review_error_rate
import com.learnian.review.domain.model.FsrsRating
import com.learnian.review.domain.usecase.GetWordDetailUseCase
import com.learnian.review.domain.usecase.RateWordUseCase
import com.learnian.review.domain.usecase.ToggleBookmarkUseCase
import com.learnian.review.presentation.mvi.ReviewEffect
import com.learnian.review.presentation.mvi.ReviewIntent
import com.learnian.review.presentation.mvi.ReviewState
import kotlinx.coroutines.delay

/**
 * Shared ViewModel behind the flashcard review screen and the word-detail screen.
 *
 * - Loads a word by its navigation id (remote API → offline catalog).
 * - Keeps previously loaded data on refresh/error (cache retention).
 * - Grades FSRS, toggles bookmarks and plays pronunciation via intents.
 */
class ReviewViewModel(
    private val getWordDetailUseCase: GetWordDetailUseCase,
    private val rateWordUseCase: RateWordUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
) : BaseViewModel<ReviewState, ReviewIntent, ReviewEffect>(ReviewState()) {

    override fun onIntent(intent: ReviewIntent) {
        when (intent) {
            is ReviewIntent.LoadWord -> loadWord(wordId = intent.wordId, force = false)
            is ReviewIntent.RefreshWord -> loadWord(wordId = currentState.currentWordId, force = true)
            is ReviewIntent.RateWord -> rateWord(intent.rating)
            is ReviewIntent.ToggleBookmark -> toggleBookmark()
            is ReviewIntent.PlayAudio -> playAudio(intent.locale)
            is ReviewIntent.NavigateToWordDetail -> {
                val wordId = currentState.wordData?.wordId ?: currentState.currentWordId
                if (wordId.isNotBlank()) {
                    sendEffect(ReviewEffect.NavigateToWordDetail(wordId))
                }
            }

            is ReviewIntent.NavigateBack -> sendEffect(ReviewEffect.NavigateBack)
        }
    }

    private fun loadWord(wordId: String, force: Boolean) {
        if (wordId.isBlank()) return
        if (!force && currentState.currentWordId == wordId && currentState.wordData != null) return

        val isSameWord = currentState.currentWordId == wordId
        val visibleWord = if (isSameWord) currentState.safeData else null
        updateState {
            copy(
                isLoading = true,
                currentWordId = wordId,
                data = if (isSameWord) data else null,
                cachedData = visibleWord,
                errorMessage = null,
            )
        }

        launchWithHandler {
            getWordDetailUseCase(wordId).fold(
                onSuccess = { word ->
                    updateState {
                        copy(
                            isLoading = false,
                            data = word,
                            cachedData = word,
                            errorMessage = null,
                        )
                    }
                },
                onFailure = {
                    val message = UiText.Resource(Res.string.review_error_load).asStringAsync()
                    updateState {
                        copy(
                            isLoading = false,
                            errorMessage = message,
                        )
                    }
                },
            )
        }
    }

    private fun rateWord(rating: FsrsRating) {
        val word = currentState.wordData ?: return
        if (currentState.isRating) return

        updateState { copy(isRating = true) }

        launchWithHandler {
            rateWordUseCase(word.wordId, rating).fold(
                onSuccess = {
                    updateState { copy(isRating = false) }
                    sendEffect(ReviewEffect.WordRatedSuccessfully)
                },
                onFailure = {
                    updateState { copy(isRating = false) }
                    sendEffect(
                        ReviewEffect.ShowToast(
                            message = UiText.Resource(Res.string.review_error_rate),
                            type = ClayToastType.Error,
                        ),
                    )
                },
            )
        }
    }

    private fun toggleBookmark() {
        val word = currentState.wordData ?: return
        if (currentState.isBookmarkUpdating) return

        updateState { copy(isBookmarkUpdating = true) }

        launchWithHandler {
            toggleBookmarkUseCase(word.wordId).fold(
                onSuccess = { isBookmarked ->
                    val updated = word.copy(isBookmarked = isBookmarked)
                    updateState {
                        copy(
                            isBookmarkUpdating = false,
                            data = updated,
                            cachedData = updated,
                        )
                    }
                    sendEffect(
                        ReviewEffect.ShowToast(
                            message = UiText.Resource(
                                if (isBookmarked) {
                                    Res.string.review_bookmark_added
                                } else {
                                    Res.string.review_bookmark_removed
                                },
                            ),
                            type = ClayToastType.Success,
                        ),
                    )
                },
                onFailure = {
                    updateState { copy(isBookmarkUpdating = false) }
                    sendEffect(
                        ReviewEffect.ShowToast(
                            message = UiText.Resource(Res.string.review_error_bookmark),
                            type = ClayToastType.Error,
                        ),
                    )
                },
            )
        }
    }

    private fun playAudio(locale: String) {
        if (currentState.isAudioPlaying) return

        updateState { copy(isAudioPlaying = true, playingLocale = locale) }
        sendEffect(
            ReviewEffect.ShowToast(
                message = UiText.Resource(Res.string.review_audio_playing, locale),
                type = ClayToastType.Info,
            ),
        )

        launchWithHandler {
            delay(1_200)
            updateState { copy(isAudioPlaying = false, playingLocale = null) }
        }
    }
}
