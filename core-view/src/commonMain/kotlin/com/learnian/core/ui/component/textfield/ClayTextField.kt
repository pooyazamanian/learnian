package com.learnian.core.ui.component.textfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.modifiers.clayInsetGrooved
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme

/**
 * Generalized Claymorphic Inset Trench Input (clay-sunken / Deep Inset Trench).
 * Implements Surface 0 Inset Grooved spec.
 *
 * Supports:
 * - Configurable label, placeholder, and validation error messages.
 * - [leadingContent] / [leadingIcon] for start-aligned icons, country flag badges, or pills.
 * - [trailingContent] / [trailingIcon] for end-aligned icons, clear buttons, or action toggles.
 * - Customizable typography ([textStyle], [placeholderStyle]) and [contentPadding].
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
    leadingContent: (@Composable () -> Unit)? = leadingIcon,
    trailingContent: (@Composable () -> Unit)? = trailingIcon,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    cornerRadius: Dp = AppCorners.Large,
    textStyle: TextStyle? = null,
    placeholderStyle: TextStyle? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (!label.isNullOrEmpty()) {
            Text(
                text = label,
                style = AppTypography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                ),
                color = ClayTheme.colors.textPrimary,
                modifier = Modifier.padding(bottom = 6.dp, start = 4.dp),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clayInsetGrooved(
                    backgroundColor = ClayTheme.colors.surfaceSunken,
                    cornerRadius = cornerRadius,
                )
                .padding(contentPadding),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                val activeLeading = leadingContent ?: leadingIcon
                if (activeLeading != null) {
                    activeLeading()
                    Spacer(modifier = Modifier.width(10.dp))
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (value.isEmpty() && placeholder.isNotEmpty()) {
                        val effectivePlaceholderStyle = placeholderStyle ?: AppTypography.bodyLarge.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ClayTheme.colors.textSecondary.copy(alpha = 0.45f),
                        )
                        Text(
                            text = placeholder,
                            style = effectivePlaceholderStyle,
                        )
                    }

                    val effectiveTextStyle = textStyle ?: AppTypography.bodyLarge.copy(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = ClayTheme.colors.textPrimary,
                    )
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = singleLine,
                        enabled = enabled,
                        textStyle = effectiveTextStyle,
                        cursorBrush = SolidColor(ClayTheme.colors.primary),
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                    )
                }

                val activeTrailing = trailingContent ?: trailingIcon
                if (activeTrailing != null) {
                    Spacer(modifier = Modifier.width(10.dp))
                    activeTrailing()
                }
            }
        }

        if (!errorMessage.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                style = AppTypography.labelMedium.copy(
                    fontSize = 12.sp,
                ),
                color = ClayTheme.colors.error,
                modifier = Modifier.padding(start = 4.dp),
            )
        }
    }
}
