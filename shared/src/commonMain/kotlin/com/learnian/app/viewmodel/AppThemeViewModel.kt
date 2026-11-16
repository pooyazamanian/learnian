package com.learnian.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnian.core.datastore.UserSettingsRepository
import com.learnian.core.datastore.model.UserSettings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Root Application Theme ViewModel that streams persistent user settings to [AppPlatformTheme].
 */
class AppThemeViewModel(
    userSettingsRepository: UserSettingsRepository,
) : ViewModel() {

    val userSettings: StateFlow<UserSettings> = userSettingsRepository.userSettingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UserSettings(),
        )
}
