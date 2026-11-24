package com.learnian.intro.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.button.ClayButtonVariant
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_arrow_left
import com.learnian.core.view.resources.intro_btn_already_account
import com.learnian.core.view.resources.intro_btn_next
import com.learnian.core.view.resources.intro_btn_start
import org.jetbrains.compose.resources.stringResource

/**
 * Bottom actions of the Intro screen:
 * - Primary Action Button ("🚀 شروع یادگیری با لرنیان" on last slide, or "ادامه")
 * - Secondary Action Link ("قبلاً عضو شده‌ام / ورود به حساب کاربری")
 */
@Composable
fun IntroBottomActions(
    isLastSlide: Boolean,
    onPrimaryClick: () -> Unit,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.Small, vertical = Spacing.ExtraSmall),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.Small),
    ) {
        // 1. Primary ClayButton
        ClayButton(
            text = stringResource(if (isLastSlide) Res.string.intro_btn_start else Res.string.intro_btn_next),
            onClick = onPrimaryClick,
            variant = ClayButtonVariant.Primary,
            cornerRadius = AppCorners.Pill,
            leadingIcon = {
                BaseIcon(
                    resource = Res.drawable.ic_arrow_left,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
        )

        // 2. Secondary Link Button
        Text(
            text = stringResource(Res.string.intro_btn_already_account),
            style = AppTypography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
            ),
            color = ClayTheme.colors.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clayPressable(onClick = onSignInClick)
                .padding(vertical = Spacing.ExtraSmall, horizontal = Spacing.Small),
        )
    }
}
