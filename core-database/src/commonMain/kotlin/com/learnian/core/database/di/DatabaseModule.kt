package com.learnian.core.database.di

import com.learnian.core.database.AppDatabase
import com.learnian.core.database.DatabaseDriverFactory
import org.koin.dsl.module

val coreDatabaseModule = module {
    single { AppDatabase(get<DatabaseDriverFactory>().createDriver()) }
}
