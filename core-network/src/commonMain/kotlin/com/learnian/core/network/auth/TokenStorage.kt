package com.learnian.core.network.auth

import kotlinx.coroutines.flow.Flow

interface TokenStorage {
    fun getToken(): AuthToken?
    fun saveToken(token: AuthToken)
    fun clearToken()
    fun observeToken(): Flow<AuthToken?>
}
