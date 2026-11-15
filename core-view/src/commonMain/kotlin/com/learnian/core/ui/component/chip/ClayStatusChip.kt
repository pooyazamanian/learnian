package com.learnian.core.ui.component.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme

/**
 * Claymorphism Status Pill Chip (clay-pill-chip)
 * Used in TopBar and across screens for Gamification Badges (Streak flame, Gems, XP).
 */
@Composable
fun ClayStatusChip(
    text: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = ClayTheme.colors.textPrimary,
    backgroundColor: Color = ClayTheme.colors.surface,
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .then(
                if (onClick != null) Modifier.clayPressable(onClick = onClick)
                else Modifier
            )
            .clayRestingFloat(
                backgroundColor = backgroundColor,
                cornerRadius = AppCorners.Pill
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = text,
                style = AppTypography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp
                ),
                color = textColor
            )
            icon()
        }
    }
}
