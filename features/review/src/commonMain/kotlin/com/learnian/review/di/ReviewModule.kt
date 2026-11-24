package com.learnian.review.di

import com.learnian.review.data.remote.ReviewApi
import com.learnian.review.data.repository.ReviewRepositoryImpl
import com.learnian.review.data.sample.ReviewLibraryCatalog
import com.learnian.review.data.sample.WordCatalog
import com.learnian.review.domain.repository.ReviewRepository
import com.learnian.review.domain.usecase.GetReviewLibraryUseCase
import com.learnian.review.domain.usecase.GetWordDetailUseCase
import com.learnian.review.domain.usecase.RateWordUseCase
import com.learnian.review.domain.usecase.ToggleBookmarkUseCase
import com.learnian.review.presentation.viewmodel.ReviewHubViewModel
import com.learnian.review.presentation.viewmodel.ReviewViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin graph for the review feature.
 *
 * [ReviewApi] consumes the shared [com.learnian.core.network.util.KtorClientWrapper]
 * provided by `:core-network`, so authentication, JSON and logging are reused.
 */
val reviewModule: Module = module {
    singleOf(::WordCatalog)
    singleOf(::ReviewLibraryCatalog)
    singleOf(::ReviewApi)
    singleOf(::ReviewRepositoryImpl) bind ReviewRepository::class

    factoryOf(::GetWordDetailUseCase)
    factoryOf(::RateWordUseCase)
    factoryOf(::ToggleBookmarkUseCase)
    factoryOf(::GetReviewLibraryUseCase)

    viewModelOf(::ReviewHubViewModel)
    viewModelOf(::ReviewViewModel)
}
