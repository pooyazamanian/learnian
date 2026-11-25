package com.learnian.learning.presentation.mvi

import com.learnian.base.LoadableUiState
import com.learnian.base.UiEffect
import com.learnian.base.UiIntent
import com.learnian.base.UiText
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.learning.domain.model.CefrBand
import com.learnian.learning.domain.model.LearningCatalog
import com.learnian.learning.domain.model.LearningPackage
import com.learnian.learning.domain.model.PackageCategory
import com.learnian.learning.presentation.mapper.filterPackages

/** UI state of the learning hub (the "learning" bottom tab). */
data class LearningState(
    override val isLoading: Boolean = true,
    override val data: LearningCatalog? = null,
    override val cachedData: LearningCatalog? = null,
    override val errorMessage: String? = null,
    override val supportsCache: Boolean = true,
    val searchQuery: String = "",
    val category: PackageCategory? = null,
) : LoadableUiState<LearningCatalog> {

    val catalog: LearningCatalog? get() = safeData

    /** Packages matching the active search term and category chip. */
    val visiblePackages: List<LearningPackage>
        get() = catalog?.filterPackages(searchQuery, category).orEmpty()

    val hasActiveFilter: Boolean
        get() = searchQuery.isNotBlank() || category != null
}

/** User intents of the learning hub. */
sealed interface LearningIntent : UiIntent {
    data object Refresh : LearningIntent
    data object Retry : LearningIntent
    data class SearchQueryChanged(val query: String) : LearningIntent
    data class CategorySelected(val category: PackageCategory?) : LearningIntent
    data class PackageClicked(val packageId: String) : LearningIntent
    data class PackageSettingsClicked(val packageId: String) : LearningIntent
    data class LevelClicked(val band: CefrBand) : LearningIntent
    data object StartLevelTest : LearningIntent
    data object StartStudy : LearningIntent
}

/** One-shot effects of the learning hub. */
sealed interface LearningEffect : UiEffect {
    data class OpenPackage(val packageId: String) : LearningEffect
    data class OpenPackageSettings(val packageId: String) : LearningEffect
    data object StartStudySession : LearningEffect
    data class ShowToast(
        val message: UiText,
        val type: ClayToastType = ClayToastType.Info,
    ) : LearningEffect
}
