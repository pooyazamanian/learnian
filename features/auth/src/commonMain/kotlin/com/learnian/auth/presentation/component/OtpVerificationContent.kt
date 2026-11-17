package com.learnian.auth.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.button.ClayButtonVariant
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.modifiers.clayInsetGrooved
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.auth_back
import com.learnian.core.view.resources.auth_btn_sending
import com.learnian.core.view.resources.auth_btn_verify
import com.learnian.core.view.resources.auth_otp_change_phone
import com.learnian.core.view.resources.auth_otp_resend_action
import com.learnian.core.view.resources.auth_otp_resend_countdown
import com.learnian.core.view.resources.auth_otp_subtitle_format
import com.learnian.core.view.resources.auth_otp_title
import com.learnian.core.view.resources.ic_chevron_right
import com.learnian.core.view.resources.ic_edit_pen
import org.jetbrains.compose.resources.stringResource

/**
 * Step 2: OTP Verification Screen with 5-digit Claymorphic tactile cells,
 * resend timer, and phone number editing affordance.
 */
@Composable
fun OtpVerificationContent(
    phoneNumber: String,
    otpCode: String,
    resendSecondsRemaining: Int,
    canResendOtp: Boolean,
    isLoading: Boolean,
    errorMessage: String?,
    onOtpChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onResendOtp: () -> Unit,
    onChangePhoneClick: () -> Unit,
    onBackClick: () -> Unit,
    errorText: com.learnian.base.UiText? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        // 1. Navigation Back Row & Phone Number Badge
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier
                    .clayPressable(onClick = onBackClick)
                    .padding(Spacing.ExtraSmall),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall),
            ) {
                BaseIcon(
                    resource = Res.drawable.ic_chevron_right,
                    contentDescription = stringResource(Res.string.auth_back),
                    tint = ClayTheme.colors.primary,
                    modifier = Modifier.size(24.dp),
                )
                Text(
                    text = stringResource(Res.string.auth_back),
                    style = AppTypography.labelLarge,
                    color = ClayTheme.colors.primary,
                )
            }

            // Phone number badge with edit pencil button
            Row(
                modifier = Modifier
                    .clayPressable(onClick = onChangePhoneClick)
                    .padding(vertical = Spacing.ExtraSmall, horizontal = Spacing.Small),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall),
            ) {
                Text(
                    text = phoneNumber,
                    style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = ClayTheme.colors.textPrimary,
                )
                BaseIcon(
                    resource = Res.drawable.ic_edit_pen,
                    contentDescription = stringResource(Res.string.auth_otp_change_phone),
                    tint = ClayTheme.colors.primary,
                    modifier = Modifier.size(16.dp),
                )
            }
        }

        // 2. Branding Mascot & Header
        AuthHeaderSection(
            title = stringResource(Res.string.auth_otp_title),
            subtitle = stringResource(Res.string.auth_otp_subtitle_format, phoneNumber),
        )

        Spacer(modifier = Modifier.height(Spacing.Small))

        // 3. 5-Digit Claymorphic Inset Cells (Strictly LTR)
        com.learnian.core.ui.component.textfield.ClayOtpInputField(
            value = otpCode,
            onValueChange = onOtpChange,
            length = 5,
            enabled = !isLoading,
            onComplete = onSubmit,
        )

        val displayError = errorText?.asString() ?: errorMessage
        if (displayError != null) {
            Text(
                text = displayError,
                style = AppTypography.bodySmall,
                color = ClayTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = Spacing.ExtraSmall),
            )
        }

        Spacer(modifier = Modifier.height(Spacing.Small))

        // 4. Countdown Timer / Resend Button
        if (canResendOtp) {
            ClayStatusChip(
                text = stringResource(Res.string.auth_otp_resend_action),
                style = ClayChipStyle.Resting,
                textColor = ClayTheme.colors.primary,
                backgroundColor = ClayTheme.colors.primaryContainer,
                onClick = onResendOtp,
            )
        } else {
            val formattedSeconds = resendSecondsRemaining.toString().padStart(2, '0')
            Text(
                text = stringResource(Res.string.auth_otp_resend_countdown, formattedSeconds),
                style = AppTypography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                ),
                color = ClayTheme.colors.textSecondary,
                textAlign = TextAlign.Center,
            )
        }

        // 5. Submit Action Button
        ClayButton(
            onClick = onSubmit,
            enabled = otpCode.length == 5 && !isLoading,
            variant = ClayButtonVariant.Primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
        ) {
            Text(
                text = stringResource(if (isLoading) Res.string.auth_btn_sending else Res.string.auth_btn_verify),
                style = AppTypography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                ),
            )
        }
    }
}
