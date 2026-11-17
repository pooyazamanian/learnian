package com.learnian.core.network.di

import com.learnian.core.network.HttpClientFactory
import com.learnian.core.network.auth.DefaultTokenRefresher
import com.learnian.core.network.auth.TokenRefresher
import com.learnian.core.network.auth.TokenStorage
import com.learnian.core.network.auth.TokenStorageImpl
import com.learnian.core.network.util.KtorClientWrapper
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreNetworkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            encodeDefaults = true
        }
    }
    single<TokenStorage> { TokenStorageImpl(settingsRepository = get(), json = get()) }
    single<TokenRefresher> { DefaultTokenRefresher(json = get()) }
    single { HttpClientFactory(tokenStorage = get(), tokenRefresher = get()).create() }
    singleOf(::KtorClientWrapper)
}
