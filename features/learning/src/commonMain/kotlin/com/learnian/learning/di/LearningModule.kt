package com.learnian.learning.di

import com.learnian.learning.data.remote.DemoLearningApi
import com.learnian.learning.data.remote.LearningApi
import com.learnian.learning.data.repository.LearningRepositoryImpl
import com.learnian.learning.data.sample.LearningSampleCatalog
import com.learnian.learning.domain.repository.LearningRepository
import com.learnian.learning.domain.usecase.SavePackageStudyPlanUseCase
import com.learnian.learning.presentation.viewmodel.LearningViewModel
import com.learnian.learning.presentation.viewmodel.PackageDetailsViewModel
import com.learnian.learning.presentation.viewmodel.PackageSettingsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val learningModule = module {

    singleOf(::LearningSampleCatalog)

    // Fake server until the learning backend is deployed: swap the binding for
    // single<LearningApi> { LearningRemoteApi(client = get()) } and nothing else changes.
    single<LearningApi> { DemoLearningApi(sample = get()) }

    singleOf(::LearningRepositoryImpl) bind LearningRepository::class

    factoryOf(::SavePackageStudyPlanUseCase)

    viewModelOf(::LearningViewModel)
    viewModelOf(::PackageDetailsViewModel)
    viewModelOf(::PackageSettingsViewModel)
}