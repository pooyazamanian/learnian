package com.learnian.learning.presentation.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_gem
import com.learnian.core.view.resources.ic_layers
import com.learnian.core.view.resources.ic_lightning
import com.learnian.core.view.resources.ic_school
import com.learnian.core.view.resources.learning_category_exams
import com.learnian.core.view.resources.learning_category_general
import com.learnian.core.view.resources.learning_category_migration
import com.learnian.core.view.resources.learning_details_mode_flashcards
import com.learnian.core.view.resources.learning_details_mode_flashcards_desc
import com.learnian.core.view.resources.learning_details_mode_quiz
import com.learnian.core.view.resources.learning_details_mode_quiz_desc
import com.learnian.core.view.resources.learning_level_a1a2_desc
import com.learnian.core.view.resources.learning_level_a1a2_title
import com.learnian.core.view.resources.learning_level_b1_desc
import com.learnian.core.view.resources.learning_level_b1_title
import com.learnian.core.view.resources.learning_level_b2_desc
import com.learnian.core.view.resources.learning_level_b2_title
import com.learnian.core.view.resources.learning_level_c1c2_desc
import com.learnian.core.view.resources.learning_level_c1c2_title
import com.learnian.core.view.resources.learning_level_code_a1a2
import com.learnian.core.view.resources.learning_level_code_b1
import com.learnian.core.view.resources.learning_level_code_b2
import com.learnian.core.view.resources.learning_level_code_c1c2
import com.learnian.learning.domain.model.CefrBand
import com.learnian.learning.domain.model.LearningCatalog
import com.learnian.learning.domain.model.LearningPackage
import com.learnian.learning.domain.model.PackageCategory
import com.learnian.learning.domain.model.PracticeMode
import kotlin.math.roundToInt
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

/** Localized label of a discovery category. */
fun PackageCategory.labelRes(): StringResource = when (this) {
    PackageCategory.GENERAL -> Res.string.learning_category_general
    PackageCategory.EXAMS -> Res.string.learning_category_exams
    PackageCategory.MIGRATION -> Res.string.learning_category_migration
}

/** Localized title of a practice mode. */
fun PracticeMode.titleRes(): StringResource = when (this) {
    PracticeMode.FLASHCARDS -> Res.string.learning_details_mode_flashcards
    PracticeMode.QUIZ -> Res.string.learning_details_mode_quiz
}

/** Localized supporting caption of a practice mode. */
fun PracticeMode.descriptionRes(): StringResource = when (this) {
    PracticeMode.FLASHCARDS -> Res.string.learning_details_mode_flashcards_desc
    PracticeMode.QUIZ -> Res.string.learning_details_mode_quiz_desc
}

/** International code shown on the level cards (A1 - A2, B1, ...). */
fun CefrBand.codeRes(): StringResource = when (this) {
    CefrBand.A1_A2 -> Res.string.learning_level_code_a1a2
    CefrBand.B1 -> Res.string.learning_level_code_b1
    CefrBand.B2 -> Res.string.learning_level_code_b2
    CefrBand.C1_C2 -> Res.string.learning_level_code_c1c2
}

/** Localized headline of the level card. */
fun CefrBand.titleRes(): StringResource = when (this) {
    CefrBand.A1_A2 -> Res.string.learning_level_a1a2_title
    CefrBand.B1 -> Res.string.learning_level_b1_title
    CefrBand.B2 -> Res.string.learning_level_b2_title
    CefrBand.C1_C2 -> Res.string.learning_level_c1c2_title
}

/** Localized caption of the level card; expects the word count as first argument. */
fun CefrBand.descriptionRes(): StringResource = when (this) {
    CefrBand.A1_A2 -> Res.string.learning_level_a1a2_desc
    CefrBand.B1 -> Res.string.learning_level_b1_desc
    CefrBand.B2 -> Res.string.learning_level_b2_desc
    CefrBand.C1_C2 -> Res.string.learning_level_c1c2_desc
}

/** Glyph that identifies the level card. */
fun CefrBand.glyphRes(): DrawableResource = when (this) {
    CefrBand.A1_A2 -> Res.drawable.ic_lightning
    CefrBand.B1 -> Res.drawable.ic_layers
    CefrBand.B2 -> Res.drawable.ic_school
    CefrBand.C1_C2 -> Res.drawable.ic_gem
}

/** Soft clay container of the level card, resolved from the active theme. */
@Composable
fun CefrBand.containerColor(): Color = when (this) {
    CefrBand.A1_A2 -> ClayTheme.colors.secondaryContainer
    CefrBand.B1 -> ClayTheme.colors.tertiaryContainer
    CefrBand.B2 -> ClayTheme.colors.primaryContainer
    CefrBand.C1_C2 -> ClayTheme.colors.surfaceSunken
}

/** Readable foreground of the level card container. */
@Composable
fun CefrBand.contentColor(): Color = when (this) {
    CefrBand.A1_A2 -> ClayTheme.colors.onSecondaryContainer
    CefrBand.B1 -> ClayTheme.colors.onTertiaryContainer
    CefrBand.B2 -> ClayTheme.colors.onPrimaryContainer
    CefrBand.C1_C2 -> ClayTheme.colors.textPrimary
}

/** Formats a count with thin thousand separators (38000 -> 38,000). */
fun Int.asGroupedCount(): String {
    val raw = toString()
    val builder = StringBuilder()
    raw.forEachIndexed { index, char ->
        if (index > 0 && (raw.length - index) % 3 == 0) builder.append(',')
        builder.append(char)
    }
    return builder.toString()
}

/** Formats a rating with exactly one decimal (4.9f -> 4.9). */
fun Float.asRating(): String {
    val scaled = (this * 10f).roundToInt()
    return (scaled / 10).toString() + "." + (scaled % 10).toString()
}

/**
 * Applies the discovery filters of the hub (free-text search + category chip).
 */
fun LearningCatalog.filterPackages(
    query: String,
    category: PackageCategory?,
): List<LearningPackage> {
    val term = query.trim()
    return packages.filter { pkg ->
        val matchesCategory = category == null || pkg.category == category
        val matchesQuery = term.isEmpty() ||
            pkg.title.contains(term, ignoreCase = true) ||
            pkg.description.contains(term, ignoreCase = true) ||
            pkg.levelLabel.contains(term, ignoreCase = true) ||
            pkg.audienceLabel.contains(term, ignoreCase = true)
        matchesCategory && matchesQuery
    }
}
