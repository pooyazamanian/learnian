package com.learnian.review.presentation.component.hub

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.all_notebook
import com.learnian.core.view.resources.book
import com.learnian.core.view.resources.notebook
import com.learnian.core.view.resources.review_level_advanced
import com.learnian.core.view.resources.review_level_beginner
import com.learnian.core.view.resources.review_level_intermediate
import com.learnian.core.view.resources.review_section_dictionary
import com.learnian.core.view.resources.review_section_dictionary_short
import com.learnian.core.view.resources.review_section_notebook
import com.learnian.core.view.resources.review_section_notebook_short
import com.learnian.core.view.resources.review_section_saved
import com.learnian.core.view.resources.review_section_saved_short
import com.learnian.core.view.resources.review_status_challenging
import com.learnian.core.view.resources.review_status_needs_review
import com.learnian.core.view.resources.review_status_stable
import com.learnian.review.domain.model.WordMasteryStatus
import com.learnian.review.presentation.mvi.ReviewSection
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

/** Localized name of a mastery state (badge of a word card). */
@Composable
fun WordMasteryStatus.label(): String = stringResource(
    when (this) {
        WordMasteryStatus.CHALLENGING -> Res.string.review_status_challenging
        WordMasteryStatus.NEEDS_REVIEW -> Res.string.review_status_needs_review
        WordMasteryStatus.STABLE -> Res.string.review_status_stable
    },
)

/** Container color of the mastery badge. */
@Composable
fun WordMasteryStatus.containerColor(): Color = when (this) {
    WordMasteryStatus.CHALLENGING -> ClayTheme.colors.errorContainer
    WordMasteryStatus.NEEDS_REVIEW -> ClayTheme.colors.tertiaryContainer
    WordMasteryStatus.STABLE -> ClayTheme.colors.secondaryContainer
}

/** Text color of the mastery badge. */
@Composable
fun WordMasteryStatus.contentColor(): Color = when (this) {
    WordMasteryStatus.CHALLENGING -> ClayTheme.colors.onErrorContainer
    WordMasteryStatus.NEEDS_REVIEW -> ClayTheme.colors.onTertiaryContainer
    WordMasteryStatus.STABLE -> ClayTheme.colors.onSecondaryContainer
}

/** Accent used for progress fills and hints. */
@Composable
fun WordMasteryStatus.accentColor(): Color = when (this) {
    WordMasteryStatus.CHALLENGING -> ClayTheme.colors.error
    WordMasteryStatus.NEEDS_REVIEW -> ClayTheme.colors.tertiary
    WordMasteryStatus.STABLE -> ClayTheme.colors.secondary
}

/** Full section title shown in the header card. */
@Composable
fun ReviewSection.title(): String = stringResource(
    when (this) {
        ReviewSection.NOTEBOOK -> Res.string.review_section_notebook
        ReviewSection.DICTIONARY -> Res.string.review_section_dictionary
        ReviewSection.SAVED -> Res.string.review_section_saved
    },
)

/** Short section name used inside the segmented tabs. */
@Composable
fun ReviewSection.shortTitle(): String = stringResource(
    when (this) {
        ReviewSection.NOTEBOOK -> Res.string.review_section_notebook_short
        ReviewSection.DICTIONARY -> Res.string.review_section_dictionary_short
        ReviewSection.SAVED -> Res.string.review_section_saved_short
    },
)

/** Soft clay container behind the section illustration. */
@Composable
fun ReviewSection.tileColor(): Color = when (this) {
    ReviewSection.NOTEBOOK -> ClayTheme.colors.primaryContainer
    ReviewSection.DICTIONARY -> ClayTheme.colors.secondaryContainer
    ReviewSection.SAVED -> ClayTheme.colors.tertiaryContainer
}

/** Accent of the section illustration and its highlights. */
@Composable
fun ReviewSection.accentColor(): Color = when (this) {
    ReviewSection.NOTEBOOK -> ClayTheme.colors.primary
    ReviewSection.DICTIONARY -> ClayTheme.colors.secondary
    ReviewSection.SAVED -> ClayTheme.colors.tertiary
}

/** Clay illustration that represents the section. */
@Composable
fun ReviewSection.illustration(): DrawableResource = when (this) {
    ReviewSection.NOTEBOOK -> Res.drawable.notebook
    ReviewSection.DICTIONARY -> Res.drawable.book
    ReviewSection.SAVED -> Res.drawable.all_notebook
}

/** Localized CEFR band of a level code such as `B2` or `B2/C1`. */
@Composable
fun levelLabel(level: String): String = stringResource(
    when (level.firstOrNull()?.uppercaseChar()) {
        'A' -> Res.string.review_level_beginner
        'B' -> Res.string.review_level_intermediate
        else -> Res.string.review_level_advanced
    },
)

/** Compact chip text: CEFR code plus its localized band, e.g. `B2 · Intermediate`. */
@Composable
fun levelChipLabel(level: String): String = "${level} · ${levelLabel(level)}"

/** Small caps label used above a stat. */
@Composable
fun statLabelStyle(): TextStyle = AppTypography.labelMedium.copy(
    fontWeight = FontWeight.Bold,
    fontSize = 10.sp,
)

/** Formats a one-decimal scheduling value without locale surprises. */
fun formatDecimal(value: Float): String {
    val rounded = (value * 10f).roundToInt() / 10f
    return if (rounded % 1f == 0f) rounded.toInt().toString() else rounded.toString()
}
