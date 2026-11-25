package com.learnian.core.ui.component.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayIconButton
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.common_back
import com.learnian.core.view.resources.ic_arrow_left
import org.jetbrains.compose.resources.stringResource

/**
 * Header of a route that is pushed above the tab scaffold (a page without the global
 * bottom bar): clay back button, title, optional caption and an optional trailing slot.
 *
 * This is the only back affordance of the app: feature screens must not hand-build their
 * own back row, so every pushed page shares one button size, corner and title rhythm.
 * The tab scaffold keeps owning the global top bar for root tabs.
 */
@Composable
fun ClayScreenHeader(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ClayIconButton(
            onClick = onBackClick,
            size = 42.dp,
            cornerRadius = AppCorners.Medium,
            backgroundColor = ClayTheme.colors.surface,
        ) {
            BaseIcon(
                resource = Res.drawable.ic_arrow_left,
                contentDescription = stringResource(Res.string.common_back),
                modifier = Modifier.size(20.dp),
                tint = ClayTheme.colors.textPrimary,
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
        ) {
            Text(
                text = title,
                style = AppTypography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp,
                ),
                color = ClayTheme.colors.textPrimary,
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = AppTypography.bodySmall.copy(fontSize = 11.sp, lineHeight = 16.sp),
                    color = ClayTheme.colors.textSecondary,
                )
            }
        }
        trailing?.invoke()
    }
}