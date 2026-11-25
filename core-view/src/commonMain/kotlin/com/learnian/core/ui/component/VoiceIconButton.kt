package com.learnian.core.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.component.button.ClayIconButton
import com.learnian.core.ui.theme.ClayTheme

/**
 * Large clay icon button for voice / audio actions.
 *
 * The icon and its tint come from [content], so callers keep full control of the
 * drawable (play, pause, speaker) while size and container color stay uniform.
 */
@Composable
fun VoiceIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    useSurface: Boolean = true,
    content: @Composable () -> Unit,
) {
    ClayIconButton(
        onClick = onClick,
        modifier = modifier,
        size = size,
        backgroundColor = if (useSurface) ClayTheme.colors.surface else ClayTheme.colors.primary,
        content = content,
    )
}