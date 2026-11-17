package com.learnian.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.learnian.base.LoadableUiState
import com.learnian.base.ScreenState
import com.learnian.core.ui.component.toast.ClayToastHost
import com.learnian.core.ui.component.toast.ClayToastHostState
import com.learnian.core.ui.component.toast.ClayToastType
import com.learnian.core.ui.component.toast.LocalClayToastHost
import com.learnian.core.ui.component.toast.rememberClayToastHostState

/**
 * Reusable BaseScreen providing unified UI infrastructure for Compose screens.
 *
 * Capabilities:
 * - Content rendering with offline-first cache retention (doesn't wipe out on background reload or error).
 * - Full-screen loading indicator for initial data retrieval.
 * - Non-destructive error handling: when cached data is visible, errors are surfaced via [ClayToastHost]
 *   instead of destroying screen content ("نپره").
 * - Full-screen error view with retry action when no cached data exists.
 * - Integrated Claymorphic Custom Toast ([ClayToastHost]) accessible via [LocalClayToastHost].
 * - Pull-to-refresh container.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    errorMessage: String? = null,
    isEmpty: Boolean = false,
    hasContent: Boolean = true,
    toastHostState: ClayToastHostState = rememberClayToastHostState(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onRefresh: (() -> Unit)? = null,
    onRetry: (() -> Unit)? = null,
    loadingContent: @Composable () -> Unit = { BaseLoadingIndicator() },
    errorContent: @Composable (message: String?) -> Unit = { msg -> BaseErrorView(message = msg, onRetry = onRetry) },
    emptyContent: @Composable () -> Unit = { BaseEmptyView() },
    content: @Composable () -> Unit,
) {
    // Surface transient errors as a ClayToast if cached content is already shown
    LaunchedEffect(errorMessage) {
        if (errorMessage != null && hasContent) {
            toastHostState.showToast(message = errorMessage, type = ClayToastType.Error)
        }
    }

    CompositionLocalProvider(LocalClayToastHost provides toastHostState) {
        Box(modifier = modifier.fillMaxSize()) {
            val screenContent: @Composable () -> Unit = {
                when {
                    // Initial load with no cached data: Show loading spinner
                    isLoading && !hasContent -> loadingContent()

                    // Network/load failure with no cached data: Show full-page error view with retry
                    errorMessage != null && !hasContent -> errorContent(errorMessage)

                    // Empty data state
                    isEmpty && !hasContent -> emptyContent()

                    // Render screen content (retains cached data even if isRefreshing or background error occurred)
                    else -> content()
                }
            }

            if (onRefresh != null) {
                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    screenContent()
                }
            } else {
                screenContent()
            }

            // Material Snackbar host at the bottom
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter),
            )

            // Claymorphic 3D Custom Toast host floating at the top
            ClayToastHost(
                hostState = toastHostState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

/**
 * Overload of [BaseScreen] accepting a typed [LoadableUiState] with automatic cache handling.
 */
@Composable
fun <T> BaseScreen(
    state: LoadableUiState<T>,
    modifier: Modifier = Modifier,
    isRefreshing: Boolean = state.isRefreshing,
    toastHostState: ClayToastHostState = rememberClayToastHostState(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onRefresh: (() -> Unit)? = null,
    onRetry: (() -> Unit)? = null,
    loadingContent: @Composable () -> Unit = { BaseLoadingIndicator() },
    errorContent: @Composable (message: String?) -> Unit = { msg -> BaseErrorView(message = msg, onRetry = onRetry) },
    emptyContent: @Composable () -> Unit = { BaseEmptyView() },
    content: @Composable (data: T) -> Unit,
) {
    val activeData = state.safeData

    BaseScreen(
        modifier = modifier,
        isLoading = state.isLoading,
        isRefreshing = isRefreshing,
        errorMessage = state.errorMessage,
        isEmpty = activeData == null && !state.isLoading && state.errorMessage == null,
        hasContent = activeData != null,
        toastHostState = toastHostState,
        snackbarHostState = snackbarHostState,
        onRefresh = onRefresh,
        onRetry = onRetry,
        loadingContent = loadingContent,
        errorContent = errorContent,
        emptyContent = emptyContent,
    ) {
        if (activeData != null) {
            content(activeData)
        }
    }
}

/**
 * Overload of [BaseScreen] accepting a typed [ScreenState] representation.
 */
@Composable
fun <T> BaseScreen(
    screenState: ScreenState<T>,
    modifier: Modifier = Modifier,
    isRefreshing: Boolean = false,
    toastHostState: ClayToastHostState = rememberClayToastHostState(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onRefresh: (() -> Unit)? = null,
    onRetry: (() -> Unit)? = null,
    loadingContent: @Composable () -> Unit = { BaseLoadingIndicator() },
    errorContent: @Composable (message: String?) -> Unit = { msg -> BaseErrorView(message = msg, onRetry = onRetry) },
    emptyContent: @Composable () -> Unit = { BaseEmptyView() },
    content: @Composable (data: T) -> Unit,
) {
    val data = screenState.dataOrNull
    val errorMessage = (screenState as? ScreenState.Error)?.message

    BaseScreen(
        modifier = modifier,
        isLoading = screenState is ScreenState.Loading,
        isRefreshing = isRefreshing,
        errorMessage = errorMessage,
        isEmpty = screenState is ScreenState.Empty,
        hasContent = data != null,
        toastHostState = toastHostState,
        snackbarHostState = snackbarHostState,
        onRefresh = onRefresh,
        onRetry = onRetry,
        loadingContent = loadingContent,
        errorContent = errorContent,
        emptyContent = emptyContent,
    ) {
        if (data != null) {
            content(data)
        }
    }
}
