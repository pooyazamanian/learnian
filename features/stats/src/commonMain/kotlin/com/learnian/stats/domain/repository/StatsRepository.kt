package com.learnian.stats.domain.repository

import com.learnian.base.BaseRepository
import com.learnian.base.ResourceKey
import com.learnian.stats.domain.model.StatsDashboard

/**
 * Source of the stats dashboard.
 *
 * The implementation is offline-first: it asks the API first and falls back to the
 * bundled catalog while the backend is not deployed yet.
 */
interface StatsRepository : BaseRepository<StatsDashboard> {

    override val resourceKey: ResourceKey
        get() = ResourceKey("stats_dashboard")

    /** Loads the whole dashboard snapshot (remote first, bundled catalog as fallback). */
    suspend fun getDashboard(): Result<StatsDashboard>
}
