package com.learnian.auth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.base.UiText
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.button.ClayButtonVariant
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.textfield.ClayTextField
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.auth_btn_send_otp
import com.learnian.core.view.resources.auth_btn_sending
import com.learnian.core.view.resources.auth_divider_quick_login
import com.learnian.core.view.resources.auth_guest_login
import com.learnian.core.view.resources.auth_phone_label
import com.learnian.core.view.resources.auth_phone_placeholder
import com.learnian.core.view.resources.auth_phone_subtitle
import com.learnian.core.view.resources.auth_phone_title
import com.learnian.core.view.resources.auth_sso_google
import com.learnian.core.view.resources.ic_arrow_left
import com.learnian.core.view.resources.ic_chevron_left
import com.learnian.core.view.resources.ic_google_logo
import com.learnian.core.view.resources.ic_phone_outline
import org.jetbrains.compose.resources.stringResource

/**
 * Step 1: Phone number entry reusing generalized core-view components:
 * - [AuthHeaderSection]: Owl mascot branding header with localized title & subtitle.
 * - [ClayTextField]: Generalized 3D sunken trench input configured with a country flag badge (+98 🇮🇷)
 *   in leadingContent and phone outline icon in trailingContent.
 * - [ClayButton]: Primary action button with arrow icon.
 * - [ClayButton]: Surface variant 3D raised white pill for Google SSO.
 */
@Composable
fun PhoneInputContent(
    phoneNumber: String,
    isLoading: Boolean,
    errorText: UiText?,
    onPhoneNumberChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onGoogleSsoClick: () -> Unit,
    onGuestLoginClick: (() -> Unit)? = null,
    onSwitchToEmailClick: (() -> Unit)? = null, // Prepared for email mode
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        // 1. Owl Mascot Branding Header
        AuthHeaderSection(
            title = stringResource(Res.string.auth_phone_title),
            subtitle = stringResource(Res.string.auth_phone_subtitle),
            modifier = Modifier.padding(bottom = Spacing.Small),
        )

        // 2. Generalized ClayTextField configured with country badge & phone icon
        ClayTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            label = stringResource(Res.string.auth_phone_label),
            placeholder = stringResource(Res.string.auth_phone_placeholder),
            errorMessage = errorText?.asString(),
            enabled = !isLoading,
            leadingContent = {
                // Country Code Badge (+98 🇮🇷)
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(AppCorners.Pill))
                        .background(ClayTheme.colors.surface)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "+98",
                        style = AppTypography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        ),
                        color = ClayTheme.colors.textPrimary,
                    )
                    Text(
                        text = "🇮🇷",
                        fontSize = 15.sp,
                    )
                }
            },
            trailingContent = {
                BaseIcon(
                    resource = Res.drawable.ic_phone_outline,
                    contentDescription = null,
                    tint = ClayTheme.colors.textSecondary.copy(alpha = 0.65f),
                    modifier = Modifier.size(22.dp),
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { onSubmit() },
            ),
        )

        Spacer(modifier = Modifier.height(Spacing.ExtraSmall))

        // 3. Primary ClayButton with leading arrow icon
        ClayButton(
            text = stringResource(if (isLoading) Res.string.auth_btn_sending else Res.string.auth_btn_send_otp),
            onClick = onSubmit,
            enabled = phoneNumber.isNotBlank() && !isLoading,
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

        // 4. Quick Login Divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center,
        ) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = ClayTheme.colors.surfaceSunken,
                thickness = 1.dp,
            )
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(AppCorners.Pill))
                    .background(ClayTheme.colors.surface)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(ClayTheme.colors.primary.copy(alpha = 0.4f)),
                )
                Text(
                    text = stringResource(Res.string.auth_divider_quick_login),
                    style = AppTypography.bodySmall.copy(fontSize = 12.sp),
                    color = ClayTheme.colors.textSecondary,
                )
            }
        }

        // 5. Google SSO: Reusing ClayButton with Surface variant & Google logo
        ClayButton(
            text = stringResource(Res.string.auth_sso_google),
            onClick = onGoogleSsoClick,
            variant = ClayButtonVariant.Surface,
            cornerRadius = AppCorners.Pill,
            leadingIcon = {
                BaseIcon(
                    resource = Res.drawable.ic_google_logo,
                    contentDescription = "Google",
                    modifier = Modifier.size(20.dp),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
        )

        Spacer(modifier = Modifier.height(Spacing.Large))

        // 6. Guest Access Link
        if (onGuestLoginClick != null) {
            Row(
                modifier = Modifier
                    .clayPressable { onGuestLoginClick() }
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                BaseIcon(
                    resource = Res.drawable.ic_chevron_left,
                    contentDescription = null,
                    tint = ClayTheme.colors.primary,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(Res.string.auth_guest_login),
                    style = AppTypography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                    ),
                    color = ClayTheme.colors.primary,
                )
            }
        }
    }
}
