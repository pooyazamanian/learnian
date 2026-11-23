package com.learnian.review.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayIconButton
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ChipIconPosition
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_arrow_left
import com.learnian.core.view.resources.ic_bookmark
import com.learnian.core.view.resources.ic_lightbulb
import com.learnian.core.view.resources.ic_link
import com.learnian.core.view.resources.ic_quote
import com.learnian.core.view.resources.ic_volume_up
import com.learnian.core.view.resources.review_context_sentence_label
import com.learnian.core.view.resources.review_meaning_label
import com.learnian.core.view.resources.review_mnemonic_label
import com.learnian.core.view.resources.review_synonyms_label
import com.learnian.core.view.resources.review_view_full_detail
import com.learnian.core.view.resources.word_detail_audio_uk
import com.learnian.core.view.resources.word_detail_audio_us
import com.learnian.core.view.resources.word_detail_bookmark_desc
import com.learnian.review.domain.model.WordReviewData
import org.jetbrains.compose.resources.stringResource

/**
 * Flashcard body of the daily review screen: word, pronunciation, meaning,
 * a real-context sentence, synonyms, mnemonic hook and the entry point to the
 * full word-detail screen.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReviewCardContent(
    wordData: WordReviewData,
    onToggleBookmark: () -> Unit,
    onPlayAudio: (String) -> Unit,
    onViewDetailClick: () -> Unit,
    modifier: Modifier = Modifier,
    bookmarkEnabled: Boolean = true,
) {
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = ClayTheme.colors.surface,
        cornerRadius = AppCorners.XL,
        padding = Spacing.Medium,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ClayIconButton(
                    onClick = onToggleBookmark,
                    enabled = bookmarkEnabled,
                    size = 40.dp,
                    cornerRadius = AppCorners.Medium,
                    backgroundColor = if (wordData.isBookmarked) {
                        ClayTheme.colors.primaryContainer
                    } else {
                        ClayTheme.colors.surfaceSunken
                    },
                ) {
                    BaseIcon(
                        resource = Res.drawable.ic_bookmark,
                        contentDescription = stringResource(Res.string.word_detail_bookmark_desc),
                        tint = if (wordData.isBookmarked) {
                            ClayTheme.colors.primary
                        } else {
                            ClayTheme.colors.textSecondary
                        },
                        modifier = Modifier.size(18.dp),
                    )
                }

                val partOfSpeech = listOf(
                    wordData.primaryPartOfSpeechEn,
                    wordData.primaryPartOfSpeechFa,
                ).filter { it.isNotBlank() }.joinToString(separator = " • ")

                if (partOfSpeech.isNotBlank()) {
                    ClayStatusChip(
                        text = partOfSpeech,
                        style = ClayChipStyle.Resting,
                        backgroundColor = ClayTheme.colors.secondaryContainer,
                        textColor = ClayTheme.colors.onSecondaryContainer,
                        textStyle = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.Comfortable))

            Text(
                text = wordData.word,
                style = AppTypography.headlineLarge.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = 34.sp,
                ),
                color = ClayTheme.colors.textPrimary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(Spacing.ExtraSmall))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = wordData.phonetic,
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                    ),
                    color = ClayTheme.colors.textSecondary,
                )

                Spacer(modifier = Modifier.width(Spacing.Small))

                AudioChip(label = stringResource(Res.string.word_detail_audio_us)) { onPlayAudio("US") }
                Spacer(modifier = Modifier.width(Spacing.ExtraSmall))
                AudioChip(label = stringResource(Res.string.word_detail_audio_uk)) { onPlayAudio("UK") }
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.28f)
                    .height(3.dp)
                    .clip(RoundedCornerShape(AppCorners.Pill))
                    .background(ClayTheme.colors.primary.copy(alpha = 0.2f)),
            )

            Spacer(modifier = Modifier.height(Spacing.Medium))

            Text(
                text = stringResource(Res.string.review_meaning_label),
                style = AppTypography.labelSmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                ),
                color = ClayTheme.colors.textSecondary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(Spacing.ExtraSmall))

            Text(
                text = wordData.persianMeanings.take(3).joinToString("، "),
                style = AppTypography.headlineSmall.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                ),
                color = ClayTheme.colors.primary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(Spacing.Large))

            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = ClayTheme.colors.primary.copy(alpha = 0.06f),
                cornerRadius = AppCorners.Large,
                padding = Spacing.Medium,
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        ClayIconButton(
                            onClick = { onPlayAudio("US") },
                            size = 32.dp,
                            cornerRadius = AppCorners.Pill,
                            backgroundColor = ClayTheme.colors.surface,
                        ) {
                            BaseIcon(
                                resource = Res.drawable.ic_volume_up,
                                contentDescription = null,
                                tint = ClayTheme.colors.primary,
                                modifier = Modifier.size(16.dp),
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(Res.string.review_context_sentence_label),
                                style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = ClayTheme.colors.primary,
                            )
                            Spacer(modifier = Modifier.width(Spacing.ExtraSmall))
                            BaseIcon(
                                resource = Res.drawable.ic_quote,
                                contentDescription = null,
                                tint = ClayTheme.colors.primary,
                                modifier = Modifier.size(14.dp),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(Spacing.Small))

                    HighlightedSentence(
                        sentence = wordData.contextSentence.englishText,
                        keyword = wordData.contextSentence.highlightedWord,
                        style = AppTypography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            lineHeight = 23.sp,
                        ),
                    )

                    Spacer(modifier = Modifier.height(Spacing.ExtraSmall))

                    Text(
                        text = wordData.contextSentence.persianText,
                        style = AppTypography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                        ),
                        color = ClayTheme.colors.textSecondary,
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(Res.string.review_synonyms_label),
                    style = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = ClayTheme.colors.textSecondary,
                )
                Spacer(modifier = Modifier.width(Spacing.ExtraSmall))
                BaseIcon(
                    resource = Res.drawable.ic_link,
                    contentDescription = null,
                    tint = ClayTheme.colors.textSecondary,
                    modifier = Modifier.size(14.dp),
                )
            }

            Spacer(modifier = Modifier.height(Spacing.Small))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall),
            ) {
                wordData.synonyms.take(5).forEachIndexed { index, synonym ->
                    val (container, content) = when (index % 4) {
                        0 -> ClayTheme.colors.secondaryContainer to ClayTheme.colors.onSecondaryContainer
                        1 -> ClayTheme.colors.tertiaryContainer to ClayTheme.colors.onTertiaryContainer
                        2 -> ClayTheme.colors.primaryContainer to ClayTheme.colors.primary
                        else -> ClayTheme.colors.surfaceSunken to ClayTheme.colors.textPrimary
                    }
                    ClayStatusChip(
                        text = synonym,
                        style = ClayChipStyle.Resting,
                        backgroundColor = container,
                        textColor = content,
                        textStyle = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 3.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = ClayTheme.colors.tertiaryContainer,
                cornerRadius = AppCorners.Large,
                padding = Spacing.Small,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(ClayTheme.colors.onTertiaryContainer.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        BaseIcon(
                            resource = Res.drawable.ic_lightbulb,
                            contentDescription = null,
                            tint = ClayTheme.colors.onTertiaryContainer,
                            modifier = Modifier.size(20.dp),
                        )
                    }

                    Spacer(modifier = Modifier.width(Spacing.Small))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(Res.string.review_mnemonic_label),
                            style = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = ClayTheme.colors.onTertiaryContainer,
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = wordData.mnemonic.hookFa,
                            style = AppTypography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                lineHeight = 19.sp,
                            ),
                            color = ClayTheme.colors.textPrimary,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            ClayStatusChip(
                text = stringResource(Res.string.review_view_full_detail),
                style = ClayChipStyle.Resting,
                backgroundColor = ClayTheme.colors.primary,
                textColor = ClayTheme.colors.onPrimary,
                textStyle = AppTypography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                ),
                iconPosition = ChipIconPosition.Start,
                icon = {
                    BaseIcon(
                        resource = Res.drawable.ic_arrow_left,
                        contentDescription = null,
                        tint = ClayTheme.colors.onPrimary,
                        modifier = Modifier.size(16.dp),
                    )
                },
                onClick = onViewDetailClick,
            )
        }
    }
}

@Composable
private fun AudioChip(
    label: String,
    onClick: () -> Unit,
) {
    ClayStatusChip(
        text = label,
        style = ClayChipStyle.Resting,
        backgroundColor = ClayTheme.colors.primaryContainer,
        textColor = ClayTheme.colors.primary,
        textStyle = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
        icon = {
            BaseIcon(
                resource = Res.drawable.ic_volume_up,
                contentDescription = null,
                tint = ClayTheme.colors.primary,
                modifier = Modifier.size(14.dp),
            )
        },
        onClick = onClick,
    )
}
