package com.learnian.learning.domain.usecase

import com.learnian.base.BaseUseCase
import com.learnian.base.UiText
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.learning_error_quota
import com.learnian.learning.domain.model.LearningValidationException
import com.learnian.learning.domain.model.MAX_DAILY_QUOTA
import com.learnian.learning.domain.model.MIN_DAILY_QUOTA
import com.learnian.learning.domain.model.PackageStudySettings
import com.learnian.learning.domain.repository.LearningRepository

/**
 * Applies one study plan to a package: the first save enrolls it, later saves update
 * its quota / practice mode / active flag. Both cases are the same domain operation,
 * so they share one rule and one server call.
 *
 * The quota is validated against the product bounds and the learner's own daily
 * capacity, so a package can never claim more words than the plan allows.
 */
class SavePackageStudyPlanUseCase(
    private val repository: LearningRepository,
) : BaseUseCase<SavePackageStudyPlanUseCase.Params, Result<Unit>> {

    data class Params(
        val packageId: String,
        val settings: PackageStudySettings,
    )

    override suspend fun invoke(params: Params): Result<Unit> {
        val settings = params.settings
        val maxQuota = minOf(MAX_DAILY_QUOTA, settings.dailyCapacity)
        if (settings.dailyQuota !in MIN_DAILY_QUOTA..maxQuota) {
            return Result.failure(
                LearningValidationException(
                    UiText.Resource(Res.string.learning_error_quota, MIN_DAILY_QUOTA, maxQuota),
                ),
            )
        }
        return repository.saveStudyPlan(packageId = params.packageId, settings = settings)
    }
}