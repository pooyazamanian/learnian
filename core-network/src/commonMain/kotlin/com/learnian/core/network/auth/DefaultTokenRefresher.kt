package com.learnian.core.network.auth

import com.learnian.core.network.config.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)

@Serializable
data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String? = null,
    val tokenType: String = "Bearer",
    val expiresIn: Long? = null
)

/**
 * Clean implementation of [TokenRefresher] that communicates with the auth refresh endpoint
 * using a standalone unauthenticated HttpClient to prevent recursive auth interceptor loops.
 */
class DefaultTokenRefresher(
    private val json: Json = Json { ignoreUnknownKeys = true; isLenient = true },
    private val baseUrl: String = AppConfig.currentBaseUrl,
) : TokenRefresher {

    // Dedicated unauthenticated client for token exchange
    private val unauthenticatedClient by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json(json)
            }
        }
    }

    override suspend fun refreshToken(currentToken: AuthToken): AuthToken? {
        val refreshToken = currentToken.refreshToken
        if (refreshToken.isNullOrBlank()) {
            println("[DefaultTokenRefresher] No refresh token available to refresh session.")
            return null
        }

        val url = "$baseUrl/auth/refresh"
        return try {
            val response = unauthenticatedClient.post(url) {
                contentType(ContentType.Application.Json)
                setBody(RefreshTokenRequest(refreshToken = refreshToken))
            }

            if (response.status.isSuccess()) {
                val refreshResult = json.decodeFromString<RefreshTokenResponse>(response.bodyAsText())
                AuthToken(
                    accessToken = refreshResult.accessToken,
                    refreshToken = refreshResult.refreshToken ?: currentToken.refreshToken,
                    tokenType = refreshResult.tokenType,
                    expiresIn = refreshResult.expiresIn,
                )
            } else {
                println("[DefaultTokenRefresher] Refresh token rejected with status: ${response.status.value}")
                null
            }
        } catch (e: Exception) {
            println("[DefaultTokenRefresher] Refresh failed with exception: ${e.message}")
            null
        }
    }
}
