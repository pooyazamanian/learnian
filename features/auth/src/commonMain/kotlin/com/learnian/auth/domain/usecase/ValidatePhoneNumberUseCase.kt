package com.learnian.auth.domain.usecase

import com.learnian.base.BaseUseCase
import com.learnian.base.UiText
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.auth_error_phone_empty
import com.learnian.core.view.resources.auth_error_phone_invalid_length
import com.learnian.core.view.resources.auth_error_phone_invalid_prefix

sealed interface PhoneValidationResult {
    data class Valid(val normalizedPhone: String) : PhoneValidationResult
    data class Invalid(val error: UiText) : PhoneValidationResult
}

/**
 * Domain UseCase for client-side phone number validation and normalization.
 *
 * Normalizes Persian/Arabic digits, strips whitespace and country code prefixes,
 * and validates the Iranian 09xx structure.
 */
class ValidatePhoneNumberUseCase : BaseUseCase<String, PhoneValidationResult> {

    override suspend fun invoke(params: String): PhoneValidationResult {
        val trimmed = params.trim()
        if (trimmed.isBlank()) {
            return PhoneValidationResult.Invalid(UiText.Resource(Res.string.auth_error_phone_empty))
        }

        // Convert Persian/Arabic digits to Latin digits
        val latinDigits = trimmed.map { ch ->
            when (ch) {
                in '۰'..'۹' -> '0' + (ch - '۰')
                in '٠'..'٩' -> '0' + (ch - '٠')
                else -> ch
            }
        }.joinToString("").filter { it.isDigit() || it == '+' }

        // Strip +98 or 0098
        var cleanNumber = when {
            latinDigits.startsWith("+98") -> latinDigits.removePrefix("+98")
            latinDigits.startsWith("0098") -> latinDigits.removePrefix("0098")
            else -> latinDigits
        }

        // Standardize leading 0
        if (!cleanNumber.startsWith("0") && cleanNumber.startsWith("9")) {
            cleanNumber = "0$cleanNumber"
        }

        if (!cleanNumber.startsWith("09")) {
            return PhoneValidationResult.Invalid(UiText.Resource(Res.string.auth_error_phone_invalid_prefix))
        }

        if (cleanNumber.length != 11) {
            return PhoneValidationResult.Invalid(UiText.Resource(Res.string.auth_error_phone_invalid_length))
        }

        return PhoneValidationResult.Valid(cleanNumber)
    }
}
