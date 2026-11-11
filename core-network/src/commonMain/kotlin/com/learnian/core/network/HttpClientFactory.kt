package com.learnian.core.network

import com.learnian.core.network.auth.AuthEvent
import com.learnian.core.network.auth.AuthEventBus
import com.learnian.core.network.auth.TokenRefresher
import com.learnian.core.network.auth.TokenStorage
import com.learnian.core.network.config.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val tokenStorage: TokenStorage,
    private val tokenRefresher: TokenRefresher? = null
) {
    fun create(): HttpClient {
        return HttpClient {
            defaultRequest {
                url(AppConfig.currentBaseUrl)
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                })
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("[KtorClient] $message")
                    }
                }
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val token = tokenStorage.getToken() ?: return@loadTokens null
                        BearerTokens(
                            accessToken = token.accessToken,
                            refreshToken = token.refreshToken ?: ""
                        )
                    }

                    refreshTokens {
                        val currentToken = tokenStorage.getToken() ?: run {
                            AuthEventBus.emit(AuthEvent.Unauthorized)
                            return@refreshTokens null
                        }

                        val refreshedToken = tokenRefresher?.refreshToken(currentToken)
                        if (refreshedToken != null) {
                            tokenStorage.saveToken(refreshedToken)
                            BearerTokens(
                                accessToken = refreshedToken.accessToken,
                                refreshToken = refreshedToken.refreshToken ?: ""
                            )
                        } else {
                            tokenStorage.clearToken()
                            AuthEventBus.emit(AuthEvent.SessionExpired)
                            null
                        }
                    }
                }
            }
        }
    }
}
