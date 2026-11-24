package com.learnian.quiz.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.component.button.ClayIconButton
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ChipIconPosition
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_quote
import com.learnian.core.view.resources.ic_volume_up
import com.learnian.core.view.resources.quiz_accent_uk
import com.learnian.core.view.resources.quiz_accent_us
import com.learnian.core.view.resources.quiz_audio_icon_desc
import com.learnian.core.view.resources.quiz_context_title
import com.learnian.core.view.resources.quiz_level_format
import com.learnian.core.view.resources.quiz_listen_natural
import com.learnian.core.view.resources.quiz_play_audio
import com.learnian.core.view.resources.quiz_speed_normal
import com.learnian.core.view.resources.quiz_speed_slow
import com.learnian.quiz.domain.model.QuizWord
import com.learnian.quiz.presentation.mvi.QuizState
import org.jetbrains.compose.resources.stringResource

/** Instruction line above the question body. */
@Composable
fun QuizPrompt(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier.fillMaxWidth(),
        style = AppTypography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = ClayTheme.colors.textPrimary,
    )
}

/** Word card of the English -> Persian format. */
@Composable
fun QuizWordCard(
    word: QuizWord,
    onPlayAudio: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        padding = AppPaddings.CardPaddingComfortable,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.Small)) {
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Small)) {
                if (word.level.isNotBlank()) {
                    ClayStatusChip(
                        text = stringResource(Res.string.quiz_level_format, word.level),
                        textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    )
                }
                if (word.partOfSpeech.isNotBlank()) {
                    ClayStatusChip(
                        text = word.partOfSpeech,
                        textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    )
                }
            }

            Text(
                text = word.term,
                style = AppTypography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                color = ClayTheme.colors.textPrimary,
            )

            if (word.phonetic.isNotBlank()) {
                Text(
                    text = word.phonetic,
                    style = AppTypography.bodyMedium,
                    color = ClayTheme.colors.textSecondary,
                )
            }

            ClayStatusChip(
                text = stringResource(Res.string.quiz_listen_natural),
                style = ClayChipStyle.Resting,
                icon = {
                    BaseIcon(
                        resource = Res.drawable.ic_volume_up,
                        contentDescription = stringResource(Res.string.quiz_audio_icon_desc),
                        tint = ClayTheme.colors.primary,
                        modifier = Modifier.size(16.dp),
                    )
                },
                iconPosition = ChipIconPosition.Start,
                textColor = ClayTheme.colors.primary,
                backgroundColor = ClayTheme.colors.primaryContainer,
                onClick = onPlayAudio,
            )
        }
    }
}

/** Persian meaning prompt of the Persian -> English format. */
@Composable
fun QuizMeaningCard(
    word: QuizWord,
    modifier: Modifier = Modifier,
) {
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        padding = AppPaddings.CardPaddingComfortable,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.Comfortable)) {
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Small)) {
                if (word.partOfSpeech.isNotBlank()) {
                    ClayStatusChip(
                        text = word.partOfSpeech,
                        textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    )
                }
                if (word.level.isNotBlank()) {
                    ClayStatusChip(
                        text = stringResource(Res.string.quiz_level_format, word.level),
                        textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    )
                }
            }

            Text(
                text = word.meaning,
                style = AppTypography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = ClayTheme.colors.textPrimary,
            )

            if (word.definition.isNotBlank()) {
                Text(
                    text = word.definition,
                    style = AppTypography.bodySmall,
                    color = ClayTheme.colors.textSecondary,
                )
            }
        }
    }
}

