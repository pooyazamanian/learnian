package com.learnian.quiz.presentation.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.component.button.ClayIconButton
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.progress.ClayProgressBar
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_arrow_left
import com.learnian.core.view.resources.ic_streak_flame
import com.learnian.core.view.resources.quiz_back
import com.learnian.core.view.resources.quiz_question_of_format
import com.learnian.core.view.resources.quiz_streak_days_format
import com.learnian.core.view.resources.quiz_streak_icon_desc
import com.learnian.core.view.resources.quiz_title
import com.learnian.core.view.resources.quiz_type_en_to_fa
import com.learnian.core.view.resources.quiz_type_fa_to_en
import com.learnian.core.view.resources.quiz_type_listening
import com.learnian.core.view.resources.quiz_type_sentence
import com.learnian.core.view.resources.quiz_type_spelling
import com.learnian.quiz.domain.model.QuizQuestionType
import org.jetbrains.compose.resources.stringResource

/**
 * Quiz header: round identity, position, streak, progress bar and the format rail.
 *
 * The rail is a read-only indicator of the active question format — the server decides
 * the format of each question, so the learner cannot switch it manually.
 */
@Composable
fun QuizHeader(
    currentIndex: Int,
    totalCount: Int,
    streakDays: Int,
    progress: Float,
    activeType: QuizQuestionType,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            ClayIconButton(
                onClick = onBackClick,
                size = 40.dp,
                cornerRadius = AppCorners.Medium,
                backgroundColor = ClayTheme.colors.surface,
            ) {
                BaseIcon(
                    resource = Res.drawable.ic_arrow_left,
                    contentDescription = stringResource(Res.string.quiz_back),
                    tint = ClayTheme.colors.textPrimary,
                    modifier = Modifier.size(20.dp),
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
            ) {
                Text(
                    text = stringResource(Res.string.quiz_title),
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                    ),
                    color = ClayTheme.colors.textPrimary,
                )
                Text(
                    text = stringResource(
                        Res.string.quiz_question_of_format,
                        (currentIndex + 1).toString(),
                        totalCount.toString(),
                    ),
                    style = AppTypography.labelSmall,
                    color = ClayTheme.colors.textSecondary,
                )
            }

            ClayStatusChip(
                text = stringResource(Res.string.quiz_streak_days_format, streakDays.toString()),
                style = ClayChipStyle.Resting,
                icon = {
                    BaseIcon(
                        resource = Res.drawable.ic_streak_flame,
                        contentDescription = stringResource(Res.string.quiz_streak_icon_desc),
                        tint = ClayTheme.colors.tertiary,
                        modifier = Modifier.size(14.dp),
                    )
                },
            )
        }

        ClayProgressBar(
            progress = progress.coerceIn(0f, 1f),
            height = 10.dp,
        )

        QuizTypeRail(activeType = activeType)
    }
}

@Composable
private fun QuizTypeRail(
    activeType: QuizQuestionType,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
    ) {
        QuizQuestionType.entries.forEach { type ->
            val isActive = type == activeType
            ClayStatusChip(
                text = stringResource(type.labelRes()),
                style = ClayChipStyle.Resting,
                backgroundColor = if (isActive) {
                    ClayTheme.colors.primaryContainer
                } else {
                    ClayTheme.colors.surface
                },
                textColor = if (isActive) {
                    ClayTheme.colors.primary
                } else {
                    ClayTheme.colors.textSecondary
                },
                textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                contentPadding = PaddingValues(
                    horizontal = Spacing.Comfortable,
                    vertical = Spacing.Compact,
                ),
            )
        }
    }
}

private fun QuizQuestionType.labelRes() = when (this) {
    QuizQuestionType.EN_TO_FA -> Res.string.quiz_type_en_to_fa
    QuizQuestionType.FA_TO_EN -> Res.string.quiz_type_fa_to_en
    QuizQuestionType.LISTENING -> Res.string.quiz_type_listening
    QuizQuestionType.SPELLING -> Res.string.quiz_type_spelling
    QuizQuestionType.SENTENCE_BLANK -> Res.string.quiz_type_sentence
}
