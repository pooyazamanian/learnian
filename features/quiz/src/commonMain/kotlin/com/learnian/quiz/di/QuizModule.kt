package com.learnian.quiz.di

import com.learnian.quiz.data.remote.QuizApi
import com.learnian.quiz.data.repository.QuizRepositoryImpl
import com.learnian.quiz.data.sample.QuizCatalog
import com.learnian.quiz.domain.repository.QuizRepository
import com.learnian.quiz.domain.usecase.GetQuizSessionUseCase
import com.learnian.quiz.domain.usecase.SubmitQuizAnswerUseCase
import com.learnian.quiz.presentation.viewmodel.QuizViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin graph for the quiz feature.
 *
 * [QuizApi] reuses the shared [com.learnian.core.network.util.KtorClientWrapper] from
 * `:core-network`, and [QuizCatalog] is the bundled fallback that keeps all five
 * question formats playable until the backend is deployed.
 */
val quizModule: Module = module {
    singleOf(::QuizCatalog)
    singleOf(::QuizApi)
    singleOf(::QuizRepositoryImpl) bind QuizRepository::class

    factoryOf(::GetQuizSessionUseCase)
    factoryOf(::SubmitQuizAnswerUseCase)

    viewModelOf(::QuizViewModel)
}
