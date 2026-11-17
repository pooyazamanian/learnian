package com.learnian.profile.presentation.mvi

import com.learnian.base.LoadableUiState
import com.learnian.base.UiEffect
import com.learnian.base.UiIntent
import com.learnian.core.datastore.model.AppFont
import com.learnian.core.datastore.model.AppLanguage
import com.learnian.core.datastore.model.FontSizeScale
import com.learnian.core.datastore.model.ThemeMode
import com.learnian.core.datastore.model.UserSettings

data class SettingsState(
    override val isLoading: Boolean = false,
    override val data: UserSettings? = UserSettings(),
    override val cachedData: UserSettings? = null,
    override val errorMessage: String? = null,
    override val supportsCache: Boolean = true,
    override val isRefreshing: Boolean = false,
    val infoMessage: String? = null,
) : LoadableUiState<UserSettings> {
    val userSettings: UserSettings
        get() = safeData ?: UserSettings()
}

sealed interface SettingsIntent : UiIntent {
    data object Refresh : SettingsIntent
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
