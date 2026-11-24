package com.learnian.quiz.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.quiz_prompt_en_to_fa
import com.learnian.core.view.resources.quiz_prompt_fa_to_en
import com.learnian.quiz.domain.model.QuizPayload
import com.learnian.quiz.domain.model.QuizQuestion
import org.jetbrains.compose.resources.stringResource

/**
 * English word -> Persian meaning.
 *
 * Renders the word card, its context sentence, the meaning options and the support
 * cards (etymology / mnemonic / AI hint).
 */
@Composable
fun WordToMeaningQuestionBody(
    question: QuizQuestion,
    body: QuizPayload.MeaningChoice,
    selectedOptionId: String?,
    isAnswerChecked: Boolean,
    onSelectOption: (String) -> Unit,
    onPlayAudio: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        QuizWordCard(
            word = question.word,
            onPlayAudio = onPlayAudio,
        )

        if (question.word.contextSentence.isNotBlank()) {
            QuizContextCard(
                sentence = question.word.contextSentence,
                translation = question.word.contextTranslation,
                keyword = question.word.term,
            )
        }

        QuizPrompt(
            text = question.prompt
                ?: stringResource(Res.string.quiz_prompt_en_to_fa),
        )

        ChoiceOptions(
            body = body,
            selectedOptionId = selectedOptionId,
            isAnswerChecked = isAnswerChecked,
            onSelectOption = onSelectOption,
        )

        QuizSupportCards(word = question.word)
    }
}

/**
 * Persian meaning -> English word.
 *
 * Same options row as the other choice formats, but the prompt is the Persian meaning
 * and the context sentence keeps its blank.
 */
@Composable
fun MeaningToWordQuestionBody(
    question: QuizQuestion,
    body: QuizPayload.MeaningChoice,
    selectedOptionId: String?,
    isAnswerChecked: Boolean,
    onSelectOption: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        QuizMeaningCard(word = question.word)

        QuizPrompt(
            text = question.prompt
                ?: stringResource(Res.string.quiz_prompt_fa_to_en),
        )

        ChoiceOptions(
            body = body,
            selectedOptionId = selectedOptionId,
            isAnswerChecked = isAnswerChecked,
            onSelectOption = onSelectOption,
        )

        if (question.word.contextSentence.isNotBlank()) {
            QuizContextCard(
                sentence = question.word.contextSentence,
                translation = question.word.contextTranslation,
                keyword = "",
            )
        }

        QuizSupportCards(word = question.word)
    }
}

/** Shared option list for the choice formats. */
@Composable
private fun ChoiceOptions(
    body: QuizPayload.Choice,
    selectedOptionId: String?,
    isAnswerChecked: Boolean,
    onSelectOption: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.Small)) {
        body.options.forEachIndexed { index, option ->
            QuizOptionRow(
                index = index + 1,
                option = option,
                state = resolveOptionState(
                    optionId = option.id,
                    selectedOptionId = selectedOptionId,
                    correctOptionId = body.correctOptionId,
                    isAnswerChecked = isAnswerChecked,
                ),
                enabled = !isAnswerChecked,
                onClick = { onSelectOption(option.id) },
            )
        }
    }
}

