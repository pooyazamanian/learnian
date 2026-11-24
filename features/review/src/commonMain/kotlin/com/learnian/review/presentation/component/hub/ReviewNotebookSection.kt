package com.learnian.review.presentation.component.hub

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.button.ClayButtonVariant
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_clock
import com.learnian.core.view.resources.ic_update_check
import com.learnian.core.view.resources.review_days_format
import com.learnian.core.view.resources.review_filter_all
import com.learnian.core.view.resources.review_filter_frequent
import com.learnian.core.view.resources.review_filter_needs_review
import com.learnian.core.view.resources.review_hours_ago_format
import com.learnian.core.view.resources.review_last_reviewed_format
import com.learnian.core.view.resources.review_next_review_format
import com.learnian.core.view.resources.review_notebook_empty
import com.learnian.core.view.resources.review_notebook_stat_mastered
import com.learnian.core.view.resources.review_notebook_stat_reviews
import com.learnian.core.view.resources.review_notebook_stat_score
import com.learnian.core.view.resources.review_notebook_stat_total
import com.learnian.core.view.resources.review_notebook_subtitle
import com.learnian.core.view.resources.review_percent_format
import com.learnian.core.view.resources.review_quick_review
import com.learnian.core.view.resources.review_stat_difficulty
import com.learnian.core.view.resources.review_stat_difficulty_value_format
import com.learnian.core.view.resources.review_stat_mastery
import com.learnian.core.view.resources.review_stat_review_count_format
import com.learnian.core.view.resources.review_stat_reviews
import com.learnian.core.view.resources.review_stat_stability
import com.learnian.core.view.resources.review_stat_stability_format
import com.learnian.review.domain.model.LibraryWord
import com.learnian.review.domain.model.ReviewLibrary
import com.learnian.review.presentation.mapper.notebookCounts
import com.learnian.review.presentation.mapper.notebookWords
import com.learnian.review.presentation.mvi.NotebookFilter
import com.learnian.review.presentation.mvi.ReviewSection
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

/**
 * Review notebook: FSRS performance strip, quick filters and the word cards.
 *
 * Every card is addressed by [LibraryWord.wordId], so tapping it opens the exact
 * same word card the detail screen renders.
 */
@Composable
fun ReviewNotebookSection(
    library: ReviewLibrary,
    filter: NotebookFilter,
    onFilterChange: (NotebookFilter) -> Unit,
    onWordClick: (String) -> Unit,
    onQuickReview: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val words = library.notebookWords(filter)
    val counts = library.notebookCounts()
    val summary = library.summary

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
                    section = ReviewSection.NOTEBOOK,
                    subtitle = stringResource(Res.string.review_notebook_subtitle),
                )

                HubStatStrip(
                    stats = listOf(
                        HubStat(
                            value = summary.totalWords.toString(),
                            label = stringResource(Res.string.review_notebook_stat_total),
                        ),
                        HubStat(
                            value = summary.fsrsReviews.toString(),
                            label = stringResource(Res.string.review_notebook_stat_reviews),
                            valueColor = ClayTheme.colors.tertiary,
                        ),
                        HubStat(
                            value = summary.masteredWords.toString(),
                            label = stringResource(Res.string.review_notebook_stat_mastered),
                            valueColor = ClayTheme.colors.secondary,
                        ),
                        HubStat(
                            value = stringResource(
                                Res.string.review_percent_format,
                                summary.overallMasteryPercent.toString(),
                            ),
                            label = stringResource(Res.string.review_notebook_stat_score),
                            valueColor = ClayTheme.colors.primary,
                        ),
                    ),
                )
            }
        }

        HubFilterRow(
            options = NotebookFilter.entries.map { notebookFilter ->
                HubChipOption(
                    key = notebookFilter.name,
                    label = notebookFilter.label(),
                    count = counts[notebookFilter] ?: 0,
                )
            },
            selectedKey = filter.name,
            onSelect = { key -> onFilterChange(NotebookFilter.valueOf(key)) },
        )

        if (words.isEmpty()) {
            HubEmptyState(text = stringResource(Res.string.review_notebook_empty))
        } else {
            words.forEach { word ->
                NotebookWordCard(
                    word = word,
                    onClick = { onWordClick(word.wordId) },
                    onQuickReview = { onQuickReview(word.wordId) },
                )
            }
        }
    }
}

