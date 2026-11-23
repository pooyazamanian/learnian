package com.learnian.review.presentation.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme

/**
 * Renders a sentence and highlights every occurrence of [keyword]
 * (case-insensitive) with the active theme accent color.
 */
@Composable
fun HighlightedSentence(
    sentence: String,
    keyword: String,
    modifier: Modifier = Modifier,
    style: TextStyle = AppTypography.bodyMedium,
    color: Color = ClayTheme.colors.textPrimary,
    highlightColor: Color = ClayTheme.colors.primary,
) {
    val annotated = remember(sentence, keyword, highlightColor) {
        buildHighlightedText(
            sentence = sentence,
            keyword = keyword,
            highlightColor = highlightColor,
        )
    }

    Text(
        text = annotated,
        modifier = modifier,
        style = style,
        color = color,
    )
}

private fun buildHighlightedText(
    sentence: String,
    keyword: String,
    highlightColor: Color,
): AnnotatedString = buildAnnotatedString {
    val lowerSentence = sentence.lowercase()
    val lowerKeyword = keyword.lowercase()

    if (lowerKeyword.isBlank() || sentence.isBlank()) {
        append(sentence)
        return@buildAnnotatedString
    }

    var cursor = 0
    while (cursor < sentence.length) {
        val matchIndex = lowerSentence.indexOf(lowerKeyword, startIndex = cursor)
        if (matchIndex < 0) {
            append(sentence.substring(cursor))
            break
        }

        if (matchIndex > cursor) {
            append(sentence.substring(cursor, matchIndex))
        }

        val matchEnd = (matchIndex + keyword.length).coerceAtMost(sentence.length)
        withStyle(
            SpanStyle(
                fontWeight = FontWeight.ExtraBold,
                color = highlightColor,
            ),
        ) {
            append(sentence.substring(matchIndex, matchEnd))
        }

        cursor = matchEnd
    }
}
