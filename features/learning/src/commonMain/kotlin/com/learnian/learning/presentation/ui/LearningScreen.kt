package com.learnian.learning.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.learnian.learning.presentation.component.hub.LearningCefrLevelSection
import com.learnian.learning.presentation.component.hub.LearningDiscoverySection
import com.learnian.learning.presentation.component.hub.LearningMyPackagesSection
import com.learnian.learning.presentation.component.hub.LearningTodayPlanCard
import com.learnian.learning.presentation.mvi.LearningEffect
import com.learnian.learning.presentation.mvi.LearningIntent
import com.learnian.learning.presentation.viewmodel.LearningViewModel
import com.learnian.ui.base.BaseScreen
import org.koin.compose.viewmodel.koinViewModel

/**
 * Learning tab: today's plan, the packages already in progress, discovery and the
 * CEFR shortcuts.
 *
 * The global top bar and bottom navigation belong to the host scaffold, so this
 * screen only renders inside the content inset it receives. Opening a package or
 * its settings is delegated to the navigation callbacks.
 */
@Composable
fun LearningScreen(
    onOpenPackage: (String) -> Unit,
    onOpenPackageSettings: (String) -> Unit,
    onStartStudy: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = AppPaddings.ScreenHorizontal,
        vertical = AppPaddings.ScreenVertical,
    ),
    viewModel: LearningViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val toastHostState = rememberClayToastHostState()
    val toastScope = rememberCoroutineScope()

    // Returning from a package detail or its settings can change the plan, so re-read.
    LaunchedEffect(Unit) {
        viewModel.dispatch(LearningIntent.Refresh)
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LearningEffect.OpenPackage -> onOpenPackage(effect.packageId)
                is LearningEffect.OpenPackageSettings -> onOpenPackageSettings(effect.packageId)
                LearningEffect.StartStudySession -> onStartStudy()
                is LearningEffect.ShowToast -> toastHostState.show(
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
        onRefresh = { viewModel.dispatch(LearningIntent.Refresh) },
        onRetry = { viewModel.dispatch(LearningIntent.Retry) },
        modifier = modifier.fillMaxSize(),
    ) { catalog ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.Section),
        ) {
            LearningTodayPlanCard(
                plan = catalog.todayPlan,
                onContinue = { viewModel.dispatch(LearningIntent.StartStudy) },
            )

            if (catalog.myPackages.isNotEmpty()) {
                LearningMyPackagesSection(
                    packages = catalog.myPackages,
                    activeCount = catalog.activeCount,
                    onPackageClick = { packageId ->
                        viewModel.dispatch(LearningIntent.PackageClicked(packageId))
                    },
                    onSettingsClick = { packageId ->
                        viewModel.dispatch(LearningIntent.PackageSettingsClicked(packageId))
                    },
                )
            }

            LearningDiscoverySection(
                state = uiState,
                onSearchChange = { query ->
                    viewModel.dispatch(LearningIntent.SearchQueryChanged(query))
                },
                onCategorySelected = { category ->
                    viewModel.dispatch(LearningIntent.CategorySelected(category))
                },
                onPackageClick = { packageId ->
                    viewModel.dispatch(LearningIntent.PackageClicked(packageId))
                },
            )

            LearningCefrLevelSection(
                levels = catalog.levels,
                onLevelClick = { band -> viewModel.dispatch(LearningIntent.LevelClicked(band)) },
                onStartTest = { viewModel.dispatch(LearningIntent.StartLevelTest) },
            )

            Spacer(Modifier.height(Spacing.Small))
        }
    }
}
