package com.learnian.profile.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.datastore.model.AppFont
import com.learnian.core.datastore.model.AppLanguage
import com.learnian.core.datastore.model.FontSizeScale
import com.learnian.core.datastore.model.ThemeMode
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.modifiers.clayPressable
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_person
import com.learnian.core.view.resources.settings_font
import com.learnian.core.view.resources.settings_font_default
import com.learnian.core.view.resources.settings_font_iranyekan
import com.learnian.core.view.resources.settings_font_size
import com.learnian.core.view.resources.settings_font_size_large
import com.learnian.core.view.resources.settings_font_size_medium
import com.learnian.core.view.resources.settings_font_size_small
import com.learnian.core.view.resources.settings_font_vazir
import com.learnian.core.view.resources.settings_language
import com.learnian.core.view.resources.settings_language_en
import com.learnian.core.view.resources.settings_language_fa
import com.learnian.core.view.resources.settings_theme
import com.learnian.core.view.resources.settings_theme_dark
import com.learnian.core.view.resources.settings_theme_light
import com.learnian.core.view.resources.settings_theme_system
import com.learnian.core.view.resources.settings_title
import com.learnian.core.view.resources.settings_user_badge
import com.learnian.core.view.resources.settings_user_name
import com.learnian.profile.presentation.mvi.SettingsIntent
import com.learnian.profile.presentation.viewmodel.SettingsViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Claymorphic Profile & Settings Screen.
 */
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val settings = state.userSettings

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // User Profile Header Card
        ClayCard(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = AppCorners.XL,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .clayRestingFloat(
                            backgroundColor = ClayTheme.colors.primary,
                            cornerRadius = AppCorners.Pill,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    BaseIcon(
                        resource = Res.drawable.ic_person,
                        contentDescription = stringResource(Res.string.settings_user_name),
                        tint = Color.White,
                        modifier = Modifier.size(32.dp),
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = stringResource(Res.string.settings_user_name),
                        style = AppTypography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = ClayTheme.colors.textPrimary,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(Res.string.settings_user_badge),
                        style = AppTypography.bodyMedium,
                        color = ClayTheme.colors.primary,
                    )
                }
            }
        }

        // Appearance Section: Theme Mode
        ClayCard(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = AppCorners.XL,
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(Res.string.settings_theme),
                    style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = ClayTheme.colors.textPrimary,
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    SettingOptionChip(
                        text = stringResource(Res.string.settings_theme_system),
                        isSelected = settings.themeMode == ThemeMode.SYSTEM,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.dispatch(SettingsIntent.SetThemeMode(ThemeMode.SYSTEM)) },
                    )
                    SettingOptionChip(
                        text = stringResource(Res.string.settings_theme_light),
                        isSelected = settings.themeMode == ThemeMode.LIGHT,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.dispatch(SettingsIntent.SetThemeMode(ThemeMode.LIGHT)) },
                    )
                    SettingOptionChip(
                        text = stringResource(Res.string.settings_theme_dark),
                        isSelected = settings.themeMode == ThemeMode.DARK,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.dispatch(SettingsIntent.SetThemeMode(ThemeMode.DARK)) },
                    )
                }
            }
        }

        // Language Section
        ClayCard(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = AppCorners.XL,
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(Res.string.settings_language),
                    style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = ClayTheme.colors.textPrimary,
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    SettingOptionChip(
                        text = stringResource(Res.string.settings_language_fa),
                        isSelected = settings.language == AppLanguage.FA,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.dispatch(SettingsIntent.SetLanguage(AppLanguage.FA)) },
                    )
                    SettingOptionChip(
                        text = stringResource(Res.string.settings_language_en),
                        isSelected = settings.language == AppLanguage.EN,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.dispatch(SettingsIntent.SetLanguage(AppLanguage.EN)) },
                    )
                }
            }
        }

        // Font Size Scale Section
        ClayCard(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = AppCorners.XL,
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(Res.string.settings_font_size),
                    style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = ClayTheme.colors.textPrimary,
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    SettingOptionChip(
                        text = stringResource(Res.string.settings_font_size_small),
                        isSelected = settings.fontSizeScale == FontSizeScale.SMALL,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.dispatch(SettingsIntent.SetFontSizeScale(FontSizeScale.SMALL)) },
                    )
                    SettingOptionChip(
                        text = stringResource(Res.string.settings_font_size_medium),
                        isSelected = settings.fontSizeScale == FontSizeScale.MEDIUM,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.dispatch(SettingsIntent.SetFontSizeScale(FontSizeScale.MEDIUM)) },
                    )
                    SettingOptionChip(
                        text = stringResource(Res.string.settings_font_size_large),
                        isSelected = settings.fontSizeScale == FontSizeScale.LARGE,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.dispatch(SettingsIntent.SetFontSizeScale(FontSizeScale.LARGE)) },
                    )
                }
            }
        }

        // Font Family Section
        ClayCard(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = AppCorners.XL,
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(Res.string.settings_font),
                    style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = ClayTheme.colors.textPrimary,
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    SettingOptionChip(
                        text = stringResource(Res.string.settings_font_default),
                        isSelected = settings.font == AppFont.DEFAULT,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.dispatch(SettingsIntent.SetFont(AppFont.DEFAULT)) },
                    )
                    SettingOptionChip(
                        text = stringResource(Res.string.settings_font_vazir),
                        isSelected = settings.font == AppFont.VAZIR,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.dispatch(SettingsIntent.SetFont(AppFont.VAZIR)) },
                    )
                    SettingOptionChip(
                        text = stringResource(Res.string.settings_font_iranyekan),
                        isSelected = settings.font == AppFont.IRANYEKAN,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.dispatch(SettingsIntent.SetFont(AppFont.IRANYEKAN)) },
                    )
                }
            }
        }
    }
}

/**
 * Reusable Claymorphism Toggle Option Chip.
 */
@Composable
private fun SettingOptionChip(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val backgroundColor = if (isSelected) ClayTheme.colors.primary else ClayTheme.colors.surface
    val textColor = if (isSelected) Color.White else ClayTheme.colors.textPrimary

    Box(
        modifier = modifier
            .clayPressable(onClick = onClick)
            .clayRestingFloat(
                backgroundColor = backgroundColor,
                cornerRadius = AppCorners.Large,
            )
            .padding(vertical = 12.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = AppTypography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                fontSize = 13.sp,
            ),
            color = textColor,
        )
    }
}
