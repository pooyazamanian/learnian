package com.learnian.stats.di

import com.learnian.stats.data.remote.StatsApi
import com.learnian.stats.data.repository.StatsRepositoryImpl
import com.learnian.stats.data.sample.StatsCatalog
import com.learnian.stats.domain.repository.StatsRepository
import com.learnian.stats.domain.usecase.GetStatsDashboardUseCase
import com.learnian.stats.presentation.viewmodel.StatsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin graph for the stats feature.
 *
 * [StatsApi] reuses the shared [com.learnian.core.network.util.KtorClientWrapper] from
 * `:core-network`, and [StatsCatalog] is the bundled fallback that keeps every section
 * populated until the backend is deployed.
 */
val statsModule: Module = module {
    singleOf(::StatsCatalog)
    singleOf(::StatsApi)
    singleOf(::StatsRepositoryImpl) bind StatsRepository::class

    factoryOf(::GetStatsDashboardUseCase)

    viewModelOf(::StatsViewModel)
}
