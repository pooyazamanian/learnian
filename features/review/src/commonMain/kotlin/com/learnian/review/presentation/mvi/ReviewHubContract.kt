package com.learnian.review.presentation.mvi

import com.learnian.base.LoadableUiState
import com.learnian.base.UiEffect
import com.learnian.base.UiIntent
import com.learnian.base.UiText
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.review.domain.model.ReviewLibrary

/** The three sub-pages of the review tab. */
enum class ReviewSection {
    NOTEBOOK,
    DICTIONARY,
    SAVED,
}

/** Quick filters of the review notebook. */
enum class NotebookFilter {
    ALL,
    FREQUENT,
    NEEDS_REVIEW,
}

/**
 * User actions of the review hub.
 *
 * Every word is addressed by its id, so navigation never carries a whole word
 * payload: the detail screen resolves the card from the same id.
 */
sealed interface ReviewHubIntent : UiIntent {
    data object LoadLibrary : ReviewHubIntent
    data object Refresh : ReviewHubIntent

    /** Re-reads the library silently (e.g. after returning from a word detail). */
    data object SyncLibrary : ReviewHubIntent
    data class SelectSection(val section: ReviewSection) : ReviewHubIntent
    data class SelectNotebookFilter(val filter: NotebookFilter) : ReviewHubIntent
    data class SelectPartOfSpeech(val partOfSpeech: String?) : ReviewHubIntent
    data class SelectTag(val tag: String?) : ReviewHubIntent
    data class Search(val query: String) : ReviewHubIntent
    data class SetDailyGoal(val goal: Int) : ReviewHubIntent
    data class ToggleSaved(val wordId: String) : ReviewHubIntent
    data class OpenWord(val wordId: String) : ReviewHubIntent
    data class StartReview(val wordId: String) : ReviewHubIntent
}

/**
 * Immutable MVI state of the hub. The library itself is retained across
 * refresh failures, while section, filters and the daily goal live beside it.
 */
data class ReviewHubState(
    override val isLoading: Boolean = true,
    override val data: ReviewLibrary? = null,
    override val cachedData: ReviewLibrary? = null,
    override val errorMessage: String? = null,
    override val supportsCache: Boolean = true,
    val section: ReviewSection = ReviewSection.NOTEBOOK,
    val notebookFilter: NotebookFilter = NotebookFilter.ALL,
    val partOfSpeech: String? = null,
    val tag: String? = null,
    val searchQuery: String = "",
    val dailyGoal: Int = DEFAULT_DAILY_GOAL,
) : LoadableUiState<ReviewLibrary> {
    val library: ReviewLibrary? get() = safeData
}

/** One-off hub side effects: navigation and Clay toasts. */
sealed interface ReviewHubEffect : UiEffect {
    data class OpenWordDetail(val wordId: String) : ReviewHubEffect
    data class StartQuickReview(val wordId: String) : ReviewHubEffect
    data class ShowToast(
        val message: UiText,
        val type: ClayToastType = ClayToastType.Info,
    ) : ReviewHubEffect
}

/** Mirrors the datastore default, used until the stored settings arrive. */
const val DEFAULT_DAILY_GOAL: Int = 20
