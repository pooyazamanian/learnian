package com.learnian.quiz.presentation.mvi

import com.learnian.base.LoadableUiState
import com.learnian.base.UiEffect
import com.learnian.base.UiIntent
import com.learnian.base.UiText
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.quiz.domain.model.QuizPayload
import com.learnian.quiz.domain.model.QuizQuestion
import com.learnian.quiz.domain.model.QuizSession

/**
 * Quiz screen state.
 *
 * The loaded round is kept in [LoadableUiState] with cache retention, while everything
 * that describes the current interaction (question position, picked option, typed
 * letters, grading result) lives next to it. Nothing here is persisted: a reopened
 * screen asks the server for the round again.
 */
data class QuizState(
    override val isLoading: Boolean = true,
    override val data: QuizSession? = null,
    override val cachedData: QuizSession? = null,
    override val errorMessage: String? = null,
    override val supportsCache: Boolean = true,
    val currentIndex: Int = 0,
    val selectedOptionId: String? = null,
    val typedLetters: List<String> = emptyList(),
    val isAnswerChecked: Boolean = false,
    val isAnswerCorrect: Boolean? = null,
    val correctAnswer: String? = null,
    val isSubmitting: Boolean = false,
    val isAudioPlaying: Boolean = false,
    val playbackSpeed: Float = NORMAL_SPEED,
) : LoadableUiState<QuizSession> {

    val session: QuizSession?
        get() = safeData

    val question: QuizQuestion?
        get() = session?.questions?.getOrNull(currentIndex)

    /** Size of the whole round as reported by the server (not just the loaded page). */
    val totalCount: Int
        get() = session?.resolvedTotal ?: 0

    val progress: Float
        get() = if (totalCount <= 0) 0f else (currentIndex + 1).toFloat() / totalCount

    val isLastQuestion: Boolean
        get() = session?.let {
            val lastLoaded = it.questions.lastIndex.coerceAtLeast(0)
            currentIndex >= minOf(it.resolvedTotal - 1, lastLoaded)
        } ?: false

    /** True once the learner provided enough input for the active format. */
    val canSubmit: Boolean
        get() = when (val body = question?.payload) {
            is QuizPayload.Choice -> selectedOptionId != null
            is QuizPayload.Spelling -> typedLetters.size >= body.missingIndices.size
            null -> false
        }

    companion object {
        const val NORMAL_SPEED = 1f
        const val SLOW_SPEED = 0.75f
    }
}

sealed interface QuizIntent : UiIntent {
    data object LoadSession : QuizIntent
    data object Refresh : QuizIntent
    data class SelectOption(val optionId: String) : QuizIntent
    data class AppendLetter(val letter: String) : QuizIntent
    data object UndoLetter : QuizIntent
    data object ToggleAudio : QuizIntent
    data class SetPlaybackSpeed(val speed: Float) : QuizIntent
    data object SubmitAnswer : QuizIntent
    data object NextQuestion : QuizIntent
    data object NavigateBack : QuizIntent
}

sealed interface QuizEffect : UiEffect {
    data object NavigateBack : QuizEffect
    data object QuizCompleted : QuizEffect
    data class ShowToast(
        val message: UiText,
        val type: ClayToastType = ClayToastType.Error,
    ) : QuizEffect
}
