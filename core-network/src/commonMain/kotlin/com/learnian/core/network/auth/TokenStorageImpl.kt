package com.learnian.core.network.auth

import com.learnian.core.datastore.SettingsWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class TokenStorageImpl(
    private val settingsRepository: SettingsWrapper,
    private val json: Json = Json { ignoreUnknownKeys = true }
) : TokenStorage {

    companion object {
        private const val KEY_AUTH_TOKEN = "key_auth_token"
    }

    override fun getToken(): AuthToken? {
        val jsonString = settingsRepository.getString(KEY_AUTH_TOKEN) ?: return null
        return runCatching { json.decodeFromString<AuthToken>(jsonString) }.getOrNull()
    }

    override fun saveToken(token: AuthToken) {
        val jsonString = json.encodeToString(token)
        settingsRepository.saveString(KEY_AUTH_TOKEN, jsonString)
    }

    override fun clearToken() {
        settingsRepository.saveString(KEY_AUTH_TOKEN, "")
    }

    override fun observeToken(): Flow<AuthToken?> {
        return settingsRepository.observeString(KEY_AUTH_TOKEN, "").map { jsonString ->
            if (jsonString.isBlank()) null
            else runCatching { json.decodeFromString<AuthToken>(jsonString) }.getOrNull()
        }
    }
}
