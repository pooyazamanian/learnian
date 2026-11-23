package com.learnian.review.presentation.ui

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.base.UiText
import com.learnian.core.ui.component.button.ClayIconButton
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.core.ui.component.toast.rememberClayToastHostState
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_arrow_left
import com.learnian.core.view.resources.review_level_lesson_format
import com.learnian.core.view.resources.review_rated_success
import com.learnian.core.view.resources.word_detail_back
import com.learnian.core.view.resources.word_detail_rating_prompt
import com.learnian.core.view.resources.word_detail_title
import com.learnian.review.presentation.component.ReviewFsrsRatingBar
import com.learnian.review.presentation.component.WordDetailContent
import com.learnian.review.presentation.mvi.ReviewEffect
import com.learnian.review.presentation.mvi.ReviewIntent
import com.learnian.review.presentation.viewmodel.ReviewViewModel
import com.learnian.ui.base.BaseScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Deep-dive word detail screen (the "دیدن جزئیات کلمه" flow).
 *
 * Navigation passes only a `wordId`; the ViewModel resolves the full payload
 * from the server (falling back to the offline catalog) and renders every
 * available section. A sticky FSRS bar lets the user grade the word right after
 * reading it.
 */
@Composable
fun WordDetailScreen(
    wordId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val toastHostState = rememberClayToastHostState()
    val toastScope = rememberCoroutineScope()

    LaunchedEffect(wordId) {
        viewModel.dispatch(ReviewIntent.LoadWord(wordId))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ReviewEffect.NavigateBack -> onBackClick()
                is ReviewEffect.WordRatedSuccessfully -> {
                    val message = UiText.Resource(Res.string.review_rated_success).asStringAsync()
                    toastHostState.show(toastScope, message, ClayToastType.Success)
                }

                is ReviewEffect.NavigateToWordDetail -> Unit
                is ReviewEffect.ShowToast -> {
                    val message = effect.message.asStringAsync()
                    toastHostState.show(toastScope, message, effect.type)
                }
            }
        }
    }

    BaseScreen(
        state = state,
        isRefreshing = state.isRefreshing,
        toastHostState = toastHostState,
        onRetry = { viewModel.dispatch(ReviewIntent.LoadWord(wordId)) },
        modifier = modifier.fillMaxSize(),
    ) { word ->
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ClayIconButton(
                    onClick = { viewModel.dispatch(ReviewIntent.NavigateBack) },
                    size = 40.dp,
                    cornerRadius = AppCorners.Pill,
                    backgroundColor = ClayTheme.colors.surface,
                ) {
                    BaseIcon(
                        resource = Res.drawable.ic_arrow_left,
                        contentDescription = stringResource(Res.string.word_detail_back),
                        tint = ClayTheme.colors.textPrimary,
                        modifier = Modifier.size(20.dp),
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = Spacing.Small),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(Res.string.word_detail_title),
                        style = AppTypography.headlineSmall.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                        textAlign = TextAlign.Center,
                    )

                    if (word.level.isNotBlank() || word.lesson.isNotBlank()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = stringResource(
                                Res.string.review_level_lesson_format,
                                word.level,
                                word.lesson,
                            ),
                            style = AppTypography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                            ),
                            color = ClayTheme.colors.textSecondary,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

                // Balancing spacer keeps the title visually centered.
                Spacer(modifier = Modifier.size(40.dp))
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Spacing.Medium),
                verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
            ) {
                WordDetailContent(
                    wordData = word,
                    onToggleBookmark = { viewModel.dispatch(ReviewIntent.ToggleBookmark) },
                    onPlayAudio = { locale -> viewModel.dispatch(ReviewIntent.PlayAudio(locale)) },
                    bookmarkEnabled = !state.isBookmarkUpdating,
                )

                Spacer(modifier = Modifier.height(Spacing.Large))
            }

            ClayCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
                backgroundColor = ClayTheme.colors.surface,
                cornerRadius = AppCorners.XL,
                padding = Spacing.Comfortable,
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.Small)) {
                    Text(
                        text = stringResource(Res.string.word_detail_rating_prompt),
                        style = AppTypography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                        ),
                        color = ClayTheme.colors.textSecondary,
                    )

                    ReviewFsrsRatingBar(
                        intervals = word.fsrsIntervals,
                        onRateClick = { rating ->
                            viewModel.dispatch(ReviewIntent.RateWord(rating))
                        },
                        enabled = !state.isRating,
                    )
                }
            }
        }
    }
}
