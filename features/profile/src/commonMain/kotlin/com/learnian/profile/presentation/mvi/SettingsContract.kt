package com.learnian.profile.presentation.mvi

import com.learnian.base.UiEffect
import com.learnian.base.UiIntent
import com.learnian.base.UiState
import com.learnian.core.datastore.model.AppFont
import com.learnian.core.datastore.model.AppLanguage
import com.learnian.core.datastore.model.FontSizeScale
import com.learnian.core.datastore.model.ThemeMode
import com.learnian.core.datastore.model.UserSettings

data class SettingsState(
    val userSettings: UserSettings = UserSettings(),
    val isLoading: Boolean = false,
    val infoMessage: String? = null,
) : UiState

sealed interface SettingsIntent : UiIntent {
    data class SetThemeMode(val themeMode: ThemeMode) : SettingsIntent
    data class SetLanguage(val language: AppLanguage) : SettingsIntent
    data class SetFont(val font: AppFont) : SettingsIntent
    data class SetFontSizeScale(val scale: FontSizeScale) : SettingsIntent
    data class SetCustomThemeId(val customThemeId: String?) : SettingsIntent
    data class SetDailyWordGoal(val goal: Int) : SettingsIntent
    data class SetAutoFsrsEnabled(val enabled: Boolean) : SettingsIntent
    data class SetAudioAccent(val accent: com.learnian.core.datastore.model.AudioAccent) : SettingsIntent
    data class SetDailyReminderTime(val time: String) : SettingsIntent
    data class SetDailyReminderEnabled(val enabled: Boolean) : SettingsIntent
}

sealed interface SettingsEffect : UiEffect {
    data class ShowToast(val message: String) : SettingsEffect
}
