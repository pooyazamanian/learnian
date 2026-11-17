package com.learnian.auth.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.learnian.auth.presentation.component.OtpVerificationContent
import com.learnian.auth.presentation.component.PasswordInputContent
import com.learnian.auth.presentation.component.PhoneInputContent
import com.learnian.auth.presentation.mvi.AuthEffect
import com.learnian.auth.presentation.mvi.AuthIntent
import com.learnian.auth.presentation.mvi.AuthStep
import com.learnian.auth.presentation.viewmodel.AuthViewModel
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.toast.rememberClayToastHostState
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.ui.base.BaseScreen
import org.koin.compose.viewmodel.koinViewModel

/**
 * Adaptive 3D Claymorphic Authentication Screen matching reference design.
 *
 * Implements MVI state handling with [BaseScreen] and [rememberClayToastHostState].
 * Dynamically adapts between:
 * - Phone Form Factor (< 600dp): Full width edge-to-edge scrollable column with Top Alignment.
 * - Tablet / Desktop / Landscape (>= 600dp): Ergonomic centered 3D ClayCard (max width 440dp).
 */
@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = AppPaddings.ScreenHorizontal,
        vertical = 16.dp,
    ),
    viewModel: AuthViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val toastHostState = rememberClayToastHostState()

    // Listen to ViewModel side-effects
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AuthEffect.ShowToast -> {
                    toastHostState.showToast(effect.message.asStringAsync(), effect.type)
                }
                is AuthEffect.NavigateToHome -> {
                    onAuthSuccess()
                }
            }
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        BaseScreen(
            modifier = modifier
                .fillMaxSize()
                .background(ClayTheme.colors.background),
            isLoading = false,
            isRefreshing = false,
            hasContent = true,
            isEmpty = false,
            toastHostState = toastHostState,
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ClayTheme.colors.background)
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .imePadding(),
                contentAlignment = Alignment.TopCenter,
            ) {
                val isWideScreen = maxWidth >= 600.dp

                val authBody: @Composable () -> Unit = {
                    AnimatedContent(
                        targetState = state.currentStep,
                        transitionSpec = {
                            slideInHorizontally { width -> width / 2 } + fadeIn() togetherWith
                                    slideOutHorizontally { width -> -width / 2 } + fadeOut()
                        },
                        label = "AuthStepTransition",
                    ) { step ->
                        when (step) {
                            is AuthStep.PhoneInput -> {
                                PhoneInputContent(
                                    phoneNumber = state.phoneNumber,
                                    isLoading = state.isLoading,
                                    errorText = state.errorText,
                                    onPhoneNumberChange = { viewModel.dispatch(AuthIntent.UpdatePhoneNumber(it)) },
                                    onSubmit = { viewModel.dispatch(AuthIntent.SubmitPhone) },
                                    onGoogleSsoClick = { viewModel.dispatch(AuthIntent.SignInWithGoogle) },
                                    onGuestLoginClick = onAuthSuccess,
                                    onSwitchToEmailClick = { viewModel.dispatch(AuthIntent.SwitchToEmail) },
                                )
                            }

                            is AuthStep.OtpVerification -> {
                                OtpVerificationContent(
                                    phoneNumber = step.phoneNumber,
                                    otpCode = state.otpCode,
                                    resendSecondsRemaining = state.resendSecondsRemaining,
                                    canResendOtp = state.canResendOtp,
                                    isLoading = state.isLoading,
                                    errorMessage = state.errorMessage,
                                    errorText = state.errorText,
                                    onOtpChange = { viewModel.dispatch(AuthIntent.UpdateOtp(it)) },
                                    onSubmit = { viewModel.dispatch(AuthIntent.SubmitOtp) },
                                    onResendOtp = { viewModel.dispatch(AuthIntent.ResendOtp) },
                                    onChangePhoneClick = { viewModel.dispatch(AuthIntent.ChangePhoneNumber) },
                                    onBackClick = { viewModel.dispatch(AuthIntent.ChangePhoneNumber) },
                                )
                            }

                            is AuthStep.PasswordInput -> {
                                PasswordInputContent(
                                    phoneNumber = step.phoneNumber,
                                    password = state.password,
                                    isLoading = state.isLoading,
                                    errorMessage = state.errorMessage,
                                    onPasswordChange = { viewModel.dispatch(AuthIntent.UpdatePassword(it)) },
                                    onSubmit = { viewModel.dispatch(AuthIntent.SubmitPassword) },
                                    onBackClick = { viewModel.dispatch(AuthIntent.ChangePhoneNumber) },
                                )
                            }

                            is AuthStep.EmailInput -> {
                                PhoneInputContent(
                                    phoneNumber = state.phoneNumber,
                                    isLoading = state.isLoading,
                                    errorText = state.errorText,
                                    onPhoneNumberChange = { viewModel.dispatch(AuthIntent.UpdatePhoneNumber(it)) },
                                    onSubmit = { viewModel.dispatch(AuthIntent.SubmitPhone) },
                                    onGoogleSsoClick = { viewModel.dispatch(AuthIntent.SignInWithGoogle) },
                                    onGuestLoginClick = onAuthSuccess,
                                    onSwitchToEmailClick = null,
                                )
                            }
                        }
                    }
                }

                if (isWideScreen) {
                    // Tablet / Desktop / Landscape Mode: Centered ClayCard with max width
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(AppPaddings.ScreenHorizontal),
                        contentAlignment = Alignment.Center,
                    ) {
                        ClayCard(
                            modifier = Modifier.widthIn(max = 440.dp),
                            backgroundColor = ClayTheme.colors.surface,
                            cornerRadius = AppCorners.XL,
                            padding = AppPaddings.CardPaddingComfortable,
                            shadowColor = ClayTheme.colors.shadowOuter,
                        ) {
                            authBody()
                        }
                    }
                } else {
                    // Mobile Mode: Edge-to-edge scrollable column starting from top
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(contentPadding),
                        contentAlignment = Alignment.TopCenter,
                    ) {
                        authBody()
                    }
                }
            }
        }
    }
}
