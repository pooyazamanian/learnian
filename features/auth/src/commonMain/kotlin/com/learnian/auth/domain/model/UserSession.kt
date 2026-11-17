package com.learnian.auth.domain.model

import kotlinx.serialization.Serializable

/**
 * Domain representation of an authenticated user session.
 */
@Serializable
data class UserSession(
    val userId: String = "",
    val phoneNumber: String = "",
    val isVerified: Boolean = false,
)
