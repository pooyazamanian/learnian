package com.learnian.review.presentation.component.hub

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.modifiers.clayInsetGrooved
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.review_days_format
import com.learnian.core.view.resources.review_dictionary_empty
import com.learnian.core.view.resources.review_dictionary_open_card
import com.learnian.core.view.resources.review_dictionary_pos_all
import com.learnian.core.view.resources.review_dictionary_search_placeholder
import com.learnian.core.view.resources.review_dictionary_subtitle
import com.learnian.core.view.resources.review_next_review_format
import com.learnian.review.domain.model.LibraryWord
import com.learnian.review.domain.model.ReviewLibrary
import com.learnian.review.presentation.component.HighlightedSentence
import com.learnian.review.presentation.mapper.dictionaryWords
import com.learnian.review.presentation.mapper.partOfSpeechOptions
import com.learnian.review.presentation.mvi.ReviewSection
import org.jetbrains.compose.resources.stringResource

/**
 * Vocabulary dictionary: searchable, filterable list of every word.
 *
 * The card shows the meaning and one highlighted real sentence; tapping it opens
 * the full word card through the word id.
 */
@Composable
fun ReviewDictionarySection(
    library: ReviewLibrary,
    partOfSpeech: String?,
    query: String,
    onPartOfSpeechChange: (String?) -> Unit,
    onQueryChange: (String) -> Unit,
    onWordClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val words = library.dictionaryWords(partOfSpeech = partOfSpeech, query = query)
    val posOptions = library.partOfSpeechOptions()

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
                    section = ReviewSection.DICTIONARY,
                    subtitle = stringResource(Res.string.review_dictionary_subtitle),
                )
                HubSearchField(
                    value = query,
                    onValueChange = onQueryChange,
                    placeholder = stringResource(Res.string.review_dictionary_search_placeholder),
                )
            }
        }

        if (posOptions.isNotEmpty()) {
            HubFilterRow(
                options = listOf(
                    HubChipOption(
                        key = "",
                        label = stringResource(Res.string.review_dictionary_pos_all),
                    ),
                ) + posOptions.map { option ->
                    HubChipOption(key = option, label = option)
                },
                selectedKey = partOfSpeech ?: "",
                onSelect = { key -> onPartOfSpeechChange(key.ifEmpty { null }) },
            )
        }

        if (words.isEmpty()) {
            HubEmptyState(text = stringResource(Res.string.review_dictionary_empty))
        } else {
            words.forEach { word ->
                DictionaryWordCard(
                    word = word,
                    onClick = { onWordClick(word.wordId) },
                )
            }
        }
    }
}

@Composable
private fun DictionaryWordCard(
    word: LibraryWord,
    onClick: () -> Unit,
) {
    ClayCard(
        modifier = Modifier.fillMaxWidth(),
        padding = Spacing.Medium,
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.Standard),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
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
                    )
                    Text(
                        text = word.phonetic,
                        style = AppTypography.bodySmall.copy(fontSize = 11.sp),
                        color = ClayTheme.colors.textSecondary,
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Compact)) {
                    ClayStatusChip(
                        text = word.partOfSpeechFa,
                        style = ClayChipStyle.Resting,
                        backgroundColor = ClayTheme.colors.secondaryContainer,
                        textColor = ClayTheme.colors.onSecondaryContainer,
                        shadowColor = ClayTheme.colors.secondary.copy(alpha = 0.16f),
                        textStyle = AppTypography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                        ),
                        contentPadding = PaddingValues(
                            horizontal = Spacing.Small,
                            vertical = Spacing.Tiny,
                        ),
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
            }

            Text(
                text = word.meaning,
                style = AppTypography.bodyMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                ),
                color = ClayTheme.colors.textPrimary,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clayInsetGrooved(
                        backgroundColor = ClayTheme.colors.surfaceSunken,
                        cornerRadius = AppCorners.Medium,
                    )
                    .padding(Spacing.Standard),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.Compact)) {
                    Text(
                        text = word.explanation,
                        style = AppTypography.bodySmall.copy(fontSize = 11.sp, lineHeight = 17.sp),
                        color = ClayTheme.colors.textSecondary,
                    )
                    HighlightedSentence(
                        sentence = word.example,
                        keyword = word.term,
                        style = AppTypography.bodySmall.copy(
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                        ),
                        color = ClayTheme.colors.textPrimary,
                        highlightColor = ClayTheme.colors.primary,
                    )
                    Text(
                        text = word.examplePersian,
                        style = AppTypography.bodySmall.copy(fontSize = 10.sp, lineHeight = 16.sp),
                        color = ClayTheme.colors.textSecondary,
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            ) {
                Text(
                    text = stringResource(Res.string.review_dictionary_open_card),
                    style = AppTypography.bodySmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp,
                    ),
                    color = ClayTheme.colors.primary,
                )
                Text(
                    text = stringResource(
                        Res.string.review_next_review_format,
                        stringResource(
                            Res.string.review_days_format,
                            formatDecimal(word.nextReviewDays),
                        ),
                    ),
                    style = AppTypography.bodySmall.copy(fontSize = 10.sp),
                    color = ClayTheme.colors.textSecondary,
                    modifier = Modifier.weight(1f),
                )
                HubPercentBadge(
                    percent = word.masteryPercent,
                    containerColor = word.status.containerColor(),
                    contentColor = word.status.contentColor(),
                )
            }
        }
    }
}
