package com.learnian.core.datastore.di

import com.learnian.core.datastore.AppSettingsRepository
import com.learnian.core.datastore.platformSettingsModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreDataStoreModule = module {
    includes(platformSettingsModule)
    singleOf(::AppSettingsRepository)
}
