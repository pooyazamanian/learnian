package com.learnian.core.ui.component.textfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.modifiers.clayInsetGrooved
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme

/**
 * Claymorphism Sunken Text Input (clay-sunken / Deep Inset Trench)
 * Surface 0 Inset Grooved spec for high-legibility text input fields.
 */
@Composable
fun ClayTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    errorMessage: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    cornerRadius: Dp = AppCorners.Large
) {
    Column(modifier = modifier) {
        if (!label.isNullOrEmpty()) {
            Text(
                text = label,
                style = AppTypography.labelMedium,
                color = ClayTheme.colors.textSecondary,
                modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clayInsetGrooved(
                    backgroundColor = ClayTheme.colors.surfaceSunken,
                    cornerRadius = cornerRadius
                )
                .padding(horizontal = 16.dp, vertical = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(10.dp))
                }

                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty() && placeholder.isNotEmpty()) {
                        Text(
                            text = placeholder,
                            style = AppTypography.bodyLarge,
                            color = ClayTheme.colors.textSecondary.copy(alpha = 0.6f)
                        )
                    }

                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = singleLine,
                        enabled = enabled,
                        textStyle = AppTypography.bodyLarge.copy(color = ClayTheme.colors.textPrimary),
                        cursorBrush = SolidColor(ClayTheme.colors.primary),
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions
                    )
                }

                if (trailingIcon != null) {
                    Spacer(modifier = Modifier.width(10.dp))
                    trailingIcon()
                }
            }
        }

        if (!errorMessage.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                style = AppTypography.labelMedium,
                color = ClayTheme.colors.error,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}
