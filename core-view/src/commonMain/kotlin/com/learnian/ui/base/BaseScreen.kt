package ir.isfahan.app.twa.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.learnian.base.ScreenState

/**
 * Reusable BaseScreen providing UI infrastructure for Compose screens.
 *
 * Handles content rendering, loading states, error states, empty states,
 * pull-to-refresh UI interactions, and snackbar host presentation.
 *
 * Does NOT know about Repositories, Database, Network, Caching, Memory Registry, Last-Modified, or HTTP 304.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    errorMessage: String? = null,
    isEmpty: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    onRetry: (() -> Unit)? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    loadingContent: @Composable () -> Unit = { BaseLoadingIndicator() },
    errorContent: @Composable (message: String?) -> Unit = { msg -> BaseErrorView(message = msg, onRetry = onRetry) },
    emptyContent: @Composable () -> Unit = { BaseEmptyView() },
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        val screenContent: @Composable () -> Unit = {
            when {
                isLoading -> loadingContent()
                errorMessage != null -> errorContent(errorMessage)
                isEmpty -> emptyContent()
                else -> content()
            }
        }

        if (onRefresh != null) {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                modifier = Modifier.fillMaxSize()
            ) {
                screenContent()
            }
        } else {
            screenContent()
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
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
    onRefresh: (() -> Unit)? = null,
    onRetry: (() -> Unit)? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    loadingContent: @Composable () -> Unit = { BaseLoadingIndicator() },
    errorContent: @Composable (ScreenState.Error) -> Unit = { error ->
        BaseErrorView(message = error.message ?: error.throwable?.message, onRetry = onRetry)
    },
    emptyContent: @Composable () -> Unit = { BaseEmptyView() },
    content: @Composable (data: T) -> Unit
) {
    BaseScreen(
        modifier = modifier,
        isLoading = screenState is ScreenState.Loading,
        isRefreshing = isRefreshing,
        errorMessage = (screenState as? ScreenState.Error)?.let { it.message ?: it.throwable?.message ?: "An error occurred" },
        isEmpty = screenState is ScreenState.Empty,
        onRefresh = onRefresh,
        onRetry = onRetry,
        snackbarHostState = snackbarHostState,
        loadingContent = loadingContent,
        errorContent = { errorMessage ->
            if (screenState is ScreenState.Error) {
                errorContent(screenState)
            } else {
                BaseErrorView(message = errorMessage, onRetry = onRetry)
            }
        },
        emptyContent = emptyContent
    ) {
        if (screenState is ScreenState.Success) {
            content(screenState.data)
        }
    }
}
