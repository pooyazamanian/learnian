package com.learnian.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.learnian.core.datastore.model.AppFont

/**
 * Creates dynamic typography based on selected font family and scale multiplier.
 */
fun createAppTypography(
    font: AppFont = AppFont.DEFAULT,
    fontScale: Float = 1.0f,
): Typography {
    val fontFamily = when (font) {
        AppFont.DEFAULT -> FontFamily.Default
        AppFont.VAZIR -> FontFamily.SansSerif
        AppFont.IRANYEKAN -> FontFamily.Default
    }

    return Typography(
        headlineLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (32 * fontScale).sp,
            lineHeight = (40 * fontScale).sp,
            letterSpacing = 0.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (28 * fontScale).sp,
            lineHeight = (36 * fontScale).sp,
            letterSpacing = 0.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (24 * fontScale).sp,
            lineHeight = (32 * fontScale).sp,
            letterSpacing = 0.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = (22 * fontScale).sp,
            lineHeight = (28 * fontScale).sp,
            letterSpacing = 0.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = (16 * fontScale).sp,
            lineHeight = (24 * fontScale).sp,
            letterSpacing = 0.15.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (16 * fontScale).sp,
            lineHeight = (24 * fontScale).sp,
            letterSpacing = 0.5.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (14 * fontScale).sp,
            lineHeight = (20 * fontScale).sp,
            letterSpacing = 0.25.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (12 * fontScale).sp,
            lineHeight = (16 * fontScale).sp,
            letterSpacing = 0.4.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = (12 * fontScale).sp,
            lineHeight = (16 * fontScale).sp,
            letterSpacing = 0.5.sp,
        ),
    )
}

val AppTypography: Typography = createAppTypography()
