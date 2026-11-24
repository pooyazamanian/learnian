package com.learnian.intro.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.intro.domain.model.IntroBadgeVariant
import com.learnian.intro.domain.model.IntroFeatureItem

/**
 * Feature card row displaying an icon badge and title/subtitle.
 */
@Composable
fun IntroFeatureRow(
    item: IntroFeatureItem,
    modifier: Modifier = Modifier,
) {
    val (badgeBg, iconTint) = when (item.badgeVariant) {
        IntroBadgeVariant.Mint -> Color(0xFFE2F7EE) to Color(0xFF00755C)
        IntroBadgeVariant.Amber -> Color(0xFFFFF2DC) to Color(0xFFC07700)
        IntroBadgeVariant.Lavender -> Color(0xFFEDE9FC) to Color(0xFF5949C0)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clayRestingFloat(
                backgroundColor = ClayTheme.colors.surface,
                cornerRadius = AppCorners.Large,
            )
            .clip(RoundedCornerShape(AppCorners.Large))
            .padding(horizontal = Spacing.Medium, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        // Feature Text Column
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = item.title.asString(),
                style = AppTypography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                ),
                color = ClayTheme.colors.textPrimary,
            )
            Text(
                text = item.description.asString(),
                style = AppTypography.bodySmall.copy(
                    fontSize = 12.sp,
                ),
                color = ClayTheme.colors.textSecondary,
            )
        }

        // Circular Icon Badge
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(badgeBg),
            contentAlignment = Alignment.Center,
        ) {
            BaseIcon(
                resource = item.iconRes,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(22.dp),
            )
        }
    }
}
