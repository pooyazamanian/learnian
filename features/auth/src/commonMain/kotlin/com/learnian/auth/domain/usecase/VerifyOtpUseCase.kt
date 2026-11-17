package com.learnian.auth.domain.usecase

import com.learnian.auth.domain.model.UserSession
import com.learnian.auth.domain.repository.AuthRepository
import com.learnian.base.BaseUseCase
import com.learnian.base.UiText
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.auth_error_invalid_otp

data class VerifyOtpParams(
    val phoneNumber: String,
    val otpCode: String,
)

sealed interface VerifyOtpResult {
    data class Success(val session: UserSession) : VerifyOtpResult
    data class Error(val error: UiText) : VerifyOtpResult
}

/**
 * Domain UseCase that validates the OTP code format and verifies it with the backend.
 */
class VerifyOtpUseCase(
    private val authRepository: AuthRepository,
) : BaseUseCase<VerifyOtpParams, VerifyOtpResult> {

    override suspend fun invoke(params: VerifyOtpParams): VerifyOtpResult {
        val trimmedOtp = params.otpCode.trim()
        if (trimmedOtp.length != 5) {
            return VerifyOtpResult.Error(UiText.Resource(Res.string.auth_error_invalid_otp))
        }

        val result = authRepository.verifyOtp(params.phoneNumber, trimmedOtp)
        return result.fold(
            onSuccess = { session -> VerifyOtpResult.Success(session) },
            onFailure = { throwable ->
                VerifyOtpResult.Error(UiText.DynamicString(throwable.message ?: "Verification failed"))
            }
        )
    }
}
