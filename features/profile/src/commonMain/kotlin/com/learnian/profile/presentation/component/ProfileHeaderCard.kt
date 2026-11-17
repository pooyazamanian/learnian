package com.learnian.profile.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayIconButton
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ChipIconPosition
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.component.progress.ClayProgressBar
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_avatar_default
import com.learnian.core.view.resources.ic_edit_pen
import com.learnian.core.view.resources.ic_ribbon_medal
import com.learnian.core.view.resources.profile_edit_avatar_desc
import com.learnian.core.view.resources.profile_pro_badge
import com.learnian.core.view.resources.profile_progress_to_level
import com.learnian.core.view.resources.profile_user_name_default
import com.learnian.core.view.resources.profile_vocab_level
import com.learnian.core.view.resources.profile_xp_format
import org.jetbrains.compose.resources.stringResource

/**
 * Claymorphic Profile Header Card.
 *
 * Implements the user's gamified profile overview including:
 * - Floating circular avatar with server URL support and 3D vector fallback
 * - Overlay clay edit pen button
 * - User name and membership status chip
 * - Vocabulary level ribbon badge
 * - Level progress information with XP indicators
 * - Sunken clay progress bar
 */
@Composable
fun ProfileHeaderCard(
    modifier: Modifier = Modifier,
    userName: String = stringResource(Res.string.profile_user_name_default),
    userAvatarUrl: String? = null,
    nextLevel: Int = 29,
    currentXp: Int = 740,
    maxXp: Int = 1000,
    isPro: Boolean = true,
    vocabLevelText: String = stringResource(Res.string.profile_vocab_level),
    onEditAvatarClick: () -> Unit = {},
) {
    val progress = (currentXp.toFloat() / maxXp.toFloat()).coerceIn(0f, 1f)

    // Mint palette for Pro membership
    val proBg = ClayTheme.colors.secondaryContainer
    val proBorder = ClayTheme.colors.secondary.copy(alpha = 0.3f)
    val proText = ClayTheme.colors.secondary

    // Lavender palette for Vocab badge
    val vocabBg = ClayTheme.colors.primaryContainer
    val vocabBorder = ClayTheme.colors.primary.copy(alpha = 0.3f)
    val vocabText = ClayTheme.colors.primary

    ClayCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = AppCorners.XL,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            // 1. Avatar with overlay Edit Button
            Box(
                modifier = Modifier.size(108.dp),
                contentAlignment = Alignment.Center,
            ) {
                // Circular clay ambient container
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clayRestingFloat(
                            backgroundColor = ClayTheme.colors.surface,
                            cornerRadius = AppCorners.Pill,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    BaseImage(
                        url = userAvatarUrl,
                        fallbackResource = Res.drawable.ic_avatar_default,
                        contentDescription = userName,
                        modifier = Modifier
                            .size(92.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                }

                // Claymorphic edit pencil button badge at bottom corner
                ClayIconButton(
                    onClick = onEditAvatarClick,
                    size = 34.dp,
                    cornerRadius = AppCorners.Pill,
                    backgroundColor = ClayTheme.colors.primary,
                    modifier = Modifier.align(Alignment.BottomEnd),
                ) {
                    BaseIcon(
                        resource = Res.drawable.ic_edit_pen,
                        contentDescription = stringResource(Res.string.profile_edit_avatar_desc),
                        tint = Color.White,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }

            // 2. User Name
            Text(
                text = userName,
                style = AppTypography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                ),
                color = ClayTheme.colors.textPrimary,
                textAlign = TextAlign.Center,
            )

            // 3. Pro Membership Embossed 3D Badge
            if (isPro) {
                ClayStatusChip(
                    text = stringResource(Res.string.profile_pro_badge),
                    style = ClayChipStyle.Embossed,
                    textColor = proText,
                    backgroundColor = proBg,
                    borderColor = proBorder,
                    borderWidth = 1.dp,
                    shadowColor = ClayTheme.colors.shadowOuter,
                    textStyle = AppTypography.bodyMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                    ),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                )
            }

            // 4. Vocabulary Level Ribbon Embossed 3D Badge
            ClayStatusChip(
                text = vocabLevelText,
                style = ClayChipStyle.Embossed,
                icon = {
                    BaseIcon(
                        resource = Res.drawable.ic_ribbon_medal,
                        contentDescription = null,
                        tint = vocabText,
                        modifier = Modifier.size(19.dp),
                    )
                },
                iconPosition = ChipIconPosition.End,
                textColor = vocabText,
                backgroundColor = vocabBg,
                borderColor = vocabBorder,
                borderWidth = 1.dp,
                shadowColor = ClayTheme.colors.shadowOuter,
                textStyle = AppTypography.bodyMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                ),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            )

            Spacer(modifier = Modifier.height(2.dp))

            // 5. Level Progress Text Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
                val currentXpFormatted = formatXpNumber(currentXp, isRtl)
                val maxXpFormatted = formatXpNumber(maxXp, isRtl)
                val nextLevelFormatted = if (isRtl) formatLocalizedDigits(nextLevel.toString()) else nextLevel.toString()

                Text(
                    text = stringResource(Res.string.profile_xp_format, maxXpFormatted, currentXpFormatted),
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                    ),
                    color = ClayTheme.colors.primary,
                )

                Text(
                    text = stringResource(Res.string.profile_progress_to_level, nextLevelFormatted),
                    style = AppTypography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    ),
                    color = ClayTheme.colors.textPrimary,
                )
            }

            // 6. Base Claymorphic Progress Bar
            ClayProgressBar(
                progress = progress,
                height = 14.dp,
                progressColor = ClayTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
            )
        }
    }
}

/**
 * Formats localized digits for RTL Persian.
 */
private fun formatLocalizedDigits(text: String): String {
    return text.map { ch ->
        when (ch) {
            '0' -> '۰'
            '1' -> '۱'
            '2' -> '۲'
            '3' -> '۳'
            '4' -> '۴'
            '5' -> '۵'
            '6' -> '۶'
            '7' -> '۷'
            '8' -> '۸'
            '9' -> '۹'
            else -> ch
        }
    }.joinToString("")
}

/**
 * Formats XP with thousands separator and localized digits.
 */
private fun formatXpNumber(number: Int, isPersian: Boolean): String {
    return if (number >= 1000) {
        val thousands = number / 1000
        val remainder = number % 1000
        if (isPersian) {
            val thousandsFa = formatLocalizedDigits(thousands.toString())
            if (remainder == 0) "$thousandsFa,۰۰۰" else "$thousandsFa,${formatLocalizedDigits(remainder.toString())}"
        } else {
            if (remainder == 0) "$thousands,000" else "$thousands,$remainder"
        }
    } else {
        if (isPersian) formatLocalizedDigits(number.toString()) else number.toString()
    }
}
