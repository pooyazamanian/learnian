package com.learnian.stats.data.repository

import com.learnian.stats.data.mapper.toDomain
import com.learnian.stats.data.remote.StatsApi
import com.learnian.stats.data.sample.StatsCatalog
import com.learnian.stats.domain.model.StatsDashboard
import com.learnian.stats.domain.repository.StatsRepository

/**
 * Offline-first [StatsRepository].
 *
 * The snapshot is requested from the API first; while the backend is not deployed (or a
 * request fails) the bundled [StatsCatalog] keeps every section of the tab populated.
 */
class StatsRepositoryImpl(
    private val api: StatsApi,
    private val catalog: StatsCatalog,
) : StatsRepository {

    override suspend fun getDashboard(): Result<StatsDashboard> {
        val remote = api.fetchDashboard()
            .getOrNull()
            ?.toDomain()

        return Result.success(remote ?: catalog.dashboard())
    }
}
