package com.learnian.auth.domain.usecase

import com.learnian.auth.domain.repository.AuthRepository
import com.learnian.base.BaseUseCase
import com.learnian.base.UiText

sealed interface SendOtpResult {
    data class Success(val normalizedPhone: String) : SendOtpResult
    data class Error(val error: UiText) : SendOtpResult
}

/**
 * Domain UseCase that validates the phone number and triggers OTP dispatch.
 */
class SendOtpUseCase(
    private val authRepository: AuthRepository,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
) : BaseUseCase<String, SendOtpResult> {

    override suspend fun invoke(params: String): SendOtpResult {
        return when (val validation = validatePhoneNumberUseCase(params)) {
            is PhoneValidationResult.Invalid -> {
                SendOtpResult.Error(validation.error)
            }
            is PhoneValidationResult.Valid -> {
                val result = authRepository.sendOtp(validation.normalizedPhone)
                result.fold(
                    onSuccess = { SendOtpResult.Success(validation.normalizedPhone) },
                    onFailure = { throwable ->
                        SendOtpResult.Error(UiText.DynamicString(throwable.message ?: "Authentication error"))
                    }
                )
            }
        }
    }
}
