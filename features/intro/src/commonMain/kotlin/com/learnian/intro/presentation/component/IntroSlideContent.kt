package com.learnian.intro.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.intro.domain.model.IntroSlide

/**
 * Slide content displaying the hero visual card, bold purple headline, description,
 * and 3 feature cards matching the design specification.
 */
@Composable
fun IntroSlideContent(
    slide: IntroSlide,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.Small),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        // 1. Hero Visual Card
        IntroHeroCard(
            tag = slide.tag,
            imageRes = slide.heroImageRes,
        )

        // 2. Headline & Subtitle Section
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = slide.title.asString(),
                style = AppTypography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                ),
                color = ClayTheme.colors.primary,
                textAlign = TextAlign.Center,
            )
            Text(
                text = slide.subtitle.asString(),
                style = AppTypography.bodyMedium.copy(
                    lineHeight = 22.sp,
                    fontSize = 13.5.sp,
                ),
                color = ClayTheme.colors.textSecondary,
                textAlign = TextAlign.Center,
            )
        }

        // 3. Feature Rows
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            slide.features.forEach { feature ->
                IntroFeatureRow(item = feature)
            }
        }
    }
}
