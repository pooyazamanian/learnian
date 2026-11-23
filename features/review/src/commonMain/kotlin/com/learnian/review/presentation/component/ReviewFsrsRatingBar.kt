package com.learnian.review.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.modifiers.clayActionRaised
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.review_rate_again
import com.learnian.core.view.resources.review_rate_easy
import com.learnian.core.view.resources.review_rate_good
import com.learnian.core.view.resources.review_rate_hard
import com.learnian.review.domain.model.FsrsIntervals
import com.learnian.review.domain.model.FsrsRating
import org.jetbrains.compose.resources.stringResource

/**
 * 4-tier FSRS grading bar (Again / Hard / Good / Easy).
 *
 * Each tier shows the next scheduled interval returned by the scheduler payload,
 * so the user understands the consequence of the grade before tapping.
 */
@Composable
fun ReviewFsrsRatingBar(
    intervals: FsrsIntervals,
    onRateClick: (FsrsRating) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FsrsRatingButton(
            title = stringResource(Res.string.review_rate_again),
            interval = intervals.againText,
            backgroundColor = ClayTheme.colors.errorContainer,
            titleColor = ClayTheme.colors.onErrorContainer,
            enabled = enabled,
            onClick = { onRateClick(FsrsRating.AGAIN) },
            modifier = Modifier.weight(1f),
        )

        FsrsRatingButton(
            title = stringResource(Res.string.review_rate_hard),
            interval = intervals.hardText,
            backgroundColor = ClayTheme.colors.tertiaryContainer,
            titleColor = ClayTheme.colors.onTertiaryContainer,
            enabled = enabled,
            onClick = { onRateClick(FsrsRating.HARD) },
            modifier = Modifier.weight(1f),
        )

        FsrsRatingButton(
            title = stringResource(Res.string.review_rate_good),
            interval = intervals.goodText,
            backgroundColor = ClayTheme.colors.secondaryContainer,
            titleColor = ClayTheme.colors.onSecondaryContainer,
            enabled = enabled,
            onClick = { onRateClick(FsrsRating.GOOD) },
            modifier = Modifier.weight(1f),
        )

        FsrsRatingButton(
            title = stringResource(Res.string.review_rate_easy),
            interval = intervals.easyText,
            backgroundColor = ClayTheme.colors.primaryContainer,
            titleColor = ClayTheme.colors.primary,
            enabled = enabled,
            onClick = { onRateClick(FsrsRating.EASY) },
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun FsrsRatingButton(
    title: String,
    interval: String,
    backgroundColor: Color,
    titleColor: Color,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .alpha(if (enabled) 1f else 0.55f)
            .clayPressable(enabled = enabled, onClick = onClick)
            .clayActionRaised(
                backgroundColor = backgroundColor,
                cornerRadius = AppCorners.Large,
            )
            .padding(vertical = 10.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title,
            style = AppTypography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                fontSize = 14.sp,
            ),
            color = titleColor,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = interval,
            style = AppTypography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                lineHeight = 13.sp,
            ),
            color = titleColor.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            maxLines = 2,
        )
    }
}
