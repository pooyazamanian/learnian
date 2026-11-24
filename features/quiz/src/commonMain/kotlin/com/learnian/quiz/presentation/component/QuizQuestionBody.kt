package com.learnian.quiz.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.learnian.quiz.domain.model.QuizPayload
import com.learnian.quiz.domain.model.QuizQuestion
import com.learnian.quiz.domain.model.QuizQuestionType
import com.learnian.quiz.presentation.mvi.QuizState

/**
 * Single entry point of the question body.
 *
 * The payload decides which body is rendered; the format only separates the two
 * direction variants (word -> meaning and meaning -> word) that share a payload shape.
 */
@Composable
fun QuizQuestionBody(
    question: QuizQuestion,
    state: QuizState,
    onSelectOption: (String) -> Unit,
    onAppendLetter: (String) -> Unit,
    onUndoLetter: () -> Unit,
    onPlayAudio: () -> Unit,
    onSpeedSelected: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (val body = question.payload) {
        is QuizPayload.MeaningChoice -> if (question.type == QuizQuestionType.FA_TO_EN) {
            MeaningToWordQuestionBody(
                question = question,
                body = body,
                selectedOptionId = state.selectedOptionId,
                isAnswerChecked = state.isAnswerChecked,
                onSelectOption = onSelectOption,
                modifier = modifier,
            )
        } else {
            WordToMeaningQuestionBody(
                question = question,
                body = body,
                selectedOptionId = state.selectedOptionId,
                isAnswerChecked = state.isAnswerChecked,
                onSelectOption = onSelectOption,
                onPlayAudio = onPlayAudio,
                modifier = modifier,
            )
        }

        is QuizPayload.Listening -> ListeningQuestionBody(
            question = question,
            body = body,
            selectedOptionId = state.selectedOptionId,
            isAnswerChecked = state.isAnswerChecked,
            isAudioPlaying = state.isAudioPlaying,
            playbackSpeed = state.playbackSpeed,
            onSelectOption = onSelectOption,
            onPlayAudio = onPlayAudio,
            onSpeedSelected = onSpeedSelected,
            modifier = modifier,
        )

        is QuizPayload.Spelling -> SpellingQuestionBody(
            question = question,
            body = body,
            typedLetters = state.typedLetters,
            isAnswerChecked = state.isAnswerChecked,
            onAppendLetter = onAppendLetter,
            onUndoLetter = onUndoLetter,
            onPlayAudio = onPlayAudio,
            modifier = modifier,
        )

        is QuizPayload.SentenceBlank -> SentenceBlankQuestionBody(
            question = question,
            body = body,
            selectedOptionId = state.selectedOptionId,
            isAnswerChecked = state.isAnswerChecked,
            onSelectOption = onSelectOption,
            modifier = modifier,
        )
    }
}
