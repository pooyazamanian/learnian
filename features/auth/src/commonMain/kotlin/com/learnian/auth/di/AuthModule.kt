package com.learnian.auth.di

import com.learnian.auth.data.repository.AuthRepositoryImpl
import com.learnian.auth.domain.repository.AuthRepository
import com.learnian.auth.domain.usecase.SendOtpUseCase
import com.learnian.auth.domain.usecase.ValidatePhoneNumberUseCase
import com.learnian.auth.domain.usecase.VerifyOtpUseCase
import com.learnian.auth.presentation.viewmodel.AuthViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
    singleOf(::ValidatePhoneNumberUseCase)
    singleOf(::SendOtpUseCase)
    singleOf(::VerifyOtpUseCase)
    viewModelOf(::AuthViewModel)
}
