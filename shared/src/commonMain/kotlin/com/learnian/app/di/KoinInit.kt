package com.learnian.app.di

import com.learnian.core.database.di.coreDatabaseModule
import com.learnian.core.datastore.di.coreDataStoreModule
import com.learnian.core.network.di.coreNetworkModule
import com.learnian.core.view.di.coreViewModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.mp.KoinPlatform

val appModules: List<Module> = listOf(
    coreNetworkModule,
    coreDatabaseModule,
    coreDataStoreModule,
    coreViewModule
)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    if (KoinPlatform.getKoinOrNull() == null) {
        startKoin {
            appDeclaration()
            modules(appModules)
        }
    }
}
