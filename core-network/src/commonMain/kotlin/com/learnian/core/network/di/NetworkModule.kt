package com.learnian.core.network.di

import com.learnian.core.network.HttpClientFactory
import com.learnian.core.network.auth.TokenStorage
import com.learnian.core.network.auth.TokenStorageImpl
import com.learnian.core.network.util.KtorClientWrapper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreNetworkModule = module {
    singleOf(::TokenStorageImpl) bind TokenStorage::class
    single { HttpClientFactory(get()).create() }
    singleOf(::KtorClientWrapper)
}
