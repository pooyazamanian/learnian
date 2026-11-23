package com.learnian.review.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_brain_ai
import com.learnian.core.view.resources.owl
import com.learnian.review.domain.model.ReviewProgressInfo

/**
 * Learnyan owl coach bubble shown above the flashcard with a contextual tip
 * coming from the review payload.
 */
@Composable
fun ReviewCoachBanner(
    coachTip: String,
    modifier: Modifier = Modifier,
) {
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = ClayTheme.colors.primaryContainer,
        cornerRadius = AppCorners.Large,
        padding = Spacing.Small,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(ClayTheme.colors.surface),
                contentAlignment = Alignment.Center,
            ) {
                BaseImage(
                    resource = Res.drawable.owl,
                    contentDescription = null,
                    modifier = Modifier.size(34.dp),
                )
            }

            Spacer(modifier = Modifier.width(Spacing.Small))

            Text(
                text = coachTip,
                style = AppTypography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                ),
                color = ClayTheme.colors.onPrimaryContainer,
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(Spacing.Small))

            BaseIcon(
                resource = Res.drawable.ic_brain_ai,
                contentDescription = null,
                tint = ClayTheme.colors.primary,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}

/** Convenience overload for screens that only have the progress payload. */
@Composable
fun ReviewCoachBanner(
    progressInfo: ReviewProgressInfo,
    modifier: Modifier = Modifier,
) {
    ReviewCoachBanner(coachTip = progressInfo.coachTip, modifier = modifier)
}
