package com.learnian.core.ui.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.modifiers.clayInsetGrooved
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme

/**
 * 3D Claymorphic OTP / Verification Code Input (فیلد فرورفته چندرقمی کد تایید).
 *
 * Capabilities:
 * - Always renders cells Left-to-Right (LTR) regardless of active language/locale.
 * - Displays configurable number of inset grooved digit cells (default 5).
 * - Focused cell highlight border with primary clay color.
 * - Transparent underlying input driver with keyboard options.
 */
@Composable
fun ClayOtpInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = 5,
    cellSize: Dp = 56.dp,
    enabled: Boolean = true,
    onComplete: (() -> Unit)? = null,
) {
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clayPressable { focusRequester.requestFocus() },
        contentAlignment = Alignment.Center,
    ) {
        // Hidden input driving the text state
        BasicTextField(
            value = value,
            onValueChange = { input ->
                val filtered = input.filter { it.isDigit() }.take(length)
                onValueChange(filtered)
                if (filtered.length == length) {
                    onComplete?.invoke()
                }
            },
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { onComplete?.invoke() },
            ),
            modifier = Modifier
                .size(1.dp)
                .focusRequester(focusRequester),
        )

        // Visual Inset Grooved Cells (strictly LTR)
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (index in 0 until length) {
                    val digit = value.getOrNull(index)?.toString() ?: ""
                    val isFocused = value.length == index

                    Box(
                        modifier = Modifier
                            .size(cellSize)
                            .clayInsetGrooved(
                                backgroundColor = ClayTheme.colors.surfaceSunken,
                                cornerRadius = AppCorners.Large,
                            )
                            .then(
                                if (isFocused) {
                                    Modifier.border(
                                        width = 2.dp,
                                        color = ClayTheme.colors.primary,
                                        shape = RoundedCornerShape(AppCorners.Large),
                                    )
                                } else Modifier
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = digit,
                            style = AppTypography.headlineMedium.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 24.sp,
                            ),
                            color = ClayTheme.colors.textPrimary,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}
