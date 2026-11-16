package com.learnian.core.datastore

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.Flow
import org.koin.core.module.Module

@OptIn(ExperimentalSettingsApi::class)
class SettingsWrapper(private val settings: ObservableSettings) {
    private val flowSettings = settings.toFlowSettings()

    fun saveString(key: String, value: String) {
        settings[key] = value
    }

    fun getString(key: String): String? {
        return settings.getStringOrNull(key)
    }

    fun observeString(key: String, defaultValue: String): Flow<String> {
        return flowSettings.getStringFlow(key, defaultValue)
    }

    fun saveInt(key: String, value: Int) {
        settings[key] = value
    }

    fun getInt(key: String): Int? {
        return settings.getIntOrNull(key)
    }

    fun observeInt(key: String, defaultValue: Int): Flow<Int> {
        return flowSettings.getIntFlow(key, defaultValue)
    }

    fun saveBoolean(key: String, value: Boolean) {
        settings[key] = value
    }

    fun getBoolean(key: String): Boolean? {
        return settings.getBooleanOrNull(key)
    }

    fun observeBoolean(key: String, defaultValue: Boolean): Flow<Boolean> {
        return flowSettings.getBooleanFlow(key, defaultValue)
    }

    fun clear() {
        settings.clear()
    }
}

expect val platformSettingsModule: Module
