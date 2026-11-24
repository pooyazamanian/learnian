package com.learnian.quiz.presentation.viewmodel

import com.learnian.base.BaseViewModel
import com.learnian.base.UiText
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.quiz_error_load
import com.learnian.core.view.resources.quiz_error_submit
import com.learnian.quiz.domain.model.QuizAnswer
import com.learnian.quiz.domain.model.QuizPayload
import com.learnian.quiz.domain.usecase.GetQuizSessionUseCase
import com.learnian.quiz.domain.usecase.SubmitQuizAnswerUseCase
import com.learnian.quiz.presentation.mvi.QuizEffect
import com.learnian.quiz.presentation.mvi.QuizIntent
import com.learnian.quiz.presentation.mvi.QuizState
import kotlinx.coroutines.delay

/**
 * Drives one quiz round.
 *
 * - Loads the round (API → bundled catalog) and resumes from the server provided index.
 * - Collects the answer per format: an option id for choice/listening/sentence questions,
 *   typed letters for spelling questions.
 * - Grades through [SubmitQuizAnswerUseCase] and advances to the next question.
 */
class QuizViewModel(
    private val getQuizSession: GetQuizSessionUseCase,
    private val submitQuizAnswer: SubmitQuizAnswerUseCase,
) : BaseViewModel<QuizState, QuizIntent, QuizEffect>(QuizState()) {

    init {
        dispatch(QuizIntent.LoadSession)
    }

    override fun onIntent(intent: QuizIntent) {
        when (intent) {
            QuizIntent.LoadSession,
            QuizIntent.Refresh,
            -> loadSession()

            is QuizIntent.SelectOption -> selectOption(intent.optionId)
            is QuizIntent.AppendLetter -> appendLetter(intent.letter)
            QuizIntent.UndoLetter -> undoLetter()
            QuizIntent.ToggleAudio -> playAudio()
            is QuizIntent.SetPlaybackSpeed -> updateState { copy(playbackSpeed = intent.speed) }
            QuizIntent.SubmitAnswer -> submitAnswer()
            QuizIntent.NextQuestion -> goToNextQuestion()
            QuizIntent.NavigateBack -> sendEffect(QuizEffect.NavigateBack)
        }
    }

    private fun loadSession() {
        updateState { copy(isLoading = true, errorMessage = null) }

        launchWithHandler {
            getQuizSession().fold(
                onSuccess = { session ->
                    updateState {
                        copy(
                            isLoading = false,
                            data = session,
                            cachedData = session,
                            errorMessage = null,
                            currentIndex = session.resolvedIndex,
                            selectedOptionId = null,
                            typedLetters = emptyList(),
                            isAnswerChecked = false,
                            isAnswerCorrect = null,
                            correctAnswer = null,
                        )
                    }
                },
                onFailure = {
                    val message = UiText.Resource(Res.string.quiz_error_load).asStringAsync()
                    updateState { copy(isLoading = false, errorMessage = message) }
                },
            )
        }
    }

    private fun selectOption(optionId: String) {
        if (currentState.isAnswerChecked) return
        updateState { copy(selectedOptionId = optionId) }
    }

    private fun appendLetter(letter: String) {
        val state = currentState
        if (state.isAnswerChecked) return
        val body = state.question?.payload as? QuizPayload.Spelling ?: return
        if (state.typedLetters.size >= body.missingIndices.size) return

        updateState { copy(typedLetters = typedLetters + letter.uppercase()) }
    }

    private fun undoLetter() {
        val state = currentState
        if (state.isAnswerChecked || state.typedLetters.isEmpty()) return

        updateState { copy(typedLetters = typedLetters.dropLast(1)) }
    }

    /**
     * Reflects a short playing pulse on the audio control.
     *
     * The stream itself arrives with the API (`audio_url`); until then the control only
     * mirrors the playing state so the interaction and timing are already in place.
     */
    private fun playAudio() {
        if (currentState.isAudioPlaying) return

        updateState { copy(isAudioPlaying = true) }
        launchWithHandler {
            delay(AUDIO_PULSE_MS)
            updateState { copy(isAudioPlaying = false) }
        }
    }

    private fun submitAnswer() {
        val state = currentState
        val question = state.question ?: return
        if (state.isAnswerChecked || state.isSubmitting) return

        val answer = when (val body = question.payload) {
            is QuizPayload.Choice -> QuizAnswer.Option(state.selectedOptionId ?: return)
            is QuizPayload.Spelling -> QuizAnswer.Spelling(body.assemble(state.typedLetters))
        }

        updateState { copy(isSubmitting = true) }

        launchWithHandler(
            onError = {
                updateState { copy(isSubmitting = false) }
                sendEffect(
                    QuizEffect.ShowToast(
                        message = UiText.Resource(Res.string.quiz_error_submit),
                        type = ClayToastType.Error,
                    ),
                )
            },
        ) {
            val result = submitQuizAnswer(question, answer)
            updateState {
                copy(
                    isSubmitting = false,
                    isAnswerChecked = true,
                    isAnswerCorrect = result.isCorrect,
                    correctAnswer = result.correctAnswer,
                )
            }
        }
    }

    private fun goToNextQuestion() {
        val state = currentState
        val session = state.session ?: return

        val isLastLoaded = state.currentIndex >= session.questions.lastIndex
        val isLastInRound = state.currentIndex >= session.resolvedTotal - 1
        if (isLastLoaded || isLastInRound) {
            sendEffect(QuizEffect.QuizCompleted)
            return
        }

        updateState {
            copy(
                currentIndex = currentIndex + 1,
                selectedOptionId = null,
                typedLetters = emptyList(),
                isAnswerChecked = false,
                isAnswerCorrect = null,
                correctAnswer = null,
            )
        }
    }

    private companion object {
        const val AUDIO_PULSE_MS = 1_200L
    }
}
