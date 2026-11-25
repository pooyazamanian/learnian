package com.learnian.learning.presentation.viewmodel

import com.learnian.base.BaseViewModel
import com.learnian.base.UiText
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.learning_settings_error
import com.learnian.core.view.resources.learning_settings_reset_done
import com.learnian.core.view.resources.learning_settings_saved
import com.learnian.learning.domain.model.MAX_DAILY_QUOTA
import com.learnian.learning.domain.model.MIN_DAILY_QUOTA
import com.learnian.learning.domain.model.PackageStudySettings
import com.learnian.learning.domain.repository.LearningRepository
import com.learnian.learning.domain.usecase.SavePackageStudyPlanUseCase
import com.learnian.learning.presentation.mapper.toUiMessage
import com.learnian.learning.presentation.mvi.PackageSettingsEffect
import com.learnian.learning.presentation.mvi.PackageSettingsIntent
import com.learnian.learning.presentation.mvi.PackageSettingsState
import kotlinx.coroutines.Job

/**
 * Drives the per-package study settings: daily quota, practice mode and the active flag.
 * Every write goes to the plan endpoint and the screen only shows the new values once
 * the server acknowledges them, so nothing is kept on the device as a fake save.
 */
class PackageSettingsViewModel(
    private val repository: LearningRepository,
    private val savePackageStudyPlan: SavePackageStudyPlanUseCase,
) : BaseViewModel<PackageSettingsState, PackageSettingsIntent, PackageSettingsEffect>(
    PackageSettingsState(),
) {

    private var loadJob: Job? = null
    private var writeJob: Job? = null

    override fun onIntent(intent: PackageSettingsIntent) {
        when (intent) {
            is PackageSettingsIntent.Load -> load(intent.packageId, reset = true)
            is PackageSettingsIntent.Refresh -> load(intent.packageId, reset = false)
            PackageSettingsIntent.Retry -> load(
                packageId = currentState.packageId,
                reset = currentState.data == null,
            )

            is PackageSettingsIntent.QuotaSelected -> updateQuota(intent.quota)
            PackageSettingsIntent.IncreaseQuota -> updateQuota(currentState.quota + 1)
            PackageSettingsIntent.DecreaseQuota -> updateQuota(currentState.quota - 1)
            is PackageSettingsIntent.ActiveToggled ->
                updateState { copy(isActive = intent.isActive) }

            PackageSettingsIntent.Save -> save()
            PackageSettingsIntent.Reset -> reset()
        }
    }

    private fun updateQuota(value: Int) {
        updateState { copy(quota = value.coerceIn(MIN_DAILY_QUOTA, maxQuota)) }
    }

    private fun load(packageId: String, reset: Boolean) {
        if (packageId.isBlank()) return
        loadJob?.cancel()
        loadJob = launchWithHandler {
            updateState { copy(packageId = packageId, isLoading = true, errorMessage = null) }
            val details = repository.getCatalog().getOrNull()?.detailsOf(packageId)
            if (details == null) {
                val message = UiText.Resource(Res.string.learning_settings_error).asStringAsync()
                updateState { copy(isLoading = false, errorMessage = message) }
                return@launchWithHandler
            }
            updateState {
                val settings = details.settings
                copy(
                    isLoading = false,
                    data = details,
                    cachedData = details,
                    errorMessage = null,
                    quota = (if (reset) settings.dailyQuota else quota).coerceIn(
                        MIN_DAILY_QUOTA,
                        minOf(details.dailyCapacity, MAX_DAILY_QUOTA),
                    ),
                    practiceMode = settings.practiceMode,
                    isActive = if (reset) settings.isActive else isActive,
                    savedQuota = settings.dailyQuota,
                    savedActive = settings.isActive,
                )
            }
        }
    }

    private fun save() {
        val details = currentState.data ?: return
        if (currentState.isSaving) return
        writeJob?.cancel()
        writeJob = launchWithHandler {
            updateState { copy(isSaving = true) }
            val settings = PackageStudySettings(
                dailyQuota = currentState.quota,
                practiceMode = currentState.practiceMode,
                isActive = currentState.isActive,
                dailyCapacity = details.dailyCapacity,
            )
            savePackageStudyPlan(
                SavePackageStudyPlanUseCase.Params(
                    packageId = details.packageInfo.id,
                    settings = settings,
                ),
            ).fold(
                onSuccess = {
                    updateState { copy(isSaving = false) }
                    sendEffect(
                        PackageSettingsEffect.ShowToast(
                            message = UiText.Resource(Res.string.learning_settings_saved),
                            type = ClayToastType.Success,
                        ),
                    )
                    load(details.packageInfo.id, reset = true)
                },
                onFailure = { error ->
                    updateState { copy(isSaving = false) }
                    sendEffect(
                        PackageSettingsEffect.ShowToast(
                            message = error.toUiMessage(Res.string.learning_settings_error),
                            type = ClayToastType.Error,
                        ),
                    )
                },
            )
        }
    }

    private fun reset() {
        val details = currentState.data ?: return
        if (currentState.isSaving) return
        writeJob?.cancel()
        writeJob = launchWithHandler {
            updateState { copy(isSaving = true) }
            repository.resetStudyPlan(details.packageInfo.id).fold(
                onSuccess = {
                    updateState { copy(isSaving = false) }
                    sendEffect(
                        PackageSettingsEffect.ShowToast(
                            message = UiText.Resource(Res.string.learning_settings_reset_done),
                            type = ClayToastType.Success,
                        ),
                    )
                    load(details.packageInfo.id, reset = true)
                },
                onFailure = { error ->
                    updateState { copy(isSaving = false) }
                    sendEffect(
                        PackageSettingsEffect.ShowToast(
                            message = error.toUiMessage(Res.string.learning_settings_error),
                            type = ClayToastType.Error,
                        ),
                    )
                },
            )
        }
    }
}