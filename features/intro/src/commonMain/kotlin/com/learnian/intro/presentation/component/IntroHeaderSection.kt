package com.learnian.intro.presentation.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.intro_btn_skip
import org.jetbrains.compose.resources.stringResource

/**
 * Top header of the Intro screen:
 * - Pager indicators (3 animated tactile dots/pills)
 * - Skip ("رد شدن") tactile pill button
 */
@Composable
fun IntroHeaderSection(
    currentPage: Int,
    totalPages: Int,
    onSkipClick: () -> Unit,
    onDotClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.Small, vertical = Spacing.ExtraSmall),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Skip Button (tactile pill with soft shadow)
        Box(
            modifier = Modifier
                .clayPressable(onClick = onSkipClick)
                .clayRestingFloat(
                    cornerRadius = AppCorners.Pill,
                    backgroundColor = ClayTheme.colors.surface,
                )
                .clip(RoundedCornerShape(AppCorners.Pill))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(Res.string.intro_btn_skip),
                style = AppTypography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                ),
                color = ClayTheme.colors.textSecondary,
            )
        }

        // Pager Indicator Dots (animated width + smooth color transition)
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            for (i in 0 until totalPages) {
                val isSelected = i == currentPage
                val dotWidth by animateDpAsState(
                    targetValue = if (isSelected) 24.dp else 8.dp,
                    animationSpec = tween(durationMillis = 300),
                    label = "DotWidth_$i",
                )

                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(dotWidth)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) ClayTheme.colors.primary
                            else ClayTheme.colors.surfaceSunken
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onDotClick(i) },
                        )
                )
            }
        }
    }
}
