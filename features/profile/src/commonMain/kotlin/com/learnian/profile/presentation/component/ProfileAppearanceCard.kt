package com.learnian.profile.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.datastore.model.AppFont
import com.learnian.core.datastore.model.AppLanguage
import com.learnian.core.datastore.model.FontSizeScale
import com.learnian.core.datastore.model.ThemeMode
import com.learnian.core.datastore.model.UserSettings
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.radio.ClayRadioCard
import com.learnian.core.ui.component.segmented.ClaySegment
import com.learnian.core.ui.component.segmented.ClaySegmentedSlider
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_device_theme
import com.learnian.core.view.resources.ic_font_family
import com.learnian.core.view.resources.ic_language
import com.learnian.core.view.resources.ic_moon
import com.learnian.core.view.resources.ic_palette
import com.learnian.core.view.resources.ic_sun
import com.learnian.core.view.resources.ic_text_size
import com.learnian.core.view.resources.settings_font
import com.learnian.core.view.resources.settings_font_default
import com.learnian.core.view.resources.settings_font_desc
import com.learnian.core.view.resources.settings_font_iranyekan
import com.learnian.core.view.resources.settings_font_size
import com.learnian.core.view.resources.settings_font_size_desc
import com.learnian.core.view.resources.settings_font_size_large
import com.learnian.core.view.resources.settings_font_size_medium
import com.learnian.core.view.resources.settings_font_size_small
import com.learnian.core.view.resources.settings_font_vazir
import com.learnian.core.view.resources.settings_language
import com.learnian.core.view.resources.settings_language_desc
import com.learnian.core.view.resources.settings_language_en
import com.learnian.core.view.resources.settings_language_en_sub
import com.learnian.core.view.resources.settings_language_fa
import com.learnian.core.view.resources.settings_language_fa_sub
import com.learnian.core.view.resources.settings_theme
import com.learnian.core.view.resources.settings_theme_dark
import com.learnian.core.view.resources.settings_theme_desc
import com.learnian.core.view.resources.settings_theme_light
import com.learnian.core.view.resources.settings_theme_system
import org.jetbrains.compose.resources.stringResource

/**
 * Unified Appearance and Display Settings Card.
 *
 * Implements:
 * 1. Theme Mode (ClaySegmentedSlider with Device, Sun ☀️, and Moon 🌙 icons)
 * 2. Application Language (ClayRadioCards for Persian RTL and English LTR)
 * 3. Text & Font Size Scaling (ClaySegmentedSlider with visual size progression A -> A -> A)
 * 4. Typography & Font Family (ClaySegmentedSlider for System, Vazirmatn, and IranYekan)
 */
