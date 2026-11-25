package com.learnian.learning.presentation.viewmodel

import com.learnian.base.BaseViewModel
import com.learnian.base.UiText
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.learning_details_enrolled_success
import com.learnian.core.view.resources.learning_details_error
import com.learnian.learning.domain.model.MAX_DAILY_QUOTA
import com.learnian.learning.domain.model.MIN_DAILY_QUOTA
import com.learnian.learning.domain.model.PackageStudySettings
import com.learnian.learning.domain.repository.LearningRepository
import com.learnian.learning.domain.usecase.SavePackageStudyPlanUseCase
import com.learnian.learning.presentation.mapper.toUiMessage
import com.learnian.learning.presentation.mvi.PackageDetailsEffect
import com.learnian.learning.presentation.mvi.PackageDetailsIntent
import com.learnian.learning.presentation.mvi.PackageDetailsState
import kotlinx.coroutines.Job

/**
 * Drives the package detail page: loads the package from the catalog, keeps the chosen
 * study plan and sends the enrollment through the plan use case.
 */
class PackageDetailsViewModel(
    private val repository: LearningRepository,
    private val savePackageStudyPlan: SavePackageStudyPlanUseCase,
) : BaseViewModel<PackageDetailsState, PackageDetailsIntent, PackageDetailsEffect>(
    PackageDetailsState(),
) {

    private var loadJob: Job? = null
    private var submitJob: Job? = null

    override fun onIntent(intent: PackageDetailsIntent) {
        when (intent) {
            is PackageDetailsIntent.Load -> load(packageId = intent.packageId, resetPlan = true)
            is PackageDetailsIntent.Refresh -> load(packageId = intent.packageId, resetPlan = false)
            PackageDetailsIntent.Retry -> load(
                packageId = currentState.packageId,
                resetPlan = currentState.data == null,
            )

            is PackageDetailsIntent.QuotaSelected -> {
                val quota = intent.quota.coerceIn(MIN_DAILY_QUOTA, currentState.maxQuota)
                updateState { copy(selectedQuota = quota) }
            }

            is PackageDetailsIntent.ModeSelected -> updateState { copy(selectedMode = intent.mode) }
            PackageDetailsIntent.Enroll -> enroll()
            PackageDetailsIntent.OpenSettings ->
                sendEffect(PackageDetailsEffect.OpenSettings(currentState.packageId))
        }
    }

    private fun load(packageId: String, resetPlan: Boolean) {
        if (packageId.isBlank()) return
        loadJob?.cancel()
        loadJob = launchWithHandler {
            updateState { copy(packageId = packageId, isLoading = true, errorMessage = null) }
            val details = repository.getCatalog().getOrNull()?.detailsOf(packageId)
            if (details == null) {
                val message = UiText.Resource(Res.string.learning_details_error).asStringAsync()
                updateState { copy(isLoading = false, errorMessage = message) }
                return@launchWithHandler
            }
            val enrollment = details.enrollment
            updateState {
                val initialQuota = when {
                    resetPlan && enrollment != null -> enrollment.dailyQuota
                    resetPlan -> details.recommendedPlan.dailyNewWords
                    else -> selectedQuota
                }
                val initialMode = when {
                    resetPlan && enrollment != null -> enrollment.practiceMode
                    resetPlan -> details.recommendedPlan.practiceMode
                    else -> selectedMode
                }
                copy(
                    isLoading = false,
                    data = details,
                    cachedData = details,
                    errorMessage = null,
                    selectedQuota = initialQuota.coerceIn(
                        MIN_DAILY_QUOTA,
                        minOf(MAX_DAILY_QUOTA, details.dailyCapacity),
                    ),
                    selectedMode = initialMode,
                )
            }
        }
    }

    private fun enroll() {
        val details = currentState.data ?: return
        if (currentState.isSubmitting) return
        submitJob?.cancel()
        submitJob = launchWithHandler {
            updateState { copy(isSubmitting = true) }
            val settings = PackageStudySettings(
                dailyQuota = currentState.selectedQuota,
                practiceMode = currentState.selectedMode,
                isActive = true,
                dailyCapacity = details.dailyCapacity,
            )
            savePackageStudyPlan(
                SavePackageStudyPlanUseCase.Params(
                    packageId = details.packageInfo.id,
                    settings = settings,
                ),
            ).fold(
                onSuccess = {
                    updateState { copy(isSubmitting = false) }
                    sendEffect(
                        PackageDetailsEffect.ShowToast(
                            message = UiText.Resource(Res.string.learning_details_enrolled_success),
                            type = ClayToastType.Success,
                        ),
                    )
                    load(packageId = details.packageInfo.id, resetPlan = true)
                },
                onFailure = { error ->
                    updateState { copy(isSubmitting = false) }
                    sendEffect(
                        PackageDetailsEffect.ShowToast(
                            message = error.toUiMessage(Res.string.learning_details_error),
                            type = ClayToastType.Error,
                        ),
                    )
                },
            )
        }
    }
}