package com.learnian.quiz.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.component.button.ClayIconButton
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_lightbulb
import com.learnian.core.view.resources.ic_volume_up
import com.learnian.core.view.resources.quiz_audio_icon_desc
import com.learnian.core.view.resources.quiz_choose_letter
import com.learnian.core.view.resources.quiz_hint_title
import com.learnian.core.view.resources.quiz_prompt_spelling
import com.learnian.core.view.resources.quiz_undo_letter
import com.learnian.quiz.domain.model.QuizPayload
import com.learnian.quiz.domain.model.QuizQuestion
import org.jetbrains.compose.resources.stringResource

/**
 * Spelling format: the word is shown as slots with a few letters revealed, and the
 * learner types the missing letters from the letter bank.
 */
@Composable
fun SpellingQuestionBody(
    question: QuizQuestion,
    body: QuizPayload.Spelling,
    typedLetters: List<String>,
    isAnswerChecked: Boolean,
    onAppendLetter: (String) -> Unit,
    onUndoLetter: () -> Unit,
    onPlayAudio: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val letters = body.answer.uppercase()
    val missingIndices = body.missingIndices
    val typedByIndex = remember(missingIndices, typedLetters) {
        missingIndices.withIndex().associate { (cursor, index) -> index to typedLetters.getOrNull(cursor) }
    }
    val isComplete = typedLetters.size >= missingIndices.size

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        QuizPrompt(
            text = question.prompt
                ?: stringResource(Res.string.quiz_prompt_spelling),
        )

        QuizMeaningCard(word = question.word)

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(vertical = Spacing.Tiny),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            letters.forEachIndexed { index, letter ->
                val typedLetter = typedByIndex[index]
                val isRevealed = index in body.revealedIndices
                val slotColor = when {
                    typedLetter != null -> ClayTheme.colors.primaryContainer
                    isRevealed -> ClayTheme.colors.surface
                    else -> ClayTheme.colors.surfaceSunken
                }

                Box(
                    modifier = Modifier
                        .size(width = 32.dp, height = 44.dp)
                        .background(slotColor, RoundedCornerShape(AppCorners.Small)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = typedLetter ?: letter.takeIf { isRevealed }?.toString().orEmpty(),
                        style = AppTypography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = if (typedLetter != null) {
                            ClayTheme.colors.primary
                        } else {
                            ClayTheme.colors.textPrimary
                        },
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            ClayIconButton(
                onClick = onPlayAudio,
                size = 36.dp,
                cornerRadius = AppCorners.Small,
                backgroundColor = ClayTheme.colors.primaryContainer,
            ) {
                BaseIcon(
                    resource = Res.drawable.ic_volume_up,
                    contentDescription = stringResource(Res.string.quiz_audio_icon_desc),
                    tint = ClayTheme.colors.primary,
                    modifier = Modifier.size(16.dp),
                )
            }

            if (question.word.phonetic.isNotBlank()) {
                Text(
                    text = question.word.phonetic,
                    style = AppTypography.labelSmall,
                    color = ClayTheme.colors.textSecondary,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            ClayStatusChip(
                text = stringResource(Res.string.quiz_undo_letter),
                textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                textColor = ClayTheme.colors.textSecondary,
                onClick = if (!isAnswerChecked && typedLetters.isNotEmpty()) onUndoLetter else null,
            )
        }

        Text(
            text = stringResource(Res.string.quiz_choose_letter),
            style = AppTypography.labelSmall,
            color = ClayTheme.colors.textSecondary,
        )

        Column(verticalArrangement = Arrangement.spacedBy(Spacing.Small)) {
            body.letterBank.chunked(LETTERS_PER_ROW).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Small)) {
                    row.forEach { letter ->
                        LetterKey(
                            letter = letter,
                            enabled = !isAnswerChecked && !isComplete,
                            onClick = { onAppendLetter(letter) },
                        )
                    }
                }
            }
        }

        if (body.hint.isNotBlank()) {
            QuizInfoCard(
                icon = Res.drawable.ic_lightbulb,
                title = stringResource(Res.string.quiz_hint_title),
                body = body.hint,
                accentColor = ClayTheme.colors.primary,
            )
        }
    }
}

@Composable
private fun LetterKey(
    letter: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    ClayCard(
        modifier = Modifier.size(56.dp),
        backgroundColor = if (enabled) ClayTheme.colors.surface else ClayTheme.colors.surfaceSunken,
        cornerRadius = AppCorners.Medium,
        padding = 0.dp,
        onClick = if (enabled) onClick else null,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = letter,
                style = AppTypography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = if (enabled) ClayTheme.colors.textPrimary else ClayTheme.colors.textSecondary,
            )
        }
    }
}

private const val LETTERS_PER_ROW = 4
