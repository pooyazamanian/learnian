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
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_brain_ai
import com.learnian.core.view.resources.ic_language
import com.learnian.core.view.resources.ic_lightbulb
import com.learnian.core.view.resources.ic_link
import com.learnian.core.view.resources.ic_quote
import com.learnian.core.view.resources.ic_target
import com.learnian.core.view.resources.owl
import com.learnian.core.view.resources.word_detail_antonyms_title
import com.learnian.core.view.resources.word_detail_etymology_derivatives
import com.learnian.core.view.resources.word_detail_examples_count_format
import com.learnian.core.view.resources.word_detail_no_derivatives
import com.learnian.core.view.resources.word_detail_section_collocations
import com.learnian.core.view.resources.word_detail_section_etymology
import com.learnian.core.view.resources.word_detail_section_meaning
import com.learnian.core.view.resources.word_detail_section_mnemonic
import com.learnian.core.view.resources.word_detail_section_real_examples
import com.learnian.core.view.resources.word_detail_section_synonyms_antonyms
import com.learnian.core.view.resources.word_detail_simple_english
import com.learnian.core.view.resources.word_detail_synonyms_title
import com.learnian.review.domain.model.WordReviewData
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

// ---------------------------------------------------------------------------------------
// 2. Practical & lexical meaning
// ---------------------------------------------------------------------------------------

@Composable
fun WordDetailMeaningSection(
    wordData: WordReviewData,
    modifier: Modifier = Modifier,
) {
    WordDetailSectionCard(
        title = stringResource(Res.string.word_detail_section_meaning),
        icon = Res.drawable.ic_language,
        modifier = modifier,
    ) {
        if (wordData.persianMeanings.isNotEmpty() || wordData.persianExplanation.isNotBlank()) {
            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = ClayTheme.colors.primary.copy(alpha = 0.06f),
                cornerRadius = AppCorners.Large,
                padding = Spacing.Medium,
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = wordData.persianMeanings.joinToString("، "),
                        style = AppTypography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            lineHeight = 27.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                    )

                    if (wordData.persianExplanation.isNotBlank()) {
                        Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
                        Text(
                            text = wordData.persianExplanation,
                            style = AppTypography.bodySmall.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                            ),
                            color = ClayTheme.colors.textSecondary,
                        )
                    }
                }
            }
        }

        if (wordData.simpleEnglishDefinition.isNotBlank()) {
            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = ClayTheme.colors.surfaceSunken,
                cornerRadius = AppCorners.Large,
                padding = Spacing.Medium,
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(Res.string.word_detail_simple_english),
                        style = AppTypography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 11.sp,
                            letterSpacing = 0.5.sp,
                        ),
                        color = ClayTheme.colors.textSecondary,
                    )
                    Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
                    Text(
                        text = wordData.simpleEnglishDefinition,
                        style = AppTypography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------------------
// 3. AI coding & mnemonic
// ---------------------------------------------------------------------------------------

@Composable
fun WordDetailMnemonicSection(
    wordData: WordReviewData,
    modifier: Modifier = Modifier,
) {
    val mnemonic = wordData.mnemonic

    WordDetailSectionCard(
        title = stringResource(Res.string.word_detail_section_mnemonic),
        icon = Res.drawable.ic_brain_ai,
        modifier = modifier,
        trailing = {
            if (mnemonic.codingTag.isNotBlank()) {
                ClayStatusChip(
                    text = mnemonic.codingTag,
                    style = ClayChipStyle.Resting,
                    backgroundColor = ClayTheme.colors.primaryContainer,
                    textColor = ClayTheme.colors.primary,
                    textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                )
            }
        },
    ) {
        ClayCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = ClayTheme.colors.tertiaryContainer,
            cornerRadius = AppCorners.Large,
            padding = Spacing.Medium,
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                ) {
                    BaseImage(
                        url = mnemonic.illustrationUrl,
                        fallbackResource = Res.drawable.owl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(84.dp)
                            .clip(RoundedCornerShape(AppCorners.Medium)),
                    )

                    Spacer(modifier = Modifier.width(Spacing.Comfortable))

                    Column(modifier = Modifier.weight(1f)) {
                        if (mnemonic.title.isNotBlank()) {
                            Text(
                                text = mnemonic.title,
                                style = AppTypography.titleSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                ),
                                color = ClayTheme.colors.onTertiaryContainer,
                            )
                            Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
                        }

                        Text(
                            text = mnemonic.story,
                            style = AppTypography.bodySmall.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                            ),
                            color = ClayTheme.colors.textPrimary,
                        )
                    }
                }

                if (mnemonic.hookFa.isNotBlank()) {
                    Spacer(modifier = Modifier.height(Spacing.Comfortable))

                    ClayCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = ClayTheme.colors.surface,
                        cornerRadius = AppCorners.Medium,
                        padding = Spacing.Small,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(ClayTheme.colors.tertiaryContainer),
                                contentAlignment = Alignment.Center,
                            ) {
                                BaseIcon(
                                    resource = Res.drawable.ic_lightbulb,
                                    contentDescription = null,
                                    tint = ClayTheme.colors.onTertiaryContainer,
                                    modifier = Modifier.size(18.dp),
                                )
                            }

                            Spacer(modifier = Modifier.width(Spacing.Small))

                            Text(
                                text = mnemonic.hookFa,
                                style = AppTypography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    lineHeight = 19.sp,
                                ),
                                color = ClayTheme.colors.textPrimary,
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------------------
// 4. Etymology & derivatives
// ---------------------------------------------------------------------------------------

