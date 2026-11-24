package com.learnian.quiz.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.quiz_blank_placeholder
import com.learnian.core.view.resources.quiz_prompt_sentence
import com.learnian.quiz.domain.model.QuizPayload
import com.learnian.quiz.domain.model.QuizQuestion
import org.jetbrains.compose.resources.stringResource

/**
 * Sentence format: the word is blanked inside a real sentence and the learner picks
 * the option that fits the context.
 */
@Composable
fun SentenceBlankQuestionBody(
    question: QuizQuestion,
    body: QuizPayload.SentenceBlank,
    selectedOptionId: String?,
    isAnswerChecked: Boolean,
    onSelectOption: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedWord = body.options.firstOrNull { it.id == selectedOptionId }?.text

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        QuizPrompt(
            text = question.prompt
                ?: stringResource(Res.string.quiz_prompt_sentence),
        )

        BlankSentenceCard(
            sentence = body.sentence,
            translation = body.translation,
            answerWord = question.word.term,
            filledWord = selectedWord,
            partOfSpeech = question.word.partOfSpeech,
            isAnswerChecked = isAnswerChecked,
            isCorrect = selectedOptionId == body.correctOptionId,
        )

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
}

@Composable
private fun BlankSentenceCard(
    sentence: String,
    translation: String,
    answerWord: String,
    filledWord: String?,
    partOfSpeech: String,
    isAnswerChecked: Boolean,
    isCorrect: Boolean,
) {
    val placeholder = stringResource(Res.string.quiz_blank_placeholder)
    val displayWord = filledWord?.takeIf { it.isNotBlank() } ?: placeholder
    val blankBackground = when {
        !isAnswerChecked -> ClayTheme.colors.primaryContainer
        isCorrect -> ClayTheme.colors.secondaryContainer
        else -> ClayTheme.colors.errorContainer
    }
    val blankContent = when {
        !isAnswerChecked -> ClayTheme.colors.onPrimaryContainer
        isCorrect -> ClayTheme.colors.onSecondaryContainer
        else -> ClayTheme.colors.onErrorContainer
    }
    val highlighted = remember(sentence, answerWord, displayWord, blankBackground, blankContent) {
        buildBlankSentence(
            sentence = sentence,
            answerWord = answerWord,
            displayWord = displayWord,
            background = blankBackground,
            content = blankContent,
        )
    }

    ClayCard(
        modifier = Modifier,
        padding = AppPaddings.CardPaddingComfortable,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.Small)) {
            if (partOfSpeech.isNotBlank()) {
                ClayStatusChip(
                    text = partOfSpeech,
                    textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                )
            }

            Text(
                text = highlighted,
                style = AppTypography.bodyLarge,
                color = ClayTheme.colors.textPrimary,
            )

            if (translation.isNotBlank()) {
                Text(
                    text = translation,
                    style = AppTypography.bodySmall,
                    color = ClayTheme.colors.textSecondary,
                )
            }
        }
    }
}

/**
 * Replaces the answer word (or an existing underscore run) inside [sentence] with the
 * currently selected word, styled as a clay blank.
 */
private fun buildBlankSentence(
    sentence: String,
    answerWord: String,
    displayWord: String,
    background: Color,
    content: Color,
): AnnotatedString {
    val blankStyle = SpanStyle(
        background = background,
        color = content,
        fontWeight = FontWeight.Bold,
    )
    val answerMatch = answerWord
        .takeIf { it.isNotBlank() }
        ?.let { keyword ->
            Regex("(?i)\\b" + Regex.escape(keyword) + "\\b").find(sentence)
        }
    val blankMatch = Regex("_{2,}").find(sentence)
    val match = answerMatch ?: blankMatch

    return buildAnnotatedString {
        if (match == null) {
            append(sentence)
            withStyle(blankStyle) { append(displayWord) }
            return@buildAnnotatedString
        }

        append(sentence.substring(0, match.range.first))
        withStyle(blankStyle) { append(displayWord) }
        append(sentence.substring(match.range.last + 1))
    }
}
