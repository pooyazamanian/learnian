package com.learnian.auth.presentation.mvi

import com.learnian.auth.domain.model.UserSession
import com.learnian.base.LoadableUiState
import com.learnian.base.UiEffect
import com.learnian.base.UiIntent
import com.learnian.core.ui.component.toast.ClayToastType

/**
 * Visual authentication steps in the multi-step auth funnel.
 */
sealed interface AuthStep {
    data object PhoneInput : AuthStep
    data class OtpVerification(val phoneNumber: String) : AuthStep

    // Prepared for future extension (Password & Email modes)
    data class PasswordInput(val phoneNumber: String) : AuthStep
    data object EmailInput : AuthStep
}

/**
 * Immutable UI State for Authentication, complying with [LoadableUiState].
 */
data class AuthState(
    override val isLoading: Boolean = false,
    override val data: UserSession? = null,
    override val cachedData: UserSession? = null,
    override val errorMessage: String? = null,
    override val supportsCache: Boolean = false,
    override val isRefreshing: Boolean = false,
    val errorText: com.learnian.base.UiText? = null,
    val currentStep: AuthStep = AuthStep.PhoneInput,
    val phoneNumber: String = "",
    val otpCode: String = "",
    val password: String = "",
    val email: String = "",
    val resendSecondsRemaining: Int = 60,
) : LoadableUiState<UserSession> {
    val canResendOtp: Boolean
        get() = resendSecondsRemaining <= 0
}

/**
 * User intents dispatched from the UI to the ViewModel.
 */
sealed interface AuthIntent : UiIntent {
    data class UpdatePhoneNumber(val phone: String) : AuthIntent
    data object SubmitPhone : AuthIntent
    data class UpdateOtp(val otp: String) : AuthIntent
    data object SubmitOtp : AuthIntent
    data object ResendOtp : AuthIntent
    data object ChangePhoneNumber : AuthIntent

    // Prepared for future extension (Password, Email, Google SSO)
    data class UpdatePassword(val password: String) : AuthIntent
    data object SubmitPassword : AuthIntent
    data class UpdateEmail(val email: String) : AuthIntent
    data object SwitchToEmail : AuthIntent
    data object SwitchToPhone : AuthIntent
    data object SignInWithGoogle : AuthIntent
}

/**
 * Single-shot side effects emitted to the UI.
 */
sealed interface AuthEffect : UiEffect {
    data class ShowToast(val message: com.learnian.base.UiText, val type: ClayToastType = ClayToastType.Info) : AuthEffect
    data object NavigateToHome : AuthEffect
}
