package com.learnian.stats.domain.usecase

import com.learnian.base.BaseNoParamsUseCase
import com.learnian.stats.domain.model.StatsDashboard
import com.learnian.stats.domain.repository.StatsRepository

/**
 * Loads the stats dashboard and guarantees the UI never receives an empty snapshot.
 */
class GetStatsDashboardUseCase(
    private val repository: StatsRepository,
) : BaseNoParamsUseCase<Result<StatsDashboard>> {

    override suspend fun invoke(): Result<StatsDashboard> =
        repository.getDashboard().mapCatching { dashboard ->
            require(dashboard.weeklyActivity.days.isNotEmpty()) {
                "Stats dashboard has no daily activity"
            }
            require(dashboard.mastery.segments.isNotEmpty()) {
                "Stats dashboard has no mastery segments"
            }
            dashboard
        }
}
