package com.learnian.core.datastore.repository

import com.learnian.core.datastore.SettingsWrapper
import com.learnian.core.datastore.model.AppFont
import com.learnian.core.datastore.model.AppLanguage
import com.learnian.core.datastore.model.AudioAccent
import com.learnian.core.datastore.model.FontSizeScale
import com.learnian.core.datastore.model.ThemeMode
import com.learnian.core.datastore.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

/**
 * Settings storage keys.
 */
object SettingsKeys {
    const val KEY_THEME_MODE = "user_settings_theme_mode"
    const val KEY_LANGUAGE = "user_settings_language"
    const val KEY_FONT = "user_settings_font"
    const val KEY_FONT_SIZE_SCALE = "user_settings_font_size_scale"
    const val KEY_CUSTOM_THEME_ID = "user_settings_custom_theme_id"
    const val KEY_DAILY_WORD_GOAL = "user_settings_daily_word_goal"
    const val KEY_AUTO_FSRS = "user_settings_auto_fsrs"
    const val KEY_AUDIO_ACCENT = "user_settings_audio_accent"
    const val KEY_DAILY_REMINDER_TIME = "user_settings_daily_reminder_time"
    const val KEY_DAILY_REMINDER_ENABLED = "user_settings_daily_reminder_enabled"
}

/**
 * Interface contract for managing and observing user preference settings.
 */
interface UserSettingsRepository {
    val userSettingsFlow: Flow<UserSettings>

    suspend fun getUserSettings(): UserSettings
    suspend fun setThemeMode(themeMode: ThemeMode)
    suspend fun setLanguage(language: AppLanguage)
    suspend fun setFont(font: AppFont)
    suspend fun setFontSizeScale(scale: FontSizeScale)
    suspend fun setCustomThemeId(themeId: String?)
    suspend fun setDailyWordGoal(goal: Int)
    suspend fun setAutoFsrsEnabled(enabled: Boolean)
    suspend fun setAudioAccent(accent: AudioAccent)
    suspend fun setDailyReminderTime(time: String)
    suspend fun setDailyReminderEnabled(enabled: Boolean)
    suspend fun updateSettings(transform: (UserSettings) -> UserSettings)
}

/**
 * Implementation of [UserSettingsRepository] backed by [SettingsWrapper]
 * (EncryptedSharedPreferences on Android / NSUserDefaults on iOS).
 */
class UserSettingsRepositoryImpl(
    private val appSettings: SettingsWrapper,
) : UserSettingsRepository {

    override val userSettingsFlow: Flow<UserSettings> = combine(
        combine(
            appSettings.observeString(SettingsKeys.KEY_THEME_MODE, ThemeMode.SYSTEM.name),
            appSettings.observeString(SettingsKeys.KEY_LANGUAGE, AppLanguage.FA.code),
            appSettings.observeString(SettingsKeys.KEY_FONT, AppFont.DEFAULT.id),
            appSettings.observeString(SettingsKeys.KEY_FONT_SIZE_SCALE, FontSizeScale.MEDIUM.name),
            appSettings.observeString(SettingsKeys.KEY_CUSTOM_THEME_ID, ""),
        ) { themeStr, langCode, fontId, sizeScaleStr, customTheme ->
            Tuple5(themeStr, langCode, fontId, sizeScaleStr, customTheme)
        },
        combine(
            appSettings.observeInt(SettingsKeys.KEY_DAILY_WORD_GOAL, 20),
            appSettings.observeBoolean(SettingsKeys.KEY_AUTO_FSRS, true),
            appSettings.observeString(SettingsKeys.KEY_AUDIO_ACCENT, AudioAccent.US.code),
            appSettings.observeString(SettingsKeys.KEY_DAILY_REMINDER_TIME, "21:30"),
            appSettings.observeBoolean(SettingsKeys.KEY_DAILY_REMINDER_ENABLED, true),
        ) { wordGoal, fsrs, accentStr, reminderTime, reminderEnabled ->
            Tuple5(wordGoal, fsrs, accentStr, reminderTime, reminderEnabled)
        },
    ) { group1, group2 ->
        UserSettings(
            themeMode = ThemeMode.fromName(group1.a),
            language = AppLanguage.fromCode(group1.b),
            font = AppFont.fromId(group1.c),
            fontSizeScale = FontSizeScale.fromName(group1.d),
            customThemeId = group1.e.ifBlank { null },
            dailyWordGoal = group2.a,
            autoFsrsEnabled = group2.b,
            audioAccent = AudioAccent.fromCode(group2.c),
            dailyReminderTime = group2.d,
            dailyReminderEnabled = group2.e,
        )
    }

    override suspend fun getUserSettings(): UserSettings {
        return userSettingsFlow.first()
    }

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        appSettings.saveString(SettingsKeys.KEY_THEME_MODE, themeMode.name)
    }

    override suspend fun setLanguage(language: AppLanguage) {
        appSettings.saveString(SettingsKeys.KEY_LANGUAGE, language.code)
    }

    override suspend fun setFont(font: AppFont) {
        appSettings.saveString(SettingsKeys.KEY_FONT, font.id)
    }

    override suspend fun setFontSizeScale(scale: FontSizeScale) {
        appSettings.saveString(SettingsKeys.KEY_FONT_SIZE_SCALE, scale.name)
    }

    override suspend fun setCustomThemeId(themeId: String?) {
        appSettings.saveString(SettingsKeys.KEY_CUSTOM_THEME_ID, themeId ?: "")
    }

    override suspend fun setDailyWordGoal(goal: Int) {
        appSettings.saveInt(SettingsKeys.KEY_DAILY_WORD_GOAL, goal)
    }

    override suspend fun setAutoFsrsEnabled(enabled: Boolean) {
        appSettings.saveBoolean(SettingsKeys.KEY_AUTO_FSRS, enabled)
    }

    override suspend fun setAudioAccent(accent: AudioAccent) {
        appSettings.saveString(SettingsKeys.KEY_AUDIO_ACCENT, accent.code)
    }

    override suspend fun setDailyReminderTime(time: String) {
        appSettings.saveString(SettingsKeys.KEY_DAILY_REMINDER_TIME, time)
    }

    override suspend fun setDailyReminderEnabled(enabled: Boolean) {
        appSettings.saveBoolean(SettingsKeys.KEY_DAILY_REMINDER_ENABLED, enabled)
    }

    override suspend fun updateSettings(transform: (UserSettings) -> UserSettings) {
        val current = getUserSettings()
        val updated = transform(current)
        setThemeMode(updated.themeMode)
        setLanguage(updated.language)
        setFont(updated.font)
        setFontSizeScale(updated.fontSizeScale)
        setCustomThemeId(updated.customThemeId)
        setDailyWordGoal(updated.dailyWordGoal)
        setAutoFsrsEnabled(updated.autoFsrsEnabled)
        setAudioAccent(updated.audioAccent)
        setDailyReminderTime(updated.dailyReminderTime)
        setDailyReminderEnabled(updated.dailyReminderEnabled)
    }
}

private data class Tuple5<A, B, C, D, E>(
    val a: A,
    val b: B,
    val c: C,
    val d: D,
    val e: E,
)
