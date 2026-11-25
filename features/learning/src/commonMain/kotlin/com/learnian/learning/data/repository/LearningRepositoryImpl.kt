package com.learnian.learning.data.repository

import com.learnian.base.ResourceKey
import com.learnian.learning.data.mapper.toDomain
import com.learnian.learning.data.remote.LearningApi
import com.learnian.learning.data.sample.LearningSampleCatalog
import com.learnian.learning.domain.model.LearningCatalog
import com.learnian.learning.domain.model.PackageStudySettings
import com.learnian.learning.domain.repository.LearningRepository

/**
 * Offline-first [LearningRepository].
 *
 * Reads: the catalog endpoint, falling back to the bundled snapshot while the backend
 * is not deployed (or the request fails), exactly like the other tabs of the app.
 * Writes: forwarded to the API and reported as successful only when it acknowledges
 * the call. A confirmed write is followed by a catalog reload from the caller, so the
 * server remains the only owner of the plan and no device-local copy can drift.
 */
class LearningRepositoryImpl(
    private val api: LearningApi,
    private val sampleCatalog: LearningSampleCatalog,
) : LearningRepository {

    override val resourceKey: ResourceKey = ResourceKey("learning_catalog")

    override suspend fun getCatalog(): Result<LearningCatalog> {
        val remote = api.fetchCatalog().getOrNull()?.toDomain()
        return Result.success(remote ?: sampleCatalog.catalog().toDomain())
    }

    override suspend fun saveStudyPlan(
        packageId: String,
        settings: PackageStudySettings,
    ): Result<Unit> = api.saveStudyPlan(packageId = packageId, settings = settings)

    override suspend fun resetStudyPlan(packageId: String): Result<Unit> =
        api.resetStudyPlan(packageId = packageId)
}