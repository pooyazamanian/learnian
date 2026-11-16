package com.learnian.core.datastore.di

import com.learnian.core.datastore.AppSettingsRepository
import com.learnian.core.datastore.UserSettingsRepository
import com.learnian.core.datastore.UserSettingsRepositoryImpl
import com.learnian.core.datastore.platformSettingsModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreDataStoreModule = module {
    includes(platformSettingsModule)
    singleOf(::AppSettingsRepository)
    single<UserSettingsRepository> { UserSettingsRepositoryImpl(get()) }
}
