package com.learnian.review.presentation.component.hub

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.button.ClayButtonVariant
import com.learnian.core.ui.component.button.ClayIconButton
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_bookmark
import com.learnian.core.view.resources.review_dictionary_empty
import com.learnian.core.view.resources.review_percent_format
import com.learnian.core.view.resources.review_saved_banner_desc
import com.learnian.core.view.resources.review_saved_banner_minutes_format
import com.learnian.core.view.resources.review_saved_banner_start
import com.learnian.core.view.resources.review_saved_banner_title
import com.learnian.core.view.resources.review_saved_banner_words_format
import com.learnian.core.view.resources.review_saved_empty
import com.learnian.core.view.resources.review_saved_end
import com.learnian.core.view.resources.review_saved_list_title
import com.learnian.core.view.resources.review_saved_search_placeholder
import com.learnian.core.view.resources.review_saved_subtitle
import com.learnian.core.view.resources.review_saved_tag_all
import com.learnian.core.view.resources.review_saved_toggle_desc
import com.learnian.review.domain.model.LibraryWord
import com.learnian.review.domain.model.ReviewLibrary
import com.learnian.review.presentation.component.HighlightedSentence
import com.learnian.review.presentation.mapper.savedReviewMinutes
import com.learnian.review.presentation.mapper.savedWordsFor
import com.learnian.review.presentation.mvi.ReviewSection
import org.jetbrains.compose.resources.stringResource

/**
 * Saved words page: readiness banner, tag filters and the bookmarked cards.
 *
 * Bookmarking is delegated to [ReviewLibrary]'s owner (the hub ViewModel), which
 * writes through the same repository the detail screen uses, so both stay in sync.
 */
@Composable
fun ReviewSavedSection(
    library: ReviewLibrary,
    tag: String?,
    query: String,
    onTagChange: (String?) -> Unit,
    onQueryChange: (String) -> Unit,
    onToggleSaved: (String) -> Unit,
    onWordClick: (String) -> Unit,
    onStartReview: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val savedWords = library.savedWordsFor(tag = tag, query = query)
    val allSaved = library.savedWords
    val averageMastery = if (allSaved.isEmpty()) {
        0
    } else {
        allSaved.sumOf { it.masteryPercent } / allSaved.size
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        ClayCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
            ) {
                HubSectionHeader(
                    section = ReviewSection.SAVED,
                    subtitle = stringResource(Res.string.review_saved_subtitle),
                )
                HubSearchField(
                    value = query,
                    onValueChange = onQueryChange,
                    placeholder = stringResource(Res.string.review_saved_search_placeholder),
                )
            }
        }

        if (library.tags.isNotEmpty()) {
            HubFilterRow(
                options = listOf(
                    HubChipOption(
                        key = "",
                        label = stringResource(Res.string.review_saved_tag_all),
                    ),
                ) + library.tags.map { option ->
                    HubChipOption(key = option, label = option)
                },
                selectedKey = tag ?: "",
                onSelect = { key -> onTagChange(key.ifEmpty { null }) },
            )
        }

        if (allSaved.isEmpty()) {
            HubEmptyState(text = stringResource(Res.string.review_saved_empty))
        } else {
            SavedReviewBanner(
                minutes = library.savedReviewMinutes(),
                wordsCount = allSaved.size,
                masteryPercent = averageMastery,
                onStartReview = {
                    // Start with the weakest card so the session is immediately useful.
                    allSaved.minByOrNull { it.masteryPercent }?.let { word ->
                        onStartReview(word.wordId)
                    }
                },
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            ) {
                Text(
                    text = stringResource(Res.string.review_saved_list_title),
                    style = AppTypography.bodyMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp,
                    ),
                    color = ClayTheme.colors.textPrimary,
                    modifier = Modifier.weight(1f),
                )
                ClayStatusChip(
                    text = savedWords.size.toString(),
                    style = ClayChipStyle.Resting,
                    backgroundColor = ClayTheme.colors.primaryContainer,
                    textColor = ClayTheme.colors.onPrimaryContainer,
                    textStyle = AppTypography.labelMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp,
                    ),
                    contentPadding = PaddingValues(
                        horizontal = Spacing.Small,
                        vertical = Spacing.Tiny,
                    ),
                )
            }

            if (savedWords.isEmpty()) {
                HubEmptyState(text = stringResource(Res.string.review_dictionary_empty))
            } else {
                savedWords.forEach { word ->
                    SavedWordCard(
                        word = word,
                        onToggleSaved = { onToggleSaved(word.wordId) },
                        onClick = { onWordClick(word.wordId) },
                    )
                }
            }

            Text(
                text = stringResource(Res.string.review_saved_end),
                style = AppTypography.bodySmall.copy(fontSize = 10.sp),
                color = ClayTheme.colors.textSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Spacing.ExtraSmall),
            )
        }
    }
}

