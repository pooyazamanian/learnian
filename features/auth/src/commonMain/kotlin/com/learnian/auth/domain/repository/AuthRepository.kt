package com.learnian.auth.domain.repository

import com.learnian.auth.domain.model.UserSession
import com.learnian.base.BaseRepository

/**
 * Domain repository contract for Authentication, OTP verification, and Session management.
 */
interface AuthRepository : BaseRepository<UserSession> {
    suspend fun sendOtp(phoneNumber: String): Result<Unit>
    suspend fun verifyOtp(phoneNumber: String, code: String): Result<UserSession>
    suspend fun verifyPassword(phoneNumber: String, password: String): Result<UserSession>
}
