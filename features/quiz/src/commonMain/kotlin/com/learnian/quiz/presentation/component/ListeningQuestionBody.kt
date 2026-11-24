package com.learnian.quiz.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_language
import com.learnian.core.view.resources.quiz_ipa_guide_format
import com.learnian.core.view.resources.quiz_prompt_listening
import com.learnian.quiz.domain.model.QuizPayload
import com.learnian.quiz.domain.model.QuizQuestion
import org.jetbrains.compose.resources.stringResource

/**
 * Audio prompt format: play control, playback speed, word options and the IPA guide.
 */
@Composable
fun ListeningQuestionBody(
    question: QuizQuestion,
    body: QuizPayload.Listening,
    selectedOptionId: String?,
    isAnswerChecked: Boolean,
    isAudioPlaying: Boolean,
    playbackSpeed: Float,
    onSelectOption: (String) -> Unit,
    onPlayAudio: () -> Unit,
    onSpeedSelected: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        QuizPrompt(
            text = question.prompt
                ?: stringResource(Res.string.quiz_prompt_listening),
        )

        QuizAudioPlayer(
            isPlaying = isAudioPlaying,
            accent = body.accent,
            onPlayClick = onPlayAudio,
        )

        QuizSpeedSelector(
            selectedSpeed = playbackSpeed,
            onSpeedSelected = onSpeedSelected,
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

        if (question.word.phonetic.isNotBlank()) {
            QuizInfoCard(
                icon = Res.drawable.ic_language,
                title = stringResource(
                    Res.string.quiz_ipa_guide_format,
                    question.word.phonetic,
                ),
                body = "",
                accentColor = ClayTheme.colors.primary,
            )
        }

        QuizSupportCards(word = question.word)
    }
}