@Composable
fun ProfileAppearanceCard(
    settings: UserSettings,
    onThemeModeChange: (ThemeMode) -> Unit,
    onLanguageChange: (AppLanguage) -> Unit,
    onFontSizeScaleChange: (FontSizeScale) -> Unit,
    onFontChange: (AppFont) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dividerColor = ClayTheme.colors.surfaceSunken.copy(alpha = 0.5f)

    ClayCard(
        modifier = modifier.fillMaxWidth(),
        cornerRadius = AppCorners.XL,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // ---------------- 1. Theme Mode (ClaySegmentedSlider with Icons) ----------------
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        BaseIcon(
                            resource = Res.drawable.ic_palette,
                            contentDescription = null,
                            tint = ClayTheme.colors.primary,
                            modifier = Modifier.size(20.dp),
                        )
                        Column {
                            Text(
                                text = stringResource(Res.string.settings_theme),
                                style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = ClayTheme.colors.textPrimary,
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = stringResource(Res.string.settings_theme_desc),
                                style = AppTypography.bodySmall,
                                color = ClayTheme.colors.textSecondary,
                            )
                        }
                    }

                    val currentThemeLabel = when (settings.themeMode) {
                        ThemeMode.SYSTEM -> stringResource(Res.string.settings_theme_system)
                        ThemeMode.LIGHT -> stringResource(Res.string.settings_theme_light)
                        ThemeMode.DARK -> stringResource(Res.string.settings_theme_dark)
                    }
                    Text(
                        text = currentThemeLabel,
                        style = AppTypography.bodyMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.sp,
                        ),
                        color = ClayTheme.colors.primary,
                    )
                }

                ClaySegmentedSlider(
                    selectedValue = settings.themeMode,
                    onValueChange = onThemeModeChange,
                    segments = listOf(
                        ClaySegment(
                            value = ThemeMode.SYSTEM,
                            label = stringResource(Res.string.settings_theme_system),
                            icon = { isSelected ->
                                BaseIcon(
                                    resource = Res.drawable.ic_device_theme,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else ClayTheme.colors.primary,
                                    modifier = Modifier.size(17.dp),
                                )
                            },
                        ),
                        ClaySegment(
                            value = ThemeMode.LIGHT,
                            label = stringResource(Res.string.settings_theme_light),
                            icon = { isSelected ->
                                BaseIcon(
                                    resource = Res.drawable.ic_sun,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else Color(0xFFF59E0B),
                                    modifier = Modifier.size(17.dp),
                                )
                            },
                        ),
                        ClaySegment(
                            value = ThemeMode.DARK,
                            label = stringResource(Res.string.settings_theme_dark),
                            icon = { isSelected ->
                                BaseIcon(
                                    resource = Res.drawable.ic_moon,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else Color(0xFF818CF8),
                                    modifier = Modifier.size(17.dp),
                                )
                            },
                        ),
                    ),
                    height = 46.dp,
                )
            }

            HorizontalDivider(color = dividerColor, thickness = 1.dp)

            // ---------------- 2. Application Language ----------------
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        BaseIcon(
                            resource = Res.drawable.ic_language,
                            contentDescription = null,
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(20.dp),
                        )
                        Column {
                            Text(
                                text = stringResource(Res.string.settings_language),
                                style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = ClayTheme.colors.textPrimary,
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = stringResource(Res.string.settings_language_desc),
                                style = AppTypography.bodySmall,
                                color = ClayTheme.colors.textSecondary,
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    ClayRadioCard(
                        selected = settings.language == AppLanguage.FA,
                        onClick = { onLanguageChange(AppLanguage.FA) },
                        title = stringResource(Res.string.settings_language_fa),
                        subtitle = stringResource(Res.string.settings_language_fa_sub),
                        modifier = Modifier.weight(1f),
                    )

                    ClayRadioCard(
                        selected = settings.language == AppLanguage.EN,
                        onClick = { onLanguageChange(AppLanguage.EN) },
                        title = stringResource(Res.string.settings_language_en),
                        subtitle = stringResource(Res.string.settings_language_en_sub),
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            HorizontalDivider(color = dividerColor, thickness = 1.dp)

            // ---------------- 3. Text & Font Size Scaling (Visual Progression) ----------------
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        BaseIcon(
                            resource = Res.drawable.ic_text_size,
                            contentDescription = null,
                            tint = Color(0xFF00A86B),
                            modifier = Modifier.size(20.dp),
                        )
                        Column {
                            Text(
                                text = stringResource(Res.string.settings_font_size),
                                style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = ClayTheme.colors.textPrimary,
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = stringResource(Res.string.settings_font_size_desc),
                                style = AppTypography.bodySmall,
                                color = ClayTheme.colors.textSecondary,
                            )
                        }
                    }

                    val isPersian = settings.language == AppLanguage.FA
                    val scaleLabel = when (settings.fontSizeScale) {
                        FontSizeScale.SMALL -> if (isPersian) "۸۸٪" else "88%"
                        FontSizeScale.MEDIUM -> if (isPersian) "۱۰۰٪" else "100%"
                        FontSizeScale.LARGE -> if (isPersian) "۱۱۵٪" else "115%"
                    }
                    Text(
                        text = scaleLabel,
                        style = AppTypography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp,
                        ),
                        color = Color(0xFF00A86B),
                    )
                }

                // Sliding Segmented Slider showing visual typography progression (Small A -> Medium A -> Large A)
                ClaySegmentedSlider(
                    selectedValue = settings.fontSizeScale,
                    onValueChange = onFontSizeScaleChange,
                    segments = listOf(
                        ClaySegment(
                            value = FontSizeScale.SMALL,
                            customContent = { isSelected ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                                ) {
                                    Text(
                                        text = "A",
                                        style = AppTypography.bodySmall.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 11.sp,
                                        ),
                                        color = if (isSelected) Color.White else Color(0xFF00A86B),
                                    )
                                    Text(
                                        text = stringResource(Res.string.settings_font_size_small),
                                        style = AppTypography.bodyMedium.copy(
                                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                                            fontSize = 12.sp,
                                        ),
                                        color = if (isSelected) Color.White else ClayTheme.colors.textPrimary,
                                    )
                                }
                            },
                        ),
                        ClaySegment(
                            value = FontSizeScale.MEDIUM,
                            customContent = { isSelected ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                ) {
                                    Text(
                                        text = "A",
                                        style = AppTypography.bodyMedium.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 15.sp,
                                        ),
                                        color = if (isSelected) Color.White else Color(0xFF00A86B),
                                    )
                                    Text(
                                        text = stringResource(Res.string.settings_font_size_medium),
                                        style = AppTypography.bodyMedium.copy(
                                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                                            fontSize = 13.sp,
                                        ),
                                        color = if (isSelected) Color.White else ClayTheme.colors.textPrimary,
                                    )
                                }
                            },
                        ),
                        ClaySegment(
                            value = FontSizeScale.LARGE,
                            customContent = { isSelected ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                ) {
                                    Text(
                                        text = "A",
                                        style = AppTypography.titleMedium.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 19.sp,
                                        ),
                                        color = if (isSelected) Color.White else Color(0xFF00A86B),
                                    )
                                    Text(
                                        text = stringResource(Res.string.settings_font_size_large),
                                        style = AppTypography.bodyMedium.copy(
                                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                                            fontSize = 14.sp,
                                        ),
                                        color = if (isSelected) Color.White else ClayTheme.colors.textPrimary,
                                    )
                                }
                            },
                        ),
                    ),
                    height = 46.dp,
                )
            }

            HorizontalDivider(color = dividerColor, thickness = 1.dp)

            // ---------------- 4. Typography & Font Family ----------------
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        BaseIcon(
                            resource = Res.drawable.ic_font_family,
                            contentDescription = null,
                            tint = Color(0xFF7C3AED),
                            modifier = Modifier.size(20.dp),
                        )
                        Column {
                            Text(
                                text = stringResource(Res.string.settings_font),
                                style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = ClayTheme.colors.textPrimary,
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = stringResource(Res.string.settings_font_desc),
                                style = AppTypography.bodySmall,
                                color = ClayTheme.colors.textSecondary,
                            )
                        }
                    }
                }

                ClaySegmentedSlider(
                    selectedValue = settings.font,
                    onValueChange = onFontChange,
                    segments = listOf(
                        ClaySegment(
                            value = AppFont.DEFAULT,
                            label = stringResource(Res.string.settings_font_default),
                        ),
                        ClaySegment(
                            value = AppFont.VAZIR,
                            label = stringResource(Res.string.settings_font_vazir),
                        ),
                        ClaySegment(
                            value = AppFont.IRANYEKAN,
                            label = stringResource(Res.string.settings_font_iranyekan),
                        ),
                    ),
                    height = 46.dp,
                )
            }
        }
    }
}
