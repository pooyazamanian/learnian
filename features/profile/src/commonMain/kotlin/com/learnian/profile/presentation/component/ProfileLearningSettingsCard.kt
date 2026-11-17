package com.learnian.profile.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
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
import com.learnian.core.datastore.model.AudioAccent
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.chip.ChipIconPosition
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.radio.ClayRadioCard
import com.learnian.core.ui.component.segmented.ClaySegment
import com.learnian.core.ui.component.segmented.ClaySegmentedSlider
import com.learnian.core.ui.component.switch.ClaySwitch
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_brain_ai
import com.learnian.core.view.resources.ic_clock
import com.learnian.core.view.resources.ic_flag
import com.learnian.core.view.resources.ic_notifications
import com.learnian.core.view.resources.ic_volume_up
import com.learnian.core.view.resources.profile_accent_uk
import com.learnian.core.view.resources.profile_accent_us
import com.learnian.core.view.resources.profile_audio_accent_label
import com.learnian.core.view.resources.profile_audio_title
import com.learnian.core.view.resources.profile_daily_goal_current
import com.learnian.core.view.resources.profile_daily_goal_title
import com.learnian.core.view.resources.profile_fsrs_desc
import com.learnian.core.view.resources.profile_fsrs_title
import com.learnian.core.view.resources.profile_reminder_desc
import com.learnian.core.view.resources.profile_reminder_time_night_format
import com.learnian.core.view.resources.profile_reminder_title
import com.learnian.core.view.resources.profile_words_count_format
import org.jetbrains.compose.resources.stringResource

/**
 * Learning and Spaced Repetition Settings Card.
 *
 * Implements:
 * 1. Daily new word goal (Option pills)
 * 2. Automatic FSRS v5 algorithm (ClaySwitch)
 * 3. Audio pronunciation default accent (ClayRadioCard)
 * 4. Daily review reminder time (ClayStatusChip)
 */
@Composable
fun ProfileLearningSettingsCard(
    dailyWordGoal: Int,
    autoFsrsEnabled: Boolean,
    audioAccent: AudioAccent,
    dailyReminderTime: String,
    onDailyWordGoalChange: (Int) -> Unit,
    onAutoFsrsChange: (Boolean) -> Unit,
    onAudioAccentChange: (AudioAccent) -> Unit,
    onDailyReminderClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
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
            // ---------------- 1. Daily Word Goal ----------------
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
                            resource = Res.drawable.ic_flag,
                            contentDescription = null,
                            tint = ClayTheme.colors.primary,
                            modifier = Modifier.size(20.dp),
                        )
                        Text(
                            text = stringResource(Res.string.profile_daily_goal_title),
                            style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = ClayTheme.colors.textPrimary,
                        )
                    }

                    val dailyWordGoalText = if (isRtl) formatLocalizedDigits(dailyWordGoal.toString()) else dailyWordGoal.toString()

                    Text(
                        text = stringResource(Res.string.profile_daily_goal_current, dailyWordGoalText),
                        style = AppTypography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp,
                        ),
                        color = ClayTheme.colors.primary,
                    )
                }

                // 3 Goal Option Sliding Segmented Control (10, 20, 30) - No icons as requested
                ClaySegmentedSlider(
                    selectedValue = dailyWordGoal,
                    onValueChange = onDailyWordGoalChange,
                    segments = listOf(
                        ClaySegment(
                            value = 10,
                            label = stringResource(Res.string.profile_words_count_format, if (isRtl) "۱۰" else "10"),
                        ),
                        ClaySegment(
                            value = 20,
                            label = stringResource(Res.string.profile_words_count_format, if (isRtl) "۲۰" else "20"),
                        ),
                        ClaySegment(
                            value = 30,
                            label = stringResource(Res.string.profile_words_count_format, if (isRtl) "۳۰" else "30"),
                        ),
                    ),
                    height = 46.dp,
                )
            }

            HorizontalDivider(color = dividerColor, thickness = 1.dp)

            // ---------------- 2. Automatic FSRS v5 Algorithm ----------------
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    BaseIcon(
                        resource = Res.drawable.ic_brain_ai,
                        contentDescription = null,
                        tint = Color(0xFF00A86B),
                        modifier = Modifier.size(22.dp),
                    )
                    Column {
                        Text(
                            text = stringResource(Res.string.profile_fsrs_title),
                            style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = ClayTheme.colors.textPrimary,
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = stringResource(Res.string.profile_fsrs_desc),
                            style = AppTypography.bodySmall.copy(
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                            ),
                            color = ClayTheme.colors.textSecondary,
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                ClaySwitch(
                    checked = autoFsrsEnabled,
                    onCheckedChange = onAutoFsrsChange,
                )
            }

            HorizontalDivider(color = dividerColor, thickness = 1.dp)

            // ---------------- 3. Audio Pronunciation & Default Accent ----------------
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
                            resource = Res.drawable.ic_volume_up,
                            contentDescription = null,
                            tint = Color(0xFFB87B16),
                            modifier = Modifier.size(20.dp),
                        )
                        Text(
                            text = stringResource(Res.string.profile_audio_title),
                            style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = ClayTheme.colors.textPrimary,
                        )
                    }

                    Text(
                        text = stringResource(Res.string.profile_audio_accent_label),
                        style = AppTypography.bodySmall,
                        color = ClayTheme.colors.textSecondary,
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    ClayRadioCard(
                        selected = audioAccent == AudioAccent.US,
                        onClick = { onAudioAccentChange(AudioAccent.US) },
                        title = stringResource(Res.string.profile_accent_us),
                        modifier = Modifier.weight(1f),
                    )

                    ClayRadioCard(
                        selected = audioAccent == AudioAccent.UK,
                        onClick = { onAudioAccentChange(AudioAccent.UK) },
                        title = stringResource(Res.string.profile_accent_uk),
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            HorizontalDivider(color = dividerColor, thickness = 1.dp)

            // ---------------- 4. Daily Review Reminder ----------------
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    BaseIcon(
                        resource = Res.drawable.ic_notifications,
                        contentDescription = null,
                        tint = ClayTheme.colors.primary,
                        modifier = Modifier.size(22.dp),
                    )
                    Column {
                        Text(
                            text = stringResource(Res.string.profile_reminder_title),
                            style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = ClayTheme.colors.textPrimary,
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = stringResource(Res.string.profile_reminder_desc),
                            style = AppTypography.bodySmall,
                            color = ClayTheme.colors.textSecondary,
                        )
                    }
                }

                val reminderFormatted = if (isRtl) {
                    formatLocalizedDigits(dailyReminderTime)
                } else {
                    if (dailyReminderTime == "21:30") "9:30" else dailyReminderTime
                }

                ClayStatusChip(
                    text = stringResource(Res.string.profile_reminder_time_night_format, reminderFormatted),
                    icon = {
                        BaseIcon(
                            resource = Res.drawable.ic_clock,
                            contentDescription = null,
                            tint = ClayTheme.colors.primary,
                            modifier = Modifier.size(16.dp),
                        )
                    },
                    iconPosition = ChipIconPosition.Start,
                    style = ClayChipStyle.Resting,
                    textColor = ClayTheme.colors.primary,
                    backgroundColor = ClayTheme.colors.primaryContainer,
                    onClick = onDailyReminderClick,
                )
            }
        }
    }
}

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
