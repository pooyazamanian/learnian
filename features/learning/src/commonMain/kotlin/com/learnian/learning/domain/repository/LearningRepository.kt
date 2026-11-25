package com.learnian.learning.domain.repository

import com.learnian.base.BaseRepository
import com.learnian.learning.domain.model.LearningCatalog
import com.learnian.learning.domain.model.PackageStudySettings

/**
 * Single source of truth for the learning (package) bounded context.
 *
 * The server owns the learner's plan: reads always resolve to a catalog (the bundled
 * snapshot keeps the screens usable offline) and every write is only reported as
 * successful when the API confirms it. There is intentionally no local copy of the
 * settings — a locally "saved" quota would be a lie the backend would overwrite.
 */
interface LearningRepository : BaseRepository<LearningCatalog> {

    /** Loads the hub snapshot (today plan, enrolled packages, discovery catalog). */
    suspend fun getCatalog(): Result<LearningCatalog>

    /** Adds the package to the plan or updates its quota / practice mode / active flag. */
    suspend fun saveStudyPlan(
        packageId: String,
        settings: PackageStudySettings,
    ): Result<Unit>

    /** Restores the package to Learnian's recommended defaults on the server. */
    suspend fun resetStudyPlan(packageId: String): Result<Unit>
}