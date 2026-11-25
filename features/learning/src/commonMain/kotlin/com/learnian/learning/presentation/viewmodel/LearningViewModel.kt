package com.learnian.learning.presentation.viewmodel

import com.learnian.base.BaseViewModel
import com.learnian.base.UiText
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.learning_error_load
import com.learnian.core.view.resources.learning_level_test_soon
import com.learnian.learning.domain.repository.LearningRepository
import com.learnian.learning.presentation.mapper.toUiMessage
import com.learnian.learning.presentation.mvi.LearningEffect
import com.learnian.learning.presentation.mvi.LearningIntent
import com.learnian.learning.presentation.mvi.LearningState
import kotlinx.coroutines.Job

/**
 * Drives the learning hub: loads the catalog snapshot and keeps the discovery
 * filters (search + category) in the UI state.
 */
class LearningViewModel(
    private val repository: LearningRepository,
) : BaseViewModel<LearningState, LearningIntent, LearningEffect>(LearningState()) {

    private var loadJob: Job? = null

    init {
        dispatch(LearningIntent.Refresh)
    }

    override fun onIntent(intent: LearningIntent) {
        when (intent) {
            LearningIntent.Refresh, LearningIntent.Retry -> loadCatalog()
            is LearningIntent.SearchQueryChanged -> updateState { copy(searchQuery = intent.query) }
            is LearningIntent.CategorySelected -> updateState { copy(category = intent.category) }
            is LearningIntent.PackageClicked -> sendEffect(LearningEffect.OpenPackage(intent.packageId))
            is LearningIntent.PackageSettingsClicked ->
                sendEffect(LearningEffect.OpenPackageSettings(intent.packageId))

            is LearningIntent.LevelClicked -> {
                val level = currentState.catalog?.levels?.firstOrNull { it.band == intent.band }
                    ?: return
                sendEffect(LearningEffect.OpenPackage(level.featuredPackageId))
            }

            LearningIntent.StartStudy -> sendEffect(LearningEffect.StartStudySession)
            LearningIntent.StartLevelTest -> sendEffect(
                LearningEffect.ShowToast(UiText.Resource(Res.string.learning_level_test_soon)),
            )
        }
    }

    private fun loadCatalog() {
        if (loadJob?.isActive == true) return
        loadJob = launchWithHandler {
            updateState { copy(isLoading = true, errorMessage = null) }
            repository.getCatalog().fold(
                onSuccess = { catalog ->
                    updateState {
                        copy(
                            isLoading = false,
                            data = catalog,
                            cachedData = catalog,
                            errorMessage = null,
                        )
                    }
                },
                onFailure = { error ->
                    val message = error.toUiMessage(Res.string.learning_error_load).asStringAsync()
                    updateState { copy(isLoading = false, errorMessage = message) }
                },
            )
        }
    }
}