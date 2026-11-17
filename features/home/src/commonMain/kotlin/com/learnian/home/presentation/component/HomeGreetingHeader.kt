package com.learnian.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.modifiers.clayActionRaised
import com.learnian.core.ui.modifiers.clayPillRaised
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.fire
import com.learnian.core.view.resources.home_greeting_format
import com.learnian.core.view.resources.home_streak_continuous
import com.learnian.core.view.resources.home_user_level_default
import com.learnian.core.view.resources.streak_days_format
import org.jetbrains.compose.resources.stringResource

/**
 * Top User Greeting Header (directly on canvas, not inside a card).
 *
 * Displays:
 * - Start (Right in RTL): User Avatar with initials and active green status dot,
 *   greeting "سلام {userName}! 👋", and vocab/proficiency level.
 * - End (Left in RTL): Continuous streak chip with fire icon, streak count, and subtitle.
 */
@Composable
fun HomeGreetingHeader(
    userName: String,
    streakDays: Int,
    modifier: Modifier = Modifier,
    userLevel: String = stringResource(Res.string.home_user_level_default),
    onStreakClick: (() -> Unit)? = null,
    onAvatarClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        // 1. User Avatar + Greeting & Level Info (Start side)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Circular Avatar with Green Status Indicator
            Box(
                modifier = Modifier.size(54.dp),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .then(if (onAvatarClick != null) Modifier.clayPressable(onClick = onAvatarClick) else Modifier)
                        .clayActionRaised(
                            backgroundColor = ClayTheme.colors.primary,
                            cornerRadius = AppCorners.Pill,
                            shadowColor = ClayTheme.colors.shadowOuter,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    val initialLetter = userName.trim().firstOrNull()?.toString() ?: "ش"
                    Text(
                        text = initialLetter,
                        style = AppTypography.headlineMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                        ),
                        color = Color.White,
                    )
                }

                // Active Green Online Status Dot
                Box(
                    modifier = Modifier
                        .size(13.dp)
                        .align(Alignment.BottomEnd)
                        .background(Color(0xFF34C759), CircleShape)
                        .border(2.dp, ClayTheme.colors.background, CircleShape),
                )
            }

            // User Greeting & Subtitle
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = stringResource(Res.string.home_greeting_format, userName),
                    style = AppTypography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                    ),
                    color = ClayTheme.colors.textPrimary,
                )

                Text(
                    text = userLevel,
                    style = AppTypography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    ),
                    color = ClayTheme.colors.primary,
                )
            }
        }

        // 2. Streak Badge Chip (End side)
        Box(
            modifier = Modifier
                .then(if (onStreakClick != null) Modifier.clayPressable(onClick = onStreakClick) else Modifier)
                .clayPillRaised(
                    backgroundColor = ClayTheme.colors.tertiaryContainer,
                    cornerRadius = AppCorners.Medium,
                    borderColor = ClayTheme.colors.tertiary.copy(alpha = 0.25f),
                    borderWidth = 1.dp,
                    shadowColor = ClayTheme.colors.shadowOuter,
                )
                .padding(horizontal = AppPaddings.BadgePaddingHorizontal, vertical = AppPaddings.BadgePaddingVertical),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Streak Text Column
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = stringResource(Res.string.streak_days_format, streakDays.toString()),
                        style = AppTypography.titleSmall.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                        ),
                        color = Color(0xFFD97706),
                    )

                    Text(
                        text = stringResource(Res.string.home_streak_continuous),
                        style = AppTypography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 10.sp,
                        ),
                        color = Color(0xFFB45309),
                    )
                }

                // Fire Flame Icon
                BaseImage(
                    resource = Res.drawable.fire,
                    contentDescription = "استریک",
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}
