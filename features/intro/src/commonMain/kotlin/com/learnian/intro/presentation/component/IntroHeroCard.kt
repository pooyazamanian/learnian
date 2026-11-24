package com.learnian.intro.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.base.UiText
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.modifiers.clayPillRaised
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import org.jetbrains.compose.resources.DrawableResource

/**
 * Visual hero card for the Intro slides with soft clay volume and bottom badge chip.
 */
@Composable
fun IntroHeroCard(
    tag: UiText,
    imageRes: DrawableResource?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .clayRestingFloat(
                backgroundColor = ClayTheme.colors.surface,
                cornerRadius = AppCorners.XXL,
            )
            .clip(RoundedCornerShape(AppCorners.XXL))
            .background(
                Brush.verticalGradient(
                    listOf(
                        ClayTheme.colors.surface.copy(alpha = 0.95f),
                        ClayTheme.colors.surfaceSunken.copy(alpha = 0.45f),
                    )
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        // Hero visual / illustration
        if (imageRes != null) {
            BaseImage(
                resource = imageRes,
                contentDescription = null,
                modifier = Modifier
                    .size(190.dp)
                    .padding(bottom = 24.dp),
            )
        }

        // Bottom badge pill ("✨ یادگیری شاداب و ماندگار")
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 18.dp)
                .clayPillRaised(
                    cornerRadius = AppCorners.Pill,
                    backgroundColor = Color(0xFFC7F2E2),
                )
                .clip(RoundedCornerShape(AppCorners.Pill))
                .padding(horizontal = 14.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = tag.asString(),
                style = AppTypography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                ),
                color = Color(0xFF005244),
            )
        }
    }
}
