package com.learnian.splash.di

import com.learnian.splash.data.repository.SplashRepositoryImpl
import com.learnian.splash.domain.repository.SplashRepository
import com.learnian.splash.domain.usecase.GetSplashConfigUseCase
import com.learnian.splash.presentation.viewmodel.SplashViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val splashModule: Module = module {
    singleOf(::SplashRepositoryImpl) bind SplashRepository::class
    factoryOf(::GetSplashConfigUseCase)
    viewModelOf(::SplashViewModel)
}
