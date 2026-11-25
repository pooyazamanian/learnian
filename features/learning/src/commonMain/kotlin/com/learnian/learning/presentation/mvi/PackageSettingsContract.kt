package com.learnian.learning.presentation.mvi

import com.learnian.base.LoadableUiState
import com.learnian.base.UiEffect
import com.learnian.base.UiIntent
import com.learnian.base.UiText
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.learning.domain.model.DEFAULT_DAILY_CAPACITY
import com.learnian.learning.domain.model.DEFAULT_DAILY_QUOTA
import com.learnian.learning.domain.model.LearningPackage
import com.learnian.learning.domain.model.MAX_DAILY_QUOTA
import com.learnian.learning.domain.model.MIN_DAILY_QUOTA
import com.learnian.learning.domain.model.PackageDetails
import com.learnian.learning.domain.model.PracticeMode

/** UI state of the per-package study settings page. */
data class PackageSettingsState(
    override val isLoading: Boolean = true,
    override val data: PackageDetails? = null,
    override val cachedData: PackageDetails? = null,
    override val errorMessage: String? = null,
    override val supportsCache: Boolean = true,
    val packageId: String = "",
    val quota: Int = DEFAULT_DAILY_QUOTA,
    val practiceMode: PracticeMode = PracticeMode.FLASHCARDS,
    val isActive: Boolean = true,
    val savedQuota: Int = DEFAULT_DAILY_QUOTA,
    val savedActive: Boolean = true,
    val isSaving: Boolean = false,
) : LoadableUiState<PackageDetails> {

    val packageInfo: LearningPackage? get() = safeData?.packageInfo

    val dailyCapacity: Int get() = safeData?.dailyCapacity ?: DEFAULT_DAILY_CAPACITY

    val maxQuota: Int get() = minOf(MAX_DAILY_QUOTA, dailyCapacity)

    val learnedWords: Int get() = safeData?.enrollment?.progress?.learnedWords ?: 0

    val totalWords: Int get() = packageInfo?.totalWords ?: 0

    /** Learned share of the package, rounded for display. */
    val learnedPercent: Int
        get() = if (totalWords <= 0) 0 else (learnedWords * 100) / totalWords

    /** Share of the learner's daily capacity claimed by this package. */
    val sharePercent: Int get() = if (dailyCapacity <= 0) 0 else (quota * 100) / dailyCapacity

    val canDecrease: Boolean get() = quota > MIN_DAILY_QUOTA

    val canIncrease: Boolean get() = quota < maxQuota

    val isDirty: Boolean get() = quota != savedQuota || isActive != savedActive

    val canSave: Boolean get() = !isSaving && isDirty
}

/** User intents of the package settings page. */
sealed interface PackageSettingsIntent : UiIntent {
    data class Load(val packageId: String) : PackageSettingsIntent
    data class Refresh(val packageId: String) : PackageSettingsIntent
    data object Retry : PackageSettingsIntent
    data class QuotaSelected(val quota: Int) : PackageSettingsIntent
    data object IncreaseQuota : PackageSettingsIntent
    data object DecreaseQuota : PackageSettingsIntent
    data class ActiveToggled(val isActive: Boolean) : PackageSettingsIntent
    data object Save : PackageSettingsIntent
    data object Reset : PackageSettingsIntent
}

/** One-shot effects of the package settings page. */
sealed interface PackageSettingsEffect : UiEffect {
    data class ShowToast(
        val message: UiText,
        val type: ClayToastType = ClayToastType.Info,
    ) : PackageSettingsEffect
}
