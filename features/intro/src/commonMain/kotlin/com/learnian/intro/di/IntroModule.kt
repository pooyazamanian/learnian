package com.learnian.intro.di

import com.learnian.intro.data.repository.IntroRepositoryImpl
import com.learnian.intro.domain.repository.IntroRepository
import com.learnian.intro.domain.usecase.CheckIntroCompletedUseCase
import com.learnian.intro.domain.usecase.CompleteIntroUseCase
import com.learnian.intro.domain.usecase.GetIntroSlidesUseCase
import com.learnian.intro.presentation.viewmodel.IntroSharedViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val introModule = module {
    singleOf(::IntroRepositoryImpl) bind IntroRepository::class
    singleOf(::GetIntroSlidesUseCase)
    singleOf(::CompleteIntroUseCase)
    singleOf(::CheckIntroCompletedUseCase)
    viewModelOf(::IntroSharedViewModel)
}
