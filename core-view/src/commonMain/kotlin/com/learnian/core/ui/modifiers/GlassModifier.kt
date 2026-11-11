package com.learnian.core.ui.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.theme.AppColors

fun Modifier.glassEffect(
    cornerRadius: Dp = 24.dp,
    borderWidth: Dp = 1.dp
): Modifier = this
    .background(
        color = AppColors.GlassSurface,
        shape = RoundedCornerShape(cornerRadius)
    )
    .border(
        width = borderWidth,
        color = AppColors.GlassBorder,
        shape = RoundedCornerShape(cornerRadius)
    )
    .clip(RoundedCornerShape(cornerRadius))
