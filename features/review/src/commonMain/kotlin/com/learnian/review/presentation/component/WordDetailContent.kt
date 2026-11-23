package com.learnian.review.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.learnian.core.ui.theme.Spacing
import com.learnian.review.domain.model.WordReviewData

/**
 * Full word-detail layout assembled from independent Claymorphic sections.
 *
 * Every section renders defensively: empty lists/blank strings simply omit
 * their block, so variable server payloads never break the layout.
 */
@Composable
fun WordDetailContent(
    wordData: WordReviewData,
    onToggleBookmark: () -> Unit,
    onPlayAudio: (String) -> Unit,
    modifier: Modifier = Modifier,
    bookmarkEnabled: Boolean = true,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        WordDetailHeader(
            wordData = wordData,
            onToggleBookmark = onToggleBookmark,
            onPlayAudio = onPlayAudio,
            bookmarkEnabled = bookmarkEnabled,
        )
        WordDetailMeaningSection(wordData = wordData)
        WordDetailMnemonicSection(wordData = wordData)
        WordDetailEtymologySection(wordData = wordData)
        WordDetailCollocationsSection(wordData = wordData)
        WordDetailRelationsSection(wordData = wordData)
        WordDetailExamplesSection(wordData = wordData)
    }
}
