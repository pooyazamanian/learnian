package com.learnian.profile.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.system.app.AppVersionInfo
import com.learnian.core.system.app.rememberAppVersionInfo
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.modifiers.clayActionRaised
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_logout
import com.learnian.core.view.resources.profile_app_version_format
import com.learnian.core.view.resources.profile_logout_btn
import org.jetbrains.compose.resources.stringResource

/**
 * Footer Section for the Profile Screen.
 *
 * Contains:
 * 1. 3D Claymorphic Logout Pill Button with soft peach background and crimson accent.
 * 2. Real dynamic App Version and Build Number retrieved from host platform.
 */
@Composable
fun ProfileFooterSection(
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    appVersionInfo: AppVersionInfo = rememberAppVersionInfo(),
) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    val buttonBgColor = ClayTheme.colors.errorContainer
    val buttonContentColor = ClayTheme.colors.onErrorContainer
    val buttonShadowColor = ClayTheme.colors.shadowOuter

    val localizedVersion = if (isRtl) formatDigits(appVersionInfo.versionName) else appVersionInfo.versionName
    val localizedBuild = if (isRtl) formatDigits(appVersionInfo.versionCode.toString()) else appVersionInfo.versionCode.toString()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // 1. Soft Peach 3D Claymorphic Logout Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clayPressable(pressedScale = 0.97f, onClick = onLogoutClick)
                .clayActionRaised(
                    backgroundColor = buttonBgColor,
                    cornerRadius = AppCorners.Pill,
                    shadowColor = buttonShadowColor,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = stringResource(Res.string.profile_logout_btn),
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 15.sp,
                    ),
                    color = buttonContentColor,
                )

                BaseIcon(
                    resource = Res.drawable.ic_logout,
                    contentDescription = null,
                    tint = buttonContentColor,
                    modifier = Modifier.size(20.dp),
                )
            }
        }

        // 2. App Version & Build Footer
        Text(
            text = stringResource(Res.string.profile_app_version_format, localizedVersion, localizedBuild),
            style = AppTypography.bodySmall.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
            ),
            color = ClayTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp),
        )
    }
}

private fun formatDigits(text: String): String {
    return text.map { ch ->
        when (ch) {
            '0' -> '۰'
            '1' -> '۱'
            '2' -> '۲'
            '3' -> '۳'
            '4' -> '۴'
            '5' -> '۵'
            '6' -> '۶'
            '7' -> '۷'
            '8' -> '۸'
            '9' -> '۹'
            else -> ch
        }
    }.joinToString("")
}
