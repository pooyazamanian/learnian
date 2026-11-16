package com.learnian.core.datastore.di

import com.learnian.core.datastore.SettingsWrapper
import com.learnian.core.datastore.repository.UserSettingsRepository
import com.learnian.core.datastore.repository.UserSettingsRepositoryImpl
import com.learnian.core.datastore.platformSettingsModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreDataStoreModule = module {
    includes(platformSettingsModule)
    singleOf(::SettingsWrapper)
    single<UserSettingsRepository> { UserSettingsRepositoryImpl(get()) }
}
