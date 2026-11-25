package com.learnian.learning.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.learnian.core.ui.component.toast.rememberClayToastHostState
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.learning_details_title
import com.learnian.core.ui.component.header.ClayScreenHeader
import com.learnian.learning.presentation.component.details.PackageActionBar
import com.learnian.learning.presentation.component.details.PackageHeroCard
import com.learnian.learning.presentation.component.details.PackageMetricsGrid
import com.learnian.learning.presentation.component.details.PackagePlanCard
import com.learnian.learning.presentation.component.details.PackageRatingStrip
import com.learnian.learning.presentation.component.details.PackageTestimonialCard
import com.learnian.learning.presentation.component.details.PackageWhyCard
import com.learnian.learning.presentation.mvi.PackageDetailsEffect
import com.learnian.learning.presentation.mvi.PackageDetailsIntent
import com.learnian.learning.presentation.viewmodel.PackageDetailsViewModel
import com.learnian.ui.base.BaseScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Marketing detail page of a package: what it contains, why it matters and the study
 * plan the learner picks before adding it to their daily plan.
 */
@Composable
fun PackageDetailsScreen(
    packageId: String,
    onBackClick: () -> Unit,
    onOpenSettings: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PackageDetailsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val toastHostState = rememberClayToastHostState()
    val toastScope = rememberCoroutineScope()

    LaunchedEffect(packageId) {
        viewModel.dispatch(PackageDetailsIntent.Load(packageId))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PackageDetailsEffect.OpenSettings -> onOpenSettings(effect.packageId)
                is PackageDetailsEffect.ShowToast -> toastHostState.show(
                    toastScope,
                    effect.message.asStringAsync(),
                    effect.type,
                )
            }
        }
    }

    BaseScreen(
        state = uiState,
        isRefreshing = uiState.isRefreshing,
        toastHostState = toastHostState,
        onRefresh = { viewModel.dispatch(PackageDetailsIntent.Refresh(packageId)) },
        onRetry = { viewModel.dispatch(PackageDetailsIntent.Retry) },
        modifier = modifier.fillMaxSize(),
    ) { details ->
        val packageInfo = details.packageInfo
        Column(modifier = Modifier.fillMaxSize()) {
            ClayScreenHeader(
                title = stringResource(Res.string.learning_details_title),
                onBackClick = onBackClick,
                modifier = Modifier.padding(
                    horizontal = AppPaddings.ScreenHorizontal,
                    vertical = Spacing.Small,
                ),
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = AppPaddings.ScreenHorizontal),
                verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
            ) {
                PackageHeroCard(packageInfo = packageInfo)

                PackageRatingStrip(packageInfo = packageInfo)

                PackageMetricsGrid(packageInfo = packageInfo)

                PackageWhyCard(packageInfo = packageInfo)

                PackagePlanCard(
                    packageInfo = packageInfo,
                    selectedQuota = uiState.selectedQuota,
                    selectedMode = uiState.selectedMode,
                    onQuotaSelected = { quota ->
                        viewModel.dispatch(PackageDetailsIntent.QuotaSelected(quota))
                    },
                    onModeSelected = { mode ->
                        viewModel.dispatch(PackageDetailsIntent.ModeSelected(mode))
                    },
                )

                PackageTestimonialCard(packageInfo = packageInfo)

                Spacer(Modifier.height(Spacing.Tiny))
            }

            PackageActionBar(
                isEnrolled = details.isEnrolled,
                estimatedDays = packageInfo.estimatedDays(uiState.selectedQuota),
                isSubmitting = uiState.isSubmitting,
                onEnroll = { viewModel.dispatch(PackageDetailsIntent.Enroll) },
                onOpenSettings = { viewModel.dispatch(PackageDetailsIntent.OpenSettings) },
                modifier = Modifier.padding(
                    horizontal = AppPaddings.ScreenHorizontal,
                    vertical = Spacing.Small,
                ),
            )
        }
    }
}
