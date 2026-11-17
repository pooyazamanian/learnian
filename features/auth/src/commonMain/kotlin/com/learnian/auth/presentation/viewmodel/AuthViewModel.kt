package com.learnian.auth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.learnian.auth.domain.repository.AuthRepository
import com.learnian.auth.domain.usecase.SendOtpResult
import com.learnian.auth.domain.usecase.SendOtpUseCase
import com.learnian.auth.domain.usecase.VerifyOtpParams
import com.learnian.auth.domain.usecase.VerifyOtpResult
import com.learnian.auth.domain.usecase.VerifyOtpUseCase
import com.learnian.auth.presentation.mvi.AuthEffect
import com.learnian.auth.presentation.mvi.AuthIntent
import com.learnian.auth.presentation.mvi.AuthState
import com.learnian.auth.presentation.mvi.AuthStep
import com.learnian.base.BaseViewModel
import com.learnian.base.UiText
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.auth_sso_google
import com.learnian.core.view.resources.auth_success_welcome
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel(
    private val sendOtpUseCase: SendOtpUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
) : BaseViewModel<AuthState, AuthIntent, AuthEffect>(AuthState()) {

    private var countdownJob: Job? = null

    override fun onIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.UpdatePhoneNumber -> {
                val cleaned = intent.phone.filter { it.isDigit() || it == '+' }.take(11)
                updateState { copy(phoneNumber = cleaned, errorText = null, errorMessage = null) }
            }
            is AuthIntent.SubmitPhone -> submitPhoneNumber()
            is AuthIntent.UpdateOtp -> {
                val cleaned = intent.otp.filter { it.isDigit() }.take(5)
                updateState { copy(otpCode = cleaned, errorText = null, errorMessage = null) }
                if (cleaned.length == 5) {
                    submitOtpCode()
                }
            }
            is AuthIntent.SubmitOtp -> submitOtpCode()
            is AuthIntent.ResendOtp -> resendOtp()
            is AuthIntent.ChangePhoneNumber -> {
                countdownJob?.cancel()
                updateState { copy(currentStep = AuthStep.PhoneInput, otpCode = "", errorText = null, errorMessage = null) }
            }
            is AuthIntent.UpdatePassword -> updateState { copy(password = intent.password, errorText = null, errorMessage = null) }
            is AuthIntent.SubmitPassword -> submitPassword()
            is AuthIntent.UpdateEmail -> updateState { copy(email = intent.email, errorText = null, errorMessage = null) }
            is AuthIntent.SwitchToEmail -> updateState { copy(currentStep = AuthStep.EmailInput, errorText = null, errorMessage = null) }
            is AuthIntent.SwitchToPhone -> updateState { copy(currentStep = AuthStep.PhoneInput, errorText = null, errorMessage = null) }
            is AuthIntent.SignInWithGoogle -> {
                sendEffect(AuthEffect.ShowToast(UiText.Resource(Res.string.auth_sso_google), ClayToastType.Info))
            }
        }
    }

    private fun submitPhoneNumber() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, errorText = null, errorMessage = null) }
            when (val result = sendOtpUseCase(currentState.phoneNumber)) {
                is SendOtpResult.Error -> {
                    updateState { copy(isLoading = false, errorText = result.error) }
                    sendEffect(AuthEffect.ShowToast(result.error, ClayToastType.Warning))
                }
                is SendOtpResult.Success -> {
                    updateState {
                        copy(
                            isLoading = false,
                            phoneNumber = result.normalizedPhone,
                            currentStep = AuthStep.OtpVerification(result.normalizedPhone),
                            errorText = null,
                            errorMessage = null,
                        )
                    }
                    startCountdownTimer()
                }
            }
        }
    }

    private fun submitOtpCode() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, errorText = null, errorMessage = null) }
            val params = VerifyOtpParams(
                phoneNumber = currentState.phoneNumber,
                otpCode = currentState.otpCode,
            )
            when (val result = verifyOtpUseCase(params)) {
                is VerifyOtpResult.Error -> {
                    updateState { copy(isLoading = false, errorText = result.error) }
                    sendEffect(AuthEffect.ShowToast(result.error, ClayToastType.Error))
                }
                is VerifyOtpResult.Success -> {
                    countdownJob?.cancel()
                    updateState {
                        copy(
                            isLoading = false,
                            data = result.session,
                            errorText = null,
                            errorMessage = null,
                        )
                    }
                    sendEffect(AuthEffect.ShowToast(UiText.Resource(Res.string.auth_success_welcome), ClayToastType.Success))
                    sendEffect(AuthEffect.NavigateToHome)
                }
            }
        }
    }

    private fun resendOtp() {
        if (!currentState.canResendOtp) return
        viewModelScope.launch {
            updateState { copy(isLoading = true, errorText = null, errorMessage = null) }
            when (val result = sendOtpUseCase(currentState.phoneNumber)) {
                is SendOtpResult.Error -> {
                    updateState { copy(isLoading = false, errorText = result.error) }
                    sendEffect(AuthEffect.ShowToast(result.error, ClayToastType.Error))
                }
                is SendOtpResult.Success -> {
                    updateState { copy(isLoading = false, errorText = null, errorMessage = null) }
                    startCountdownTimer()
                }
            }
        }
    }

    private fun submitPassword() {
        // Prepared for future password flow
    }

    private fun startCountdownTimer() {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            for (sec in 60 downTo 0) {
                updateState { copy(resendSecondsRemaining = sec) }
                delay(1000)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }
}
