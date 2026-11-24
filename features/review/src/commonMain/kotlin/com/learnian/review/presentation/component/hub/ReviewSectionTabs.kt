package com.learnian.review.presentation.component.hub

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.component.segmented.ClaySegment
import com.learnian.core.ui.component.segmented.ClaySegmentedSlider
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.review.presentation.mvi.ReviewSection

/**
 * The three sub-pages of the review tab: notebook, dictionary and saved words.
 *
 * A sunken clay groove holds a sliding raised thumb; each segment keeps its clay
 * illustration so the section is recognisable at a glance.
 */
@Composable
fun ReviewSectionTabs(
    selectedSection: ReviewSection,
    onSectionSelected: (ReviewSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    ClaySegmentedSlider(
        segments = ReviewSection.entries.map { section ->
            ClaySegment(
                value = section,
                customContent = { isSelected ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Spacing.Compact),
                    ) {
                        BaseImage(
                            resource = section.illustration(),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                        )
                        Text(
                            text = section.shortTitle(),
                            style = AppTypography.bodySmall.copy(
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                                fontSize = 12.sp,
                            ),
                            color = if (isSelected) {
                                ClayTheme.colors.onPrimary
                            } else {
                                ClayTheme.colors.textPrimary
                            },
                            maxLines = 1,
                        )
                    }
                },
            )
        },
        selectedValue = selectedSection,
        onValueChange = onSectionSelected,
        modifier = modifier.fillMaxWidth(),
        height = 58.dp,
        thumbColor = ClayTheme.colors.primary,
    )
}
