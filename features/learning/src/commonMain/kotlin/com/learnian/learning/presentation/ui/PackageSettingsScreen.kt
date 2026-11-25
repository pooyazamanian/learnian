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
import com.learnian.core.view.resources.learning_settings_title
import com.learnian.core.ui.component.header.ClayScreenHeader
import com.learnian.learning.presentation.component.settings.PackageActiveToggleCard
import com.learnian.learning.presentation.component.settings.PackageQuotaCard
import com.learnian.learning.presentation.component.settings.PackageSettingsActions
import com.learnian.learning.presentation.component.settings.PackageSettingsSummaryCard
import com.learnian.learning.presentation.mvi.PackageSettingsEffect
import com.learnian.learning.presentation.mvi.PackageSettingsIntent
import com.learnian.learning.presentation.viewmodel.PackageSettingsViewModel
import com.learnian.ui.base.BaseScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Per-package study settings: the active flag, the smart daily quota and the exact word
 * counter. Saving forwards the settings to the repository, which persists them after
 * the server confirms the change (best effort until the backend is deployed).
 */
@Composable
fun PackageSettingsScreen(
    packageId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PackageSettingsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val toastHostState = rememberClayToastHostState()
    val toastScope = rememberCoroutineScope()

    LaunchedEffect(packageId) {
        viewModel.dispatch(PackageSettingsIntent.Load(packageId))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PackageSettingsEffect.ShowToast -> toastHostState.show(
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
        onRefresh = { viewModel.dispatch(PackageSettingsIntent.Refresh(packageId)) },
        onRetry = { viewModel.dispatch(PackageSettingsIntent.Retry) },
        modifier = modifier.fillMaxSize(),
    ) { details ->
        Column(modifier = Modifier.fillMaxSize()) {
            ClayScreenHeader(
                title = stringResource(Res.string.learning_settings_title),
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
                PackageSettingsSummaryCard(
                    packageInfo = details.packageInfo,
                    learnedWords = uiState.learnedWords,
                    learnedPercent = uiState.learnedPercent,
                )

                PackageActiveToggleCard(
                    isActive = uiState.isActive,
                    onToggle = { isActive ->
                        viewModel.dispatch(PackageSettingsIntent.ActiveToggled(isActive))
                    },
                )

                PackageQuotaCard(
                    dailyCapacity = uiState.dailyCapacity,
                    quota = uiState.quota,
                    canDecrease = uiState.canDecrease,
                    canIncrease = uiState.canIncrease,
                    onQuotaSelected = { quota ->
                        viewModel.dispatch(PackageSettingsIntent.QuotaSelected(quota))
                    },
                    onIncrease = { viewModel.dispatch(PackageSettingsIntent.IncreaseQuota) },
                    onDecrease = { viewModel.dispatch(PackageSettingsIntent.DecreaseQuota) },
                )

                Spacer(Modifier.height(Spacing.Tiny))
            }

            PackageSettingsActions(
                canSave = uiState.canSave,
                isSaving = uiState.isSaving,
                onSave = { viewModel.dispatch(PackageSettingsIntent.Save) },
                onReset = { viewModel.dispatch(PackageSettingsIntent.Reset) },
                modifier = Modifier.padding(
                    horizontal = AppPaddings.ScreenHorizontal,
                    vertical = Spacing.Small,
                ),
            )
        }
    }
}
