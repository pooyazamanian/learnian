package com.learnian.core.network.auth

interface TokenRefresher {
    suspend fun refreshToken(currentToken: AuthToken): AuthToken?
}
