package com.learnian.app.di

import com.learnian.app.viewmodel.AppThemeViewModel
import com.learnian.auth.di.authModule
import com.learnian.core.database.di.coreDatabaseModule
import com.learnian.core.datastore.di.coreDataStoreModule
import com.learnian.core.network.di.coreNetworkModule
import com.learnian.home.di.homeModule
import com.learnian.intro.di.introModule
import com.learnian.learning.di.learningModule
import com.learnian.profile.di.profileModule
import com.learnian.quiz.di.quizModule
import com.learnian.review.di.reviewModule
import com.learnian.splash.di.splashModule
import com.learnian.stats.di.statsModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.mp.KoinPlatform

val appModule = module {
    viewModelOf(::AppThemeViewModel)
}

val appModules: List<Module> = listOf(
    coreNetworkModule,
    coreDatabaseModule,
    coreDataStoreModule,
    authModule,
    introModule,
    splashModule,
    homeModule,
    profileModule,
    reviewModule,
    quizModule,
    statsModule,
    learningModule,
    appModule,
)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    if (KoinPlatform.getKoinOrNull() == null) {
        startKoin {
            appDeclaration()
            modules(appModules)
        }
    }
}