@Composable
private fun NotebookWordCard(
    word: LibraryWord,
    onClick: () -> Unit,
    onQuickReview: () -> Unit,
) {
    ClayCard(
        modifier = Modifier.fillMaxWidth(),
        padding = Spacing.Medium,
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
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
                                fontSize = 19.sp,
                            ),
                            color = ClayTheme.colors.textPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false),
                        )

                        ClayStatusChip(
                            text = levelChipLabel(word.level),
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

                ClayStatusChip(
                    text = word.status.label(),
                    style = ClayChipStyle.Resting,
                    backgroundColor = word.status.containerColor(),
                    textColor = word.status.contentColor(),
                    shadowColor = word.status.contentColor().copy(alpha = 0.18f),
                    textStyle = AppTypography.labelMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 10.sp,
                    ),
                    contentPadding = PaddingValues(
                        horizontal = Spacing.Small,
                        vertical = Spacing.Tiny,
                    ),
                )
            }

            HubStatStrip(
                stats = listOf(
                    HubStat(
                        value = stringResource(
                            Res.string.review_percent_format,
                            word.masteryPercent.toString(),
                        ),
                        label = stringResource(Res.string.review_stat_mastery),
                        valueColor = word.status.accentColor(),
                    ),
                    HubStat(
                        value = stringResource(
                            Res.string.review_stat_review_count_format,
                            word.reviewCount,
                        ),
                        label = stringResource(Res.string.review_stat_reviews),
                    ),
                    HubStat(
                        value = stringResource(
                            Res.string.review_stat_stability_format,
                            word.stabilityHours,
                        ),
                        label = stringResource(Res.string.review_stat_stability),
                    ),
                    HubStat(
                        value = stringResource(
                            Res.string.review_stat_difficulty_value_format,
                            word.difficulty,
                        ),
                        label = stringResource(Res.string.review_stat_difficulty),
                    ),
                ),
            )

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall)) {
                ReviewScheduleLine(
                    icon = Res.drawable.ic_clock,
                    text = stringResource(
                        Res.string.review_last_reviewed_format,
                        stringResource(
                            Res.string.review_hours_ago_format,
                            word.lastReviewedHoursAgo,
                        ),
                    ),
                )
                ReviewScheduleLine(
                    icon = Res.drawable.ic_update_check,
                    text = stringResource(
                        Res.string.review_next_review_format,
                        stringResource(
                            Res.string.review_days_format,
                            formatDecimal(word.nextReviewDays),
                        ),
                    ),
                )
            }

            ClayButton(
                text = stringResource(Res.string.review_quick_review),
                onClick = onQuickReview,
                modifier = Modifier.fillMaxWidth(),
                variant = ClayButtonVariant.Surface,
                cornerRadius = AppCorners.Medium,
                contentPadding = PaddingValues(vertical = Spacing.Standard),
            )
        }
    }
}

@Composable
private fun ReviewScheduleLine(
    icon: DrawableResource,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.Compact),
    ) {
        BaseIcon(
            resource = icon,
            contentDescription = null,
            modifier = Modifier.size(13.dp),
            tint = ClayTheme.colors.textSecondary,
        )
        Text(
            text = text,
            style = AppTypography.bodySmall.copy(fontSize = 10.sp),
            color = ClayTheme.colors.textSecondary,
        )
    }
}

@Composable
private fun NotebookFilter.label(): String = stringResource(
    when (this) {
        NotebookFilter.ALL -> Res.string.review_filter_all
        NotebookFilter.FREQUENT -> Res.string.review_filter_frequent
        NotebookFilter.NEEDS_REVIEW -> Res.string.review_filter_needs_review
    },
)
