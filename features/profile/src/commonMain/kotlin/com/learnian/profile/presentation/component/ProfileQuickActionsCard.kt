package com.learnian.profile.presentation.component

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_chevron_left
import com.learnian.core.view.resources.ic_chevron_right
import com.learnian.core.view.resources.ic_cloud_sync
import com.learnian.core.view.resources.ic_headset_support
import com.learnian.core.view.resources.ic_update_check
import com.learnian.core.view.resources.profile_check_update_status
import com.learnian.core.view.resources.profile_check_update_title
import com.learnian.core.view.resources.profile_cloud_sync_status
import com.learnian.core.view.resources.profile_cloud_sync_title
import com.learnian.core.view.resources.profile_support_title
import org.jetbrains.compose.resources.stringResource

/**
 * Quick Actions & Services Card inside the Profile section.
 *
 * Displays:
 * 1. Cloud Vocabulary Sync with status (e.g. "Just now" / "لحظاتی پیش").
 * 2. Voice Guide & Support.
 */
@Composable
fun ProfileQuickActionsCard(
    modifier: Modifier = Modifier,
    onCloudSyncClick: () -> Unit = {},
    onSupportClick: () -> Unit = {},
    onCheckUpdateClick: () -> Unit = {},
) {
    val dividerColor = ClayTheme.colors.surfaceSunken.copy(alpha = 0.5f)

    ClayCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = AppCorners.XL,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            // ---------------- 1. Cloud Vocabulary Sync ----------------
            ProfileQuickActionItem(
                title = stringResource(Res.string.profile_cloud_sync_title),
                status = stringResource(Res.string.profile_cloud_sync_status),
                iconResource = Res.drawable.ic_cloud_sync,
                iconTint = ClayTheme.colors.primary,
                badgeBackground = ClayTheme.colors.primaryContainer,
                onClick = onCloudSyncClick,
            )

            HorizontalDivider(
                color = dividerColor,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 4.dp),
            )

            // ---------------- 2. Voice Guide & Support ----------------
            ProfileQuickActionItem(
                title = stringResource(Res.string.profile_support_title),
                status = null,
                iconResource = Res.drawable.ic_headset_support,
                iconTint = ClayTheme.colors.secondary,
                badgeBackground = ClayTheme.colors.secondaryContainer,
                onClick = onSupportClick,
            )

            HorizontalDivider(
                color = dividerColor,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 4.dp),
            )

            // ---------------- 3. Check for Updates ----------------
            ProfileQuickActionItem(
                title = stringResource(Res.string.profile_check_update_title),
                status = stringResource(Res.string.profile_check_update_status),
                iconResource = Res.drawable.ic_update_check,
                iconTint = ClayTheme.colors.tertiary,
                badgeBackground = ClayTheme.colors.tertiaryContainer,
                onClick = onCheckUpdateClick,
            )
        }
    }
}

@Composable
private fun ProfileQuickActionItem(
    title: String,
    status: String?,
    iconResource: org.jetbrains.compose.resources.DrawableResource,
    iconTint: Color,
    badgeBackground: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(AppCorners.Large))
            .clayPressable(pressedScale = 0.98f, onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f),
        ) {
            // Rounded 3D Tinted Icon Badge
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(color = badgeBackground, shape = CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                BaseIcon(
                    resource = iconResource,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp),
                )
            }

            // Title and Optional Status
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = title,
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    ),
                    color = ClayTheme.colors.textPrimary,
                )

                if (!status.isNullOrBlank()) {
                    Text(
                        text = status,
                        style = AppTypography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                        ),
                        color = ClayTheme.colors.textSecondary,
                    )
                }
            }
        }

        val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

        // Forward Navigation Chevron (points left in RTL, points right in LTR)
        BaseIcon(
            resource = if (isRtl) Res.drawable.ic_chevron_left else Res.drawable.ic_chevron_right,
            contentDescription = null,
            tint = ClayTheme.colors.textSecondary.copy(alpha = 0.7f),
            modifier = Modifier.size(18.dp),
        )
    }
}
