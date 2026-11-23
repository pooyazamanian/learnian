package com.learnian.review.presentation.mvi

import com.learnian.base.LoadableUiState
import com.learnian.base.UiEffect
import com.learnian.base.UiIntent
import com.learnian.base.UiText
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.review.domain.model.FsrsRating
import com.learnian.review.domain.model.WordReviewData

/**
 * User actions accepted by the review flow (flashcard + word detail).
 */
sealed interface ReviewIntent : UiIntent {
    /** Loads the word identified by [wordId] (navigation argument). */
    data class LoadWord(val wordId: String) : ReviewIntent

    /** Re-fetches the currently displayed word while keeping it on screen. */
    data object RefreshWord : ReviewIntent

    /** Submits a 4-tier FSRS grade. */
    data class RateWord(val rating: FsrsRating) : ReviewIntent

    /** Toggles the bookmark flag of the current word. */
    data object ToggleBookmark : ReviewIntent

    /** Plays the pronunciation of the given accent locale ("US" / "UK"). */
    data class PlayAudio(val locale: String) : ReviewIntent

    /** Opens the deep-dive word detail screen for the current word. */
    data object NavigateToWordDetail : ReviewIntent

    /** Pops the current screen. */
    data object NavigateBack : ReviewIntent
}

/**
 * Immutable MVI state with cache retention: a failed refresh or rating never wipes
 * the word that is already visible on screen.
 */
data class ReviewState(
    override val isLoading: Boolean = true,
    override val data: WordReviewData? = null,
    override val cachedData: WordReviewData? = null,
    override val errorMessage: String? = null,
    override val supportsCache: Boolean = true,
    val currentWordId: String = "",
    val isRating: Boolean = false,
    val isBookmarkUpdating: Boolean = false,
    val isAudioPlaying: Boolean = false,
    val playingLocale: String? = null,
) : LoadableUiState<WordReviewData> {
    val wordData: WordReviewData? get() = safeData
}

/**
 * One-off side effects (navigation and Clay toasts).
 */
sealed interface ReviewEffect : UiEffect {
    data object NavigateBack : ReviewEffect
    data class NavigateToWordDetail(val wordId: String) : ReviewEffect
    data class ShowToast(
        val message: UiText,
        val type: ClayToastType = ClayToastType.Info,
    ) : ReviewEffect

    data object WordRatedSuccessfully : ReviewEffect
}
