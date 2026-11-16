package com.learnian.core.ui.component.topbar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayIconButton
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.app_name
import com.learnian.core.view.resources.default_gems
import com.learnian.core.view.resources.default_streak
import com.learnian.core.view.resources.ic_gem
import com.learnian.core.view.resources.ic_learnian_eye_logo
import com.learnian.core.view.resources.ic_person
import com.learnian.core.view.resources.ic_streak_flame
import com.learnian.core.view.resources.topbar_gem_desc
import com.learnian.core.view.resources.topbar_logo_desc
import com.learnian.core.view.resources.topbar_profile_desc
import com.learnian.core.view.resources.topbar_streak_desc
import org.jetbrains.compose.resources.stringResource

/**
 * Claymorphism Floating Top App Bar (clay-topbar)
 *
 * Implements the brand gamification pattern:
 * - Start: Glossy 3D Brand Logo + localized brand title (or custom title with vertical slide/fade animation)
 * - Center: Dual Gamification Pills (Streak Flame + Gem Stone)
 * - End: Circular 3D purple profile button with person icon (and custom action hooks)
 */
@Composable
fun ClayTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    brandText: String = stringResource(Res.string.app_name),
    showLogo: Boolean = true,
    logo: (@Composable () -> Unit)? = null,
    streakText: String? = stringResource(Res.string.default_streak),
    gemText: String? = stringResource(Res.string.default_gems),
    onStreakClick: (() -> Unit)? = null,
    onGemClick: (() -> Unit)? = null,
    onProfileClick: (() -> Unit)? = null,
    onMoreClick: (() -> Unit)? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    cornerRadius: Dp = AppCorners.XL,
    flat: Boolean = false,
) {
    val containerModifier = if (flat) {
        modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(ClayTheme.colors.surface)
            .height(64.dp)
            .padding(horizontal = 20.dp)
    } else {
        modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clayRestingFloat(
                backgroundColor = ClayTheme.colors.surface,
                cornerRadius = cornerRadius
            )
            .height(64.dp)
            .padding(horizontal = 12.dp)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = containerModifier,
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Start section: Navigation Icon OR Glossy Logo + localized Brand Name (or animated Title)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (navigationIcon != null) {
                        navigationIcon()
                        Spacer(modifier = Modifier.width(8.dp))
                    } else if (showLogo) {
                        if (logo != null) {
                            logo()
                        } else {
                            BaseImage(
                                resource = Res.drawable.ic_learnian_eye_logo,
                                contentDescription = stringResource(Res.string.topbar_logo_desc),
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                    }

                    val displayTitle = title ?: brandText
                    AnimatedContent(
                        targetState = displayTitle,
                        transitionSpec = {
                            (slideInVertically(animationSpec = tween(260)) { -it / 2 } + fadeIn(
                                animationSpec = tween(260)
                            ))
                                .togetherWith(
                                    slideOutVertically(animationSpec = tween(200)) { it / 2 } + fadeOut(
                                        animationSpec = tween(200)
                                    )
                                )
                        },
                        label = "TopBarTitleAnimation"
                    ) { animatedTitle ->
                        Text(
                            text = animatedTitle,
                            style = AppTypography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp
                            ),
                            color = ClayTheme.colors.primary
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (gemText != null) {
                        ClayStatusChip(
                            text = gemText,
                            textColor = Color(0xFF00796B),
                            icon = {
                                BaseIcon(
                                    resource = Res.drawable.ic_gem,
                                    contentDescription = stringResource(Res.string.topbar_gem_desc),
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            onClick = onGemClick
                        )
                    }

                    if (streakText != null) {
                        ClayStatusChip(
                            text = streakText,
                            textColor = Color(0xFFB5700A),
                            icon = {
                                BaseIcon(
                                    resource = Res.drawable.ic_streak_flame,
                                    contentDescription = stringResource(Res.string.topbar_streak_desc),
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            onClick = onStreakClick
                        )
                    }
                }

                // End section: Circular Purple Profile 3D Button & Custom Actions
                if ((actions != null) || (onProfileClick != null) || (onMoreClick != null)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                    ) {
                        if (actions != null) {
                            actions()
                        }

                        val profileClickAction = onProfileClick ?: onMoreClick
                        if (profileClickAction != null) {
                            ClayIconButton(
                                onClick = profileClickAction,
                                size = 42.dp,
                                cornerRadius = AppCorners.Pill,
                                backgroundColor = ClayTheme.colors.primary
                            ) {
                                BaseIcon(
                                    resource = Res.drawable.ic_person,
                                    contentDescription = stringResource(Res.string.topbar_profile_desc),
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
            if (flat) {
                HorizontalDivider(
                    color = ClayTheme.colors.surfaceSunken.copy(alpha = 0.6f),
                    thickness = 1.dp
                )
            }
        }


    }
}

