package com.learnian.home.di

import com.learnian.home.data.repository.HomeRepositoryImpl
import com.learnian.home.domain.repository.HomeRepository
import com.learnian.home.domain.usecase.GetHomeDashboardUseCase
import com.learnian.home.presentation.viewmodel.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule: Module = module {
    singleOf(::HomeRepositoryImpl) bind HomeRepository::class
    factoryOf(::GetHomeDashboardUseCase)
    viewModelOf(::HomeViewModel)
}
