package com.learnian.auth.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.owl

/**
 * 3D Claymorphic Mascot Header with the Learnyan Owl mascot,
 * title, and subtitle.
 */
@Composable
fun AuthHeaderSection(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.Small),
    ) {
        // Tactile 3D Circular Mascot Container
        Box(
            modifier = Modifier
                .size(92.dp)
                .clayRestingFloat(
                    backgroundColor = ClayTheme.colors.primaryContainer,
                    cornerRadius = AppCorners.Pill,
                    shadowColor = ClayTheme.colors.shadowOuter,
                )
                .padding(Spacing.Small),
            contentAlignment = Alignment.Center,
        ) {
            BaseImage(
                resource = Res.drawable.owl,
                contentDescription = "Learnyan Mascot",
                modifier = Modifier.size(72.dp),
            )
        }

        Text(
            text = title,
            style = AppTypography.headlineMedium.copy(
                fontWeight = FontWeight.Black,
                fontSize = 22.sp,
            ),
            color = ClayTheme.colors.textPrimary,
            textAlign = TextAlign.Center,
        )

        Text(
            text = subtitle,
            style = AppTypography.bodyMedium.copy(
                fontSize = 13.sp,
            ),
            color = ClayTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}
