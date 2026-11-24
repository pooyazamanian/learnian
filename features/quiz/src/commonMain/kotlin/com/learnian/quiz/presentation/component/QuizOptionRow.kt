package com.learnian.quiz.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_update_check
import com.learnian.core.view.resources.quiz_answer_correct
import com.learnian.quiz.domain.model.QuizOption
import org.jetbrains.compose.resources.stringResource

/** Visual state of one selectable option. */
enum class QuizOptionState {
    Idle,
    Selected,
    Correct,
    Wrong,
}

/**
 * One answer row shared by the choice, listening and sentence formats.
 *
 * Optional metadata ([QuizOption.phonetic], [QuizOption.tag], [QuizOption.meaning]) is
 * rendered only when the payload provides it, so the same row serves every format.
 */
@Composable
fun QuizOptionRow(
    index: Int,
    option: QuizOption,
    state: QuizOptionState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val backgroundColor = when (state) {
        QuizOptionState.Idle -> ClayTheme.colors.surface
        QuizOptionState.Selected -> ClayTheme.colors.primaryContainer
        QuizOptionState.Correct -> ClayTheme.colors.secondaryContainer
        QuizOptionState.Wrong -> ClayTheme.colors.errorContainer
    }

    val contentColor = when (state) {
        QuizOptionState.Idle -> ClayTheme.colors.textPrimary
        QuizOptionState.Selected -> ClayTheme.colors.primary
        QuizOptionState.Correct -> ClayTheme.colors.onSecondaryContainer
        QuizOptionState.Wrong -> ClayTheme.colors.onErrorContainer
    }

    ClayCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = backgroundColor,
        cornerRadius = AppCorners.Medium,
        padding = Spacing.Comfortable,
        onClick = if (enabled) onClick else null,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(ClayTheme.colors.surfaceSunken, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = index.toString(),
                    style = AppTypography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = ClayTheme.colors.textSecondary,
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
            ) {
                Text(
                    text = option.text,
                    style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = contentColor,
                )

                option.metadataLine()?.let { metadata ->
                    Text(
                        text = metadata,
                        style = AppTypography.labelSmall,
                        color = contentColor.copy(alpha = 0.75f),
                    )
                }

                option.meaning?.let { meaning ->
                    Text(
                        text = meaning,
                        style = AppTypography.bodySmall,
                        color = contentColor.copy(alpha = 0.85f),
                    )
                }
            }

            if (state == QuizOptionState.Correct) {
                BaseIcon(
                    resource = Res.drawable.ic_update_check,
                    contentDescription = stringResource(Res.string.quiz_answer_correct),
                    tint = ClayTheme.colors.secondary,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

/**
 * Maps the submission state to the visual state of one option.
 *
 * After checking, only the correct option and the picked-but-wrong option are marked;
 * every other row falls back to its idle look.
 */
fun resolveOptionState(
    optionId: String,
    selectedOptionId: String?,
    correctOptionId: String,
    isAnswerChecked: Boolean,
): QuizOptionState = when {
    !isAnswerChecked && optionId == selectedOptionId -> QuizOptionState.Selected
    isAnswerChecked && optionId == correctOptionId -> QuizOptionState.Correct
    isAnswerChecked && optionId == selectedOptionId -> QuizOptionState.Wrong
    else -> QuizOptionState.Idle
}

/** Phonetic + part of speech, joined only when present. */
private fun QuizOption.metadataLine(): String? {
    val parts = listOfNotNull(
        phonetic?.takeIf { it.isNotBlank() },
        tag?.takeIf { it.isNotBlank() },
    )
    return parts.takeIf { it.isNotEmpty() }?.joinToString(separator = " • ")
}
