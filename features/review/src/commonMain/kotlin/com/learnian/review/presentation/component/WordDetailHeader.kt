package com.learnian.review.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.learnian.core.view.resources.ic_bookmark
import com.learnian.core.view.resources.ic_ribbon_medal
import com.learnian.core.view.resources.ic_volume_up
import com.learnian.core.view.resources.word_detail_audio_uk
import com.learnian.core.view.resources.word_detail_audio_us
import com.learnian.core.view.resources.word_detail_bookmark_desc
import com.learnian.core.view.resources.word_detail_level_format
import com.learnian.review.domain.model.WordReviewData
import org.jetbrains.compose.resources.stringResource

/**
 * Hero card of the word-detail screen: level badge, bookmark, headword,
 * phonetics, UK/US pronunciation and part of speech.
 */
@Composable
fun WordDetailHeader(
    wordData: WordReviewData,
    onToggleBookmark: () -> Unit,
    onPlayAudio: (String) -> Unit,
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
                    cornerRadius = AppCorners.Pill,
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

                if (wordData.level.isNotBlank()) {
                    ClayStatusChip(
                        text = stringResource(
                            Res.string.word_detail_level_format,
                            wordData.level,
                        ),
                        style = ClayChipStyle.Embossed,
                        backgroundColor = ClayTheme.colors.secondaryContainer,
                        textColor = ClayTheme.colors.onSecondaryContainer,
                        textStyle = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        icon = {
                            BaseIcon(
                                resource = Res.drawable.ic_ribbon_medal,
                                contentDescription = null,
                                tint = ClayTheme.colors.onSecondaryContainer,
                                modifier = Modifier.size(14.dp),
                            )
                        },
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

            if (wordData.phonetic.isNotBlank()) {
                Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
                Text(
                    text = wordData.phonetic,
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                    ),
                    color = ClayTheme.colors.textSecondary,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            ) {
                DetailAudioChip(
                    label = stringResource(Res.string.word_detail_audio_uk),
                    onClick = { onPlayAudio("UK") },
                )
                DetailAudioChip(
                    label = stringResource(Res.string.word_detail_audio_us),
                    onClick = { onPlayAudio("US") },
                )
            }

            if (wordData.primaryPartOfSpeechFa.isNotBlank() || wordData.primaryPartOfSpeechEn.isNotBlank()) {
                Spacer(modifier = Modifier.height(Spacing.Small))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                ) {
                    if (wordData.primaryPartOfSpeechFa.isNotBlank()) {
                        ClayStatusChip(
                            text = wordData.primaryPartOfSpeechFa,
                            style = ClayChipStyle.Resting,
                            backgroundColor = ClayTheme.colors.primaryContainer,
                            textColor = ClayTheme.colors.primary,
                            textStyle = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        )
                    }

                    if (wordData.primaryPartOfSpeechEn.isNotBlank()) {
                        ClayStatusChip(
                            text = wordData.primaryPartOfSpeechEn,
                            style = ClayChipStyle.Resting,
                            backgroundColor = ClayTheme.colors.surfaceSunken,
                            textColor = ClayTheme.colors.textSecondary,
                            textStyle = AppTypography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailAudioChip(
    label: String,
    onClick: () -> Unit,
) {
    ClayStatusChip(
        text = label,
        style = ClayChipStyle.Resting,
        backgroundColor = ClayTheme.colors.primaryContainer,
        textColor = ClayTheme.colors.primary,
        textStyle = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
        iconPosition = ChipIconPosition.Start,
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