@Composable
fun WordDetailEtymologySection(
    wordData: WordReviewData,
    modifier: Modifier = Modifier,
) {
    val etymology = wordData.etymology

    WordDetailSectionCard(
        title = stringResource(Res.string.word_detail_section_etymology),
        icon = Res.drawable.ic_target,
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            ClayCard(
                modifier = Modifier.weight(1f),
                backgroundColor = ClayTheme.colors.surfaceSunken,
                cornerRadius = AppCorners.Large,
                padding = Spacing.Small,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(Res.string.word_detail_etymology_derivatives),
                        style = AppTypography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = ClayTheme.colors.textSecondary,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(Spacing.ExtraSmall))

                    if (etymology.derivatives.isEmpty()) {
                        Text(
                            text = stringResource(Res.string.word_detail_no_derivatives),
                            style = AppTypography.bodySmall.copy(fontSize = 11.sp),
                            color = ClayTheme.colors.textSecondary,
                            textAlign = TextAlign.Center,
                        )
                    } else {
                        etymology.derivatives.take(2).forEachIndexed { index, derivative ->
                            if (index > 0) {
                                Spacer(modifier = Modifier.height(Spacing.Compact))
                            }

                            Text(
                                text = derivative.englishTerm +
                                    if (derivative.partOfSpeech.isNotBlank()) " (${derivative.partOfSpeech})" else "",
                                style = AppTypography.titleSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp,
                                ),
                                color = ClayTheme.colors.textPrimary,
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = derivative.persianMeaning,
                                style = AppTypography.labelSmall.copy(fontSize = 11.sp),
                                color = ClayTheme.colors.textSecondary,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }

            ClayCard(
                modifier = Modifier.weight(1f),
                backgroundColor = ClayTheme.colors.surfaceSunken,
                cornerRadius = AppCorners.Large,
                padding = Spacing.Small,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = etymology.rootOrigin,
                        style = AppTypography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = ClayTheme.colors.textSecondary,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
                    Text(
                        text = etymology.rootTerm,
                        style = AppTypography.titleSmall.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(Spacing.Tiny))
                    Text(
                        text = etymology.rootMeaning,
                        style = AppTypography.labelSmall.copy(fontSize = 11.sp),
                        color = ClayTheme.colors.textSecondary,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------------------
// 5. Collocations
// ---------------------------------------------------------------------------------------

@Composable
fun WordDetailCollocationsSection(
    wordData: WordReviewData,
    modifier: Modifier = Modifier,
) {
    if (wordData.collocations.isEmpty()) return

    WordDetailSectionCard(
        title = stringResource(Res.string.word_detail_section_collocations),
        icon = Res.drawable.ic_link,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            wordData.collocations.forEach { collocation ->
                ClayCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = ClayTheme.colors.surfaceSunken,
                    cornerRadius = AppCorners.Large,
                    padding = Spacing.Small,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(ClayTheme.colors.primaryContainer),
                            contentAlignment = Alignment.Center,
                        ) {
                            BaseIcon(
                                resource = collocationIcon(collocation.iconKey),
                                contentDescription = null,
                                tint = ClayTheme.colors.primary,
                                modifier = Modifier.size(16.dp),
                            )
                        }

                        Spacer(modifier = Modifier.width(Spacing.Small))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = collocation.englishPhrase,
                                style = AppTypography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                ),
                                color = ClayTheme.colors.textPrimary,
                            )
                            Text(
                                text = collocation.persianMeaning,
                                style = AppTypography.labelSmall.copy(fontSize = 12.sp),
                                color = ClayTheme.colors.textSecondary,
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun collocationIcon(iconKey: String): DrawableResource {
    return when (iconKey) {
        "ship" -> Res.drawable.ic_link
        "sad" -> Res.drawable.ic_lightbulb
        "cancel" -> Res.drawable.ic_target
        else -> Res.drawable.ic_link
    }
}

// ---------------------------------------------------------------------------------------
// 6. Synonyms & antonyms
// ---------------------------------------------------------------------------------------

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordDetailRelationsSection(
    wordData: WordReviewData,
    modifier: Modifier = Modifier,
) {
    if (wordData.synonyms.isEmpty() && wordData.antonyms.isEmpty()) return

    WordDetailSectionCard(
        title = stringResource(Res.string.word_detail_section_synonyms_antonyms),
        icon = Res.drawable.ic_language,
        modifier = modifier,
    ) {
        if (wordData.synonyms.isNotEmpty()) {
            Text(
                text = stringResource(Res.string.word_detail_synonyms_title),
                style = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = ClayTheme.colors.secondary,
                modifier = Modifier.align(Alignment.Start),
            )
            Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall),
            ) {
                wordData.synonyms.forEach { synonym ->
                    ClayStatusChip(
                        text = synonym,
                        style = ClayChipStyle.Resting,
                        backgroundColor = ClayTheme.colors.secondaryContainer,
                        textColor = ClayTheme.colors.onSecondaryContainer,
                        textStyle = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 3.dp),
                    )
                }
            }
        }

        if (wordData.antonyms.isNotEmpty()) {
            Spacer(modifier = Modifier.height(Spacing.Small))
            Text(
                text = stringResource(Res.string.word_detail_antonyms_title),
                style = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = ClayTheme.colors.error,
                modifier = Modifier.align(Alignment.Start),
            )
            Spacer(modifier = Modifier.height(Spacing.ExtraSmall))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall),
            ) {
                wordData.antonyms.forEach { antonym ->
                    ClayStatusChip(
                        text = antonym,
                        style = ClayChipStyle.Resting,
                        backgroundColor = ClayTheme.colors.errorContainer,
                        textColor = ClayTheme.colors.onErrorContainer,
                        textStyle = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 3.dp),
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------------------
// 7. Real-context examples
// ---------------------------------------------------------------------------------------

@Composable
fun WordDetailExamplesSection(
    wordData: WordReviewData,
    modifier: Modifier = Modifier,
) {
    if (wordData.realExamples.isEmpty()) return

    WordDetailSectionCard(
        title = stringResource(Res.string.word_detail_section_real_examples),
        icon = Res.drawable.ic_quote,
        modifier = modifier,
        trailing = {
            ClayStatusChip(
                text = stringResource(
                    Res.string.word_detail_examples_count_format,
                    wordData.realExamples.size.toString(),
                ),
                style = ClayChipStyle.Resting,
                backgroundColor = ClayTheme.colors.surfaceSunken,
                textColor = ClayTheme.colors.textSecondary,
                textStyle = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
            )
        },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            wordData.realExamples.forEach { example ->
                ClayCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = ClayTheme.colors.primary.copy(alpha = 0.05f),
                    cornerRadius = AppCorners.Large,
                    padding = Spacing.Medium,
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        BaseIcon(
                            resource = Res.drawable.ic_quote,
                            contentDescription = null,
                            tint = ClayTheme.colors.primary.copy(alpha = 0.6f),
                            modifier = Modifier.size(16.dp),
                        )

                        Spacer(modifier = Modifier.height(Spacing.ExtraSmall))

                        HighlightedSentence(
                            sentence = example.englishText,
                            keyword = example.highlightedWord,
                            style = AppTypography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                            ),
                        )

                        Spacer(modifier = Modifier.height(Spacing.ExtraSmall))

                        Text(
                            text = example.persianText,
                            style = AppTypography.bodySmall.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                            ),
                            color = ClayTheme.colors.textSecondary,
                        )
                    }
                }
            }
        }
    }
}
