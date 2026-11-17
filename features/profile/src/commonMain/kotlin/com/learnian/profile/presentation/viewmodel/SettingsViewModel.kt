package com.learnian.profile.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.learnian.base.BaseViewModel
import com.learnian.core.datastore.repository.UserSettingsRepository
import com.learnian.profile.presentation.mvi.SettingsEffect
import com.learnian.profile.presentation.mvi.SettingsIntent
import com.learnian.profile.presentation.mvi.SettingsState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingsViewModel(
    private val userSettingsRepository: UserSettingsRepository,
) : BaseViewModel<SettingsState, SettingsIntent, SettingsEffect>(
    initialState = SettingsState()
) {

    init {
        observeSettings()
    }

    private fun observeSettings() {
        userSettingsRepository.userSettingsFlow
            .onEach { settings ->
                updateState { copy(data = settings, cachedData = settings, isLoading = false, isRefreshing = false) }
            }
            .launchIn(viewModelScope)
    }

    override fun onIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.Refresh -> {
                // Settings are reactive from DataStore
                updateState { copy(isRefreshing = false) }
            }
            is SettingsIntent.SetThemeMode -> {
                launchWithHandler {
                    userSettingsRepository.setThemeMode(intent.themeMode)
                }
            }
            is SettingsIntent.SetLanguage -> {
                launchWithHandler {
                    userSettingsRepository.setLanguage(intent.language)
                }
            }
            is SettingsIntent.SetFont -> {
                launchWithHandler {
                    userSettingsRepository.setFont(intent.font)
                }
            }
            is SettingsIntent.SetFontSizeScale -> {
                launchWithHandler {
                    userSettingsRepository.setFontSizeScale(intent.scale)
                }
            }
            is SettingsIntent.SetCustomThemeId -> {
                launchWithHandler {
                    userSettingsRepository.setCustomThemeId(intent.customThemeId)
                }
            }
            is SettingsIntent.SetDailyWordGoal -> {
                launchWithHandler {
                    userSettingsRepository.setDailyWordGoal(intent.goal)
                }
            }
            is SettingsIntent.SetAutoFsrsEnabled -> {
                launchWithHandler {
                    userSettingsRepository.setAutoFsrsEnabled(intent.enabled)
                }
            }
            is SettingsIntent.SetAudioAccent -> {
                launchWithHandler {
                    userSettingsRepository.setAudioAccent(intent.accent)
                }
            }
            is SettingsIntent.SetDailyReminderTime -> {
                launchWithHandler {
                    userSettingsRepository.setDailyReminderTime(intent.time)
                }
            }
            is SettingsIntent.SetDailyReminderEnabled -> {
                launchWithHandler {
                    userSettingsRepository.setDailyReminderEnabled(intent.enabled)
                }
            }
        }
    }
}
