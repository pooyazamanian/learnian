package com.learnian.core.datastore.model

/**
 * Supported Theme Modes for the application.
 */
enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK;

    companion object {
        fun fromName(name: String?): ThemeMode =
            entries.firstOrNull { it.name.equals(name, ignoreCase = true) } ?: SYSTEM
    }
}

/**
 * Supported Languages and Layout Directions.
 */
enum class AppLanguage(val code: String, val isRtl: Boolean) {
    FA(code = "fa", isRtl = true),
    EN(code = "en", isRtl = false);

    companion object {
        fun fromCode(code: String?): AppLanguage =
            entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: FA
    }
}

/**
 * Supported Font Families.
 */
enum class AppFont(val id: String) {
    DEFAULT(id = "default"),
    VAZIR(id = "vazir"),
    IRANYEKAN(id = "iranyekan");

    companion object {
        fun fromId(id: String?): AppFont =
            entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: DEFAULT
    }
}

/**
 * Font Size Scaling Factors for dynamic typography.
 */
enum class FontSizeScale(val scale: Float) {
    SMALL(scale = 0.88f),
    MEDIUM(scale = 1.0f),
    LARGE(scale = 1.15f);

    companion object {
        fun fromScale(scale: Float): FontSizeScale =
            entries.firstOrNull { it.scale == scale } ?: MEDIUM

        fun fromName(name: String?): FontSizeScale =
            entries.firstOrNull { it.name.equals(name, ignoreCase = true) } ?: MEDIUM
    }
}

/**
 * Audio pronunciation accents.
 */
enum class AudioAccent(val code: String, val titleFa: String, val titleEn: String) {
    US("us", "آمریکایی (US)", "American (US)"),
    UK("uk", "بریتانیایی (UK)", "British (UK)");

    companion object {
        fun fromCode(code: String?): AudioAccent =
            entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: US
    }
}

/**
 * Consolidated User Settings data model.
 *
 * @param themeMode The active theme mode (System, Light, Dark).
 * @param language The active application language (FA, EN).
 * @param font The active font family.
 * @param fontSizeScale The active font size multiplier.
 * @param customThemeId Optional identifier for extensible custom themes.
 * @param dailyWordGoal Daily new word count (e.g. 10, 20, 30).
 * @param autoFsrsEnabled Whether FSRS v5 spaced repetition algorithm is enabled.
 * @param audioAccent Preferred audio pronunciation accent (US / UK).
 * @param dailyReminderTime Daily reminder notification time string (e.g. "21:30").
 * @param dailyReminderEnabled Whether daily review notifications are active.
 */
data class UserSettings(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val language: AppLanguage = AppLanguage.FA,
    val font: AppFont = AppFont.DEFAULT,
    val fontSizeScale: FontSizeScale = FontSizeScale.MEDIUM,
    val customThemeId: String? = null,
    val dailyWordGoal: Int = 20,
    val autoFsrsEnabled: Boolean = true,
    val audioAccent: AudioAccent = AudioAccent.US,
    val dailyReminderTime: String = "21:30",
    val dailyReminderEnabled: Boolean = true,
)
