package com.learnian.profile.presentation.component

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.button.ClayButtonVariant
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_chevron_left
import com.learnian.core.view.resources.ic_chevron_right
import com.learnian.core.view.resources.ic_pro_badge_doc
import com.learnian.core.view.resources.profile_pro_card_btn
import com.learnian.core.view.resources.profile_pro_card_desc
import com.learnian.core.view.resources.profile_pro_card_title
import org.jetbrains.compose.resources.stringResource

/**
 * Claymorphic Pro Subscription Banner Card.
 */
@Composable
fun ProfileProBannerCard(
    modifier: Modifier = Modifier,
    onUpgradeClick: () -> Unit = {},
) {
    val cardBackground = ClayTheme.colors.primaryContainer

    ClayCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = AppCorners.XL,
        backgroundColor = cardBackground,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // Right Section (in RTL): Title, Description, and CTA Button
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(Res.string.profile_pro_card_title),
                    style = AppTypography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 17.sp,
                    ),
                    color = ClayTheme.colors.primary,
                )

                Text(
                    text = stringResource(Res.string.profile_pro_card_desc),
                    style = AppTypography.bodySmall.copy(
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                    ),
                    color = ClayTheme.colors.textSecondary,
                )

                Spacer(modifier = Modifier.height(2.dp))

                val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

                ClayButton(
                    onClick = onUpgradeClick,
                    variant = ClayButtonVariant.Primary,
                    cornerRadius = AppCorners.Pill,
                    modifier = Modifier.fillMaxWidth(0.92f),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        if (isRtl) {
                            BaseIcon(
                                resource = Res.drawable.ic_chevron_left,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp),
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = stringResource(Res.string.profile_pro_card_btn),
                                style = AppTypography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                ),
                                color = Color.White,
                            )
                        } else {
                            Text(
                                text = stringResource(Res.string.profile_pro_card_btn),
                                style = AppTypography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                ),
                                color = Color.White,
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            BaseIcon(
                                resource = Res.drawable.ic_chevron_right,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp),
                            )
                        }
                    }
                }
            }

            // Left Section (in RTL): 3D Document Illustration with Sparkles
            Box(
                modifier = Modifier.size(80.dp),
                contentAlignment = Alignment.Center,
            ) {
                BaseImage(
                    resource = Res.drawable.ic_pro_badge_doc,
                    contentDescription = stringResource(Res.string.profile_pro_card_title),
                    modifier = Modifier.size(76.dp),
                )
            }
        }
    }
}
