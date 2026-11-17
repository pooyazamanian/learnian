package com.learnian.auth.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.button.ClayButtonVariant
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.textfield.ClayTextField
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.auth_password_subtitle
import com.learnian.core.view.resources.auth_password_title
import com.learnian.core.view.resources.btn_login
import com.learnian.core.view.resources.ic_chevron_right
import org.jetbrains.compose.resources.stringResource

/**
 * Step 3: Password verification screen.
 * Prepared for future activation when password authentication is enabled on the server.
 */
@Composable
fun PasswordInputContent(
    phoneNumber: String,
    password: String,
    isLoading: Boolean,
    errorMessage: String?,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
    ) {
        // Back Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clayPressable(onClick = onBackClick)
                .padding(vertical = Spacing.ExtraSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall),
        ) {
            BaseIcon(
                resource = Res.drawable.ic_chevron_right,
                contentDescription = "بازگشت",
                tint = ClayTheme.colors.primary,
                modifier = Modifier.size(24.dp),
            )
            Text(
                text = "بازگشت",
                style = AppTypography.labelLarge,
                color = ClayTheme.colors.primary,
            )
        }

        // Header Section
        AuthHeaderSection(
            title = stringResource(Res.string.auth_password_title),
            subtitle = stringResource(Res.string.auth_password_subtitle),
        )

        Spacer(modifier = Modifier.height(Spacing.Small))

        // Password Input
        ClayTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = stringResource(Res.string.auth_password_title),
            placeholder = "••••••••",
            errorMessage = errorMessage,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { onSubmit() },
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        // Submit Button
        ClayButton(
            onClick = onSubmit,
            enabled = password.length >= 6 && !isLoading,
            variant = ClayButtonVariant.Primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
        ) {
            Text(
                text = if (isLoading) "در حال بررسی..." else stringResource(Res.string.btn_login),
                style = AppTypography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                ),
            )
        }
    }
}