/** Context sentence card; [keyword] occurrences are highlighted when present. */
@Composable
fun QuizContextCard(
    sentence: String,
    translation: String,
    keyword: String,
    modifier: Modifier = Modifier,
) {
    val highlightBackground = ClayTheme.colors.primaryContainer
    val highlightContent = ClayTheme.colors.onPrimaryContainer
    val highlighted = remember(sentence, keyword, highlightBackground, highlightContent) {
        highlightSentence(
            sentence = sentence,
            keyword = keyword,
            background = highlightBackground,
            content = highlightContent,
        )
    }

    ClayCard(
        modifier = modifier.fillMaxWidth(),
        padding = AppPaddings.CardPaddingComfortable,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.Small)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            ) {
                BaseIcon(
                    resource = Res.drawable.ic_quote,
                    contentDescription = null,
                    tint = ClayTheme.colors.primary,
                    modifier = Modifier.size(16.dp),
                )
                Text(
                    text = stringResource(Res.string.quiz_context_title),
                    style = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = ClayTheme.colors.textSecondary,
                )
            }

            Text(
                text = highlighted,
                style = AppTypography.bodyMedium,
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

/** Listening format player: big play control plus the accent label. */
@Composable
fun QuizAudioPlayer(
    isPlaying: Boolean,
    accent: String,
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = if (isPlaying) {
            ClayTheme.colors.primaryContainer
        } else {
            ClayTheme.colors.surface
        },
        padding = AppPaddings.CardPaddingComfortable,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.Medium),
        ) {
            ClayIconButton(
                onClick = onPlayClick,
                size = 56.dp,
                cornerRadius = AppCorners.Large,
                backgroundColor = if (isPlaying) ClayTheme.colors.secondary else ClayTheme.colors.primary,
            ) {
                BaseIcon(
                    resource = Res.drawable.ic_volume_up,
                    contentDescription = stringResource(Res.string.quiz_audio_icon_desc),
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.Tiny)) {
                Text(
                    text = stringResource(Res.string.quiz_play_audio),
                    style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = ClayTheme.colors.textPrimary,
                )
                Text(
                    text = accentLabel(accent),
                    style = AppTypography.labelSmall,
                    color = ClayTheme.colors.textSecondary,
                )
            }
        }
    }
}

/** Playback speed switch for the listening format. */
@Composable
fun QuizSpeedSelector(
    selectedSpeed: Float,
    onSpeedSelected: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
    ) {
        SpeedChip(
            speed = QuizState.NORMAL_SPEED,
            label = stringResource(Res.string.quiz_speed_normal),
            selectedSpeed = selectedSpeed,
            onSpeedSelected = onSpeedSelected,
        )
        SpeedChip(
            speed = QuizState.SLOW_SPEED,
            label = stringResource(Res.string.quiz_speed_slow),
            selectedSpeed = selectedSpeed,
            onSpeedSelected = onSpeedSelected,
        )
    }
}

@Composable
private fun SpeedChip(
    speed: Float,
    label: String,
    selectedSpeed: Float,
    onSpeedSelected: (Float) -> Unit,
) {
    val isSelected = selectedSpeed == speed
    ClayStatusChip(
        text = label,
        style = ClayChipStyle.Resting,
        backgroundColor = if (isSelected) {
            ClayTheme.colors.primaryContainer
        } else {
            ClayTheme.colors.surface
        },
        textColor = if (isSelected) ClayTheme.colors.primary else ClayTheme.colors.textSecondary,
        textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
        onClick = { onSpeedSelected(speed) },
    )
}

@Composable
private fun accentLabel(accent: String): String = when (accent.uppercase()) {
    "US" -> stringResource(Res.string.quiz_accent_us)
    "UK" -> stringResource(Res.string.quiz_accent_uk)
    else -> accent
}

/**
 * Highlights every case-insensitive occurrence of [keyword] inside [sentence].
 */
private fun highlightSentence(
    sentence: String,
    keyword: String,
    background: Color,
    content: Color,
): AnnotatedString = buildAnnotatedString {
    val trimmedKeyword = keyword.trim()
    if (trimmedKeyword.isEmpty() || sentence.isEmpty()) {
        append(sentence)
        return@buildAnnotatedString
    }

    val lowerSentence = sentence.lowercase()
    val lowerKeyword = trimmedKeyword.lowercase()
    var cursor = 0

    while (cursor < sentence.length) {
        val matchIndex = lowerSentence.indexOf(lowerKeyword, startIndex = cursor)
        if (matchIndex < 0) {
            append(sentence.substring(cursor))
            break
        }

        if (matchIndex > cursor) {
            append(sentence.substring(cursor, matchIndex))
        }

        val matchEnd = (matchIndex + trimmedKeyword.length).coerceAtMost(sentence.length)
        withStyle(
            SpanStyle(
                background = background,
                color = content,
                fontWeight = FontWeight.Bold,
            ),
        ) {
            append(sentence.substring(matchIndex, matchEnd))
        }
        cursor = matchEnd
    }
}
