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
import com.learnian.learning.domain.model.PackageDetails
import com.learnian.learning.domain.model.PracticeMode

/** UI state of the package detail (enrollment) page. */
data class PackageDetailsState(
    override val isLoading: Boolean = true,
    override val data: PackageDetails? = null,
    override val cachedData: PackageDetails? = null,
    override val errorMessage: String? = null,
    override val supportsCache: Boolean = true,
    val packageId: String = "",
    val selectedQuota: Int = DEFAULT_DAILY_QUOTA,
    val selectedMode: PracticeMode = PracticeMode.FLASHCARDS,
    val isSubmitting: Boolean = false,
) : LoadableUiState<PackageDetails> {

    val packageInfo: LearningPackage? get() = safeData?.packageInfo

    val isEnrolled: Boolean get() = safeData?.isEnrolled == true

    val dailyCapacity: Int get() = safeData?.dailyCapacity ?: DEFAULT_DAILY_CAPACITY

    val maxQuota: Int get() = minOf(MAX_DAILY_QUOTA, dailyCapacity)

    /** Days needed to finish the package with the currently selected quota. */
    val estimatedDays: Int get() = packageInfo?.estimatedDays(selectedQuota) ?: 0

    val canSubmit: Boolean get() = !isSubmitting && packageInfo != null
}

/** User intents of the package detail page. */
sealed interface PackageDetailsIntent : UiIntent {
    data class Load(val packageId: String) : PackageDetailsIntent
    data class Refresh(val packageId: String) : PackageDetailsIntent
    data object Retry : PackageDetailsIntent
    data class QuotaSelected(val quota: Int) : PackageDetailsIntent
    data class ModeSelected(val mode: PracticeMode) : PackageDetailsIntent
    data object Enroll : PackageDetailsIntent
    data object OpenSettings : PackageDetailsIntent
}

/** One-shot effects of the package detail page. */
sealed interface PackageDetailsEffect : UiEffect {
    data class OpenSettings(val packageId: String) : PackageDetailsEffect
    data class ShowToast(
        val message: UiText,
        val type: ClayToastType = ClayToastType.Info,
    ) : PackageDetailsEffect
}
