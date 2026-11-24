package com.learnian.quiz.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.learnian.base.UiText
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.core.ui.component.toast.rememberClayToastHostState
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_flag
import com.learnian.core.view.resources.ic_update_check
import com.learnian.core.view.resources.quiz_answer_correct
import com.learnian.core.view.resources.quiz_answer_wrong
import com.learnian.core.view.resources.quiz_completed
import com.learnian.core.view.resources.quiz_confirm_answer
import com.learnian.core.view.resources.quiz_correct_answer_format
import com.learnian.core.view.resources.quiz_finish
import com.learnian.core.view.resources.quiz_next_question
import com.learnian.quiz.presentation.component.QuizHeader
import com.learnian.quiz.presentation.component.QuizQuestionBody
import com.learnian.quiz.presentation.mvi.QuizEffect
import com.learnian.quiz.presentation.mvi.QuizIntent
import com.learnian.quiz.presentation.viewmodel.QuizViewModel
import com.learnian.ui.base.BaseScreen
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

private const val COMPLETION_TOAST_HOLD_MS = 1_200L

/**
 * One quiz round: header, the body of the active question format, the inline grading
 * banner and the confirm/next action.
 *
 * The screen is format agnostic — [QuizQuestionBody] renders the body that matches the
 * payload delivered by the server, so adding a format never touches this file.
 */
@Composable
fun QuizScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val toastHostState = rememberClayToastHostState()
    val toastScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                QuizEffect.NavigateBack -> onBackClick()
                QuizEffect.QuizCompleted -> {
                    val message = UiText.Resource(Res.string.quiz_completed).asStringAsync()
                    toastHostState.show(toastScope, message, ClayToastType.Success)
                    delay(COMPLETION_TOAST_HOLD_MS)
                    onBackClick()
                }

                is QuizEffect.ShowToast -> {
                    val message = effect.message.asStringAsync()
                    toastHostState.show(toastScope, message, effect.type)
                }
            }
        }
    }

    LaunchedEffect(state.currentIndex) {
        scrollState.scrollTo(0)
    }

    BaseScreen(
        state = state,
        isRefreshing = state.isRefreshing,
        toastHostState = toastHostState,
        onRefresh = { viewModel.dispatch(QuizIntent.Refresh) },
        onRetry = { viewModel.dispatch(QuizIntent.LoadSession) },
        modifier = modifier.fillMaxSize(),
    ) {
        val question = state.question

        if (question != null) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
                ) {
                    QuizHeader(
                        currentIndex = state.currentIndex,
                        totalCount = state.totalCount,
                        streakDays = state.session?.streakDays ?: 0,
                        progress = state.progress,
                        activeType = question.type,
                        onBackClick = { viewModel.dispatch(QuizIntent.NavigateBack) },
                    )

                    QuizQuestionBody(
                        question = question,
                        state = state,
                        onSelectOption = { optionId ->
                            viewModel.dispatch(QuizIntent.SelectOption(optionId))
                        },
                        onAppendLetter = { letter ->
                            viewModel.dispatch(QuizIntent.AppendLetter(letter))
                        },
                        onUndoLetter = { viewModel.dispatch(QuizIntent.UndoLetter) },
                        onPlayAudio = { viewModel.dispatch(QuizIntent.ToggleAudio) },
                        onSpeedSelected = { speed ->
                            viewModel.dispatch(QuizIntent.SetPlaybackSpeed(speed))
                        },
                    )

                    Spacer(modifier = Modifier.height(Spacing.Small))
                }

                Column(
                    modifier = Modifier.padding(
                        horizontal = Spacing.Medium,
                        vertical = Spacing.Small,
                    ),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Small),
                ) {
                    if (state.isAnswerChecked) {
                        QuizAnswerBanner(
                            isCorrect = state.isAnswerCorrect == true,
                            correctAnswer = state.correctAnswer.orEmpty(),
                        )
                    }

                    ClayButton(
                        text = when {
                            !state.isAnswerChecked -> stringResource(Res.string.quiz_confirm_answer)
                            state.isLastQuestion -> stringResource(Res.string.quiz_finish)
                            else -> stringResource(Res.string.quiz_next_question)
                        },
                        onClick = {
                            if (state.isAnswerChecked) {
                                viewModel.dispatch(QuizIntent.NextQuestion)
                            } else {
                                viewModel.dispatch(QuizIntent.SubmitAnswer)
                            }
                        },
                        enabled = if (state.isAnswerChecked) {
                            !state.isSubmitting
                        } else {
                            state.canSubmit && !state.isSubmitting
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

/** Inline grading banner shown after an answer is checked. */
@Composable
private fun QuizAnswerBanner(
    isCorrect: Boolean,
    correctAnswer: String,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isCorrect) {
        ClayTheme.colors.secondaryContainer
    } else {
        ClayTheme.colors.errorContainer
    }
    val contentColor = if (isCorrect) {
        ClayTheme.colors.onSecondaryContainer
    } else {
        ClayTheme.colors.onErrorContainer
    }

    ClayCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = backgroundColor,
        padding = Spacing.Comfortable,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            BaseIcon(
                resource = if (isCorrect) Res.drawable.ic_update_check else Res.drawable.ic_flag,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(18.dp),
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
            ) {
                Text(
                    text = stringResource(
                        if (isCorrect) Res.string.quiz_answer_correct else Res.string.quiz_answer_wrong,
                    ),
                    style = AppTypography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = contentColor,
                )

                if (!isCorrect && correctAnswer.isNotBlank()) {
                    Text(
                        text = stringResource(Res.string.quiz_correct_answer_format, correctAnswer),
                        style = AppTypography.bodySmall,
                        color = contentColor,
                    )
                }
            }
        }
    }
}
