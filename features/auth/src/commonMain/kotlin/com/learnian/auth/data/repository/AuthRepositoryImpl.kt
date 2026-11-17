package com.learnian.auth.data.repository

import com.learnian.auth.domain.model.UserSession
import com.learnian.auth.domain.repository.AuthRepository
import com.learnian.base.ResourceKey
import com.learnian.core.network.auth.AuthToken
import com.learnian.core.network.auth.TokenStorage
import kotlinx.coroutines.delay

class AuthRepositoryImpl(
    private val tokenStorage: TokenStorage,
) : AuthRepository {

    override val resourceKey: ResourceKey = ResourceKey("user_session")

    override suspend fun sendOtp(phoneNumber: String): Result<Unit> {
        return runCatching {
            // Simulated network latency while backend endpoints are being finalized
            delay(600)
            println("[AuthRepository] OTP successfully sent to $phoneNumber")
        }
    }

    override suspend fun verifyOtp(phoneNumber: String, code: String): Result<UserSession> {
        return runCatching {
            delay(700)
            if (code.length != 5) {
                throw IllegalArgumentException("کد تایید باید ۵ رقمی باشد.")
            }

            // Save access token and refresh token into TokenStorage
            val mockToken = AuthToken(
                accessToken = "mock_access_token_${phoneNumber}_${code}",
                refreshToken = "mock_refresh_token_${phoneNumber}",
                expiresIn = 3600L,
            )
            tokenStorage.saveToken(mockToken)

            UserSession(
                userId = "usr_${phoneNumber.takeLast(4)}",
                phoneNumber = phoneNumber,
                isVerified = true,
            )
        }
    }

    override suspend fun verifyPassword(phoneNumber: String, password: String): Result<UserSession> {
        return runCatching {
            delay(700)
            if (password.length < 6) {
                throw IllegalArgumentException("رمز عبور باید حداقل ۶ کاراکتر باشد.")
            }

            val mockToken = AuthToken(
                accessToken = "mock_access_token_${phoneNumber}_pwd",
                refreshToken = "mock_refresh_token_${phoneNumber}_pwd",
                expiresIn = 3600L,
            )
            tokenStorage.saveToken(mockToken)

            UserSession(
                userId = "usr_${phoneNumber.takeLast(4)}",
                phoneNumber = phoneNumber,
                isVerified = true,
            )
        }
    }
}
