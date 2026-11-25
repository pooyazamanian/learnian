package com.learnian.review.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.header.ClayScreenHeader
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.progress.ClayProgressBar
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_streak_flame
import com.learnian.core.view.resources.review_level_lesson_format
import com.learnian.core.view.resources.review_progress_format
import com.learnian.core.view.resources.review_progress_streak
import com.learnian.core.view.resources.review_title
import com.learnian.review.domain.model.ReviewProgressInfo
import org.jetbrains.compose.resources.stringResource

/**
 * Review header: the shared [ClayScreenHeader] plus the session-specific progress row
 * (position chip and the progress bar of the current review).
 */
@Composable
fun ReviewTopHeader(
    progressInfo: ReviewProgressInfo,
    level: String,
    lesson: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
    ) {
        ClayScreenHeader(
            title = stringResource(Res.string.review_title),
            subtitle = stringResource(Res.string.review_level_lesson_format, level, lesson),
            onBackClick = onBackClick,
            trailing = {
                ClayStatusChip(
                    text = stringResource(
                        Res.string.review_progress_streak,
                        progressInfo.streakDays.toString(),
                    ),
                    style = ClayChipStyle.Resting,
                    backgroundColor = ClayTheme.colors.tertiaryContainer,
                    textColor = ClayTheme.colors.onTertiaryContainer,
                    textStyle = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    icon = {
                        BaseIcon(
                            resource = Res.drawable.ic_streak_flame,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = ClayTheme.colors.onTertiaryContainer,
                        )
                    },
                )
            },
        )

        ClayStatusChip(
            text = stringResource(
                Res.string.review_progress_format,
                progressInfo.currentIndex.toString(),
                progressInfo.totalCount.toString(),
            ),
            style = ClayChipStyle.Resting,
            backgroundColor = ClayTheme.colors.primaryContainer,
            textColor = ClayTheme.colors.primary,
            textStyle = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
        )

        ClayProgressBar(
            progress = if (progressInfo.totalCount > 0) {
                progressInfo.currentIndex.toFloat() / progressInfo.totalCount.toFloat()
            } else {
                0f
            },
            modifier = Modifier.fillMaxWidth(),
            height = 10.dp,
            progressColor = ClayTheme.colors.primary,
            trackColor = ClayTheme.colors.surfaceSunken,
        )
    }
}