@Composable
private fun SavedReviewBanner(
    minutes: Int,
    wordsCount: Int,
    masteryPercent: Int,
    onStartReview: () -> Unit,
) {
    ClayCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = ClayTheme.colors.primary,
        cornerRadius = AppCorners.XL,
        padding = Spacing.Medium,
        shadowColor = ClayTheme.colors.primary.copy(alpha = 0.35f),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
        ) {
            Text(
                text = stringResource(Res.string.review_saved_banner_title),
                style = AppTypography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                ),
                color = ClayTheme.colors.onPrimary,
            )
            Text(
                text = stringResource(Res.string.review_saved_banner_desc),
                style = AppTypography.bodySmall.copy(fontSize = 11.sp, lineHeight = 17.sp),
                color = ClayTheme.colors.onPrimary.copy(alpha = 0.82f),
            )

            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Small)) {
                BannerPill(
                    text = stringResource(Res.string.review_saved_banner_minutes_format, minutes),
                )
                BannerPill(
                    text = stringResource(Res.string.review_saved_banner_words_format, wordsCount),
                )
                BannerPill(
                    text = stringResource(
                        Res.string.review_percent_format,
                        masteryPercent.toString(),
                    ),
                )
            }

            ClayButton(
                text = stringResource(Res.string.review_saved_banner_start),
                onClick = onStartReview,
                modifier = Modifier.fillMaxWidth(),
                variant = ClayButtonVariant.Surface,
                cornerRadius = AppCorners.Large,
                contentPadding = PaddingValues(vertical = Spacing.Standard),
            )
        }
    }
}

@Composable
private fun BannerPill(text: String) {
    ClayStatusChip(
        text = text,
        style = ClayChipStyle.Resting,
        backgroundColor = ClayTheme.colors.onPrimary.copy(alpha = 0.16f),
        textColor = ClayTheme.colors.onPrimary,
        textStyle = AppTypography.labelMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 11.sp,
        ),
        contentPadding = PaddingValues(
            horizontal = Spacing.Standard,
            vertical = Spacing.Compact,
        ),
    )
}

@Composable
private fun SavedWordCard(
    word: LibraryWord,
    onToggleSaved: () -> Unit,
    onClick: () -> Unit,
) {
    ClayCard(
        modifier = Modifier.fillMaxWidth(),
        padding = Spacing.Medium,
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.Compact),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                    ) {
                        Text(
                            text = word.term,
                            style = AppTypography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                            ),
                            color = ClayTheme.colors.textPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false),
                        )
                        ClayStatusChip(
                            text = word.level,
                            style = ClayChipStyle.Resting,
                            backgroundColor = ClayTheme.colors.primaryContainer,
                            textColor = ClayTheme.colors.onPrimaryContainer,
                            shadowColor = ClayTheme.colors.primary.copy(alpha = 0.16f),
                            textStyle = AppTypography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                            ),
                            contentPadding = PaddingValues(
                                horizontal = Spacing.Small,
                                vertical = Spacing.Tiny,
                            ),
                        )
                    }
                    Text(
                        text = word.phonetic,
                        style = AppTypography.bodySmall.copy(fontSize = 11.sp),
                        color = ClayTheme.colors.textSecondary,
                    )
                    Text(
                        text = word.meaning,
                        style = AppTypography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                    )
                }

                ClayIconButton(
                    onClick = onToggleSaved,
                    size = 40.dp,
                    cornerRadius = AppCorners.Pill,
                    backgroundColor = if (word.isSaved) {
                        ClayTheme.colors.primary
                    } else {
                        ClayTheme.colors.primaryContainer
                    },
                ) {
                    BaseIcon(
                        resource = Res.drawable.ic_bookmark,
                        contentDescription = stringResource(Res.string.review_saved_toggle_desc),
                        modifier = Modifier.size(18.dp),
                        tint = if (word.isSaved) {
                            ClayTheme.colors.onPrimary
                        } else {
                            ClayTheme.colors.primary
                        },
                    )
                }
            }

            HighlightedSentence(
                sentence = word.example,
                keyword = word.term,
                style = AppTypography.bodySmall.copy(fontSize = 12.sp, lineHeight = 18.sp),
                color = ClayTheme.colors.textPrimary,
                highlightColor = ClayTheme.colors.primary,
            )
            Text(
                text = word.examplePersian,
                style = AppTypography.bodySmall.copy(fontSize = 10.sp, lineHeight = 16.sp),
                color = ClayTheme.colors.textSecondary,
            )

            if (word.tags.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Compact)) {
                    word.tags.forEach { wordTag ->
                        ClayStatusChip(
                            text = wordTag,
                            style = ClayChipStyle.Resting,
                            backgroundColor = ClayTheme.colors.surfaceSunken,
                            textColor = ClayTheme.colors.textSecondary,
                            textStyle = AppTypography.labelMedium.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 9.sp,
                            ),
                            contentPadding = PaddingValues(
                                horizontal = Spacing.Small,
                                vertical = 3.dp,
                            ),
                        )
                    }
                }
            }
        }
    }
}
