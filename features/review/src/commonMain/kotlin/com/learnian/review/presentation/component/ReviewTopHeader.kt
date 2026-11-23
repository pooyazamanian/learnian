package com.learnian.review.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.progress.ClayProgressBar
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_arrow_left
import com.learnian.core.view.resources.ic_streak_flame
import com.learnian.core.view.resources.word_detail_back
import com.learnian.core.view.resources.review_level_lesson_format
import com.learnian.core.view.resources.review_progress_format
import com.learnian.core.view.resources.review_progress_streak
import com.learnian.core.view.resources.review_title
import com.learnian.review.domain.model.ReviewProgressInfo
import org.jetbrains.compose.resources.stringResource

/**
 * Review header: back navigation, lesson context, streak and progress of the
 * current review session.
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
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ClayIconButton(
                onClick = onBackClick,
                size = 40.dp,
                cornerRadius = AppCorners.Pill,
                backgroundColor = ClayTheme.colors.surface,
            ) {
                BaseIcon(
                    resource = Res.drawable.ic_arrow_left,
                    contentDescription = stringResource(Res.string.word_detail_back),
                    tint = ClayTheme.colors.textPrimary,
                    modifier = Modifier.size(20.dp),
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.Small),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(Res.string.review_title),
                    style = AppTypography.headlineSmall.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                    ),
                    color = ClayTheme.colors.textPrimary,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = stringResource(
                        Res.string.review_level_lesson_format,
                        level,
                        lesson,
                    ),
                    style = AppTypography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                    ),
                    color = ClayTheme.colors.textSecondary,
                    textAlign = TextAlign.Center,
                )
            }

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
                        tint = ClayTheme.colors.onTertiaryContainer,
                        modifier = Modifier.size(14.dp),
                    )
                },
            )
        }

        Spacer(modifier = Modifier.height(Spacing.Medium))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.Small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
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
        }

        Spacer(modifier = Modifier.height(Spacing.Small))

        val progressFraction = if (progressInfo.totalCount > 0) {
            progressInfo.currentIndex.toFloat() / progressInfo.totalCount.toFloat()
        } else {
            0f
        }

        ClayProgressBar(
            progress = progressFraction,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.Small),
            height = 10.dp,
            progressColor = ClayTheme.colors.primary,
            trackColor = ClayTheme.colors.surfaceSunken,
        )
    }
}
