package com.learnian.base

/**
 * Marker interface representing the UI state in the MVI architecture.
 * UiState belongs strictly to the presentation layer (ViewModel/MVI).
 */
interface UiState

/**
 * Marker interface representing user actions or UI events in the MVI architecture.
 */
interface UiIntent

/**
 * Marker interface representing one-shot UI side-effects (e.g., navigation, snackbars, toasts).
 */
interface UiEffect

/**
 * Interface representing a loadable MVI UI state with built-in cache retention support.
 *
 * Ensures that if a background refresh or network error occurs, previously loaded data
 * is NOT destroyed ("نپره"), preserving an offline-first, smooth user experience.
 */
interface LoadableUiState<out T> : UiState {
    val isLoading: Boolean
    val data: T?
    val cachedData: T?
    val errorMessage: String?
    val supportsCache: Boolean

    /**
     * Safely retrieves the best available data: returns fresh [data] if present,
     * or falls back to [cachedData] if [supportsCache] is enabled.
     */
    val safeData: T?
        get() = data ?: if (supportsCache) cachedData else null

    /**
     * True if currently loading and no cached data is available (first load).
     */
    val isInitialLoading: Boolean
        get() = isLoading && safeData == null

    /**
     * True if data is already visible on screen and a background reload/refresh is in progress.
     */
    val isRefreshing: Boolean
        get() = isLoading && safeData != null

    /**
     * True if valid data (either fresh or cached) is available for display.
     */
    val hasData: Boolean
        get() = safeData != null
}

/**
 * Representation of generic presentation states (Idle, Loading, Success, Error, Empty)
 * with optional cached data persistence.
 */
sealed interface ScreenState<out T> {
    val dataOrNull: T?
        get() = when (this) {
            is Success -> data
            is Loading -> cachedData
            is Error -> cachedData
            is Idle -> cachedData
            is Empty -> null
        }

    val isLoading: Boolean
        get() = this is Loading

    val isSuccess: Boolean
        get() = this is Success

    val isError: Boolean
        get() = this is Error

    val isEmpty: Boolean
        get() = this is Empty

    val hasData: Boolean
        get() = dataOrNull != null

    /**
     * Initial uninitialized state.
     */
    data class Idle<out T>(val cachedData: T? = null) : ScreenState<T>

    /**
     * Loading state, optionally holding [cachedData] during pull-to-refresh or background sync.
     */
    data class Loading<out T>(val cachedData: T? = null) : ScreenState<T>

    /**
     * Successful state containing fresh data.
     */
    data class Success<out T>(val data: T) : ScreenState<T>

    /**
     * Error state, optionally holding [cachedData] so previous data is not wiped out on network drops.
     */
    data class Error<out T>(
        val throwable: Throwable? = null,
        val message: String? = null,
        val cachedData: T? = null,
    ) : ScreenState<T>

    /**
     * Empty state when data was successfully loaded but contains no items.
     */
    data object Empty : ScreenState<Nothing>
}

// -------------------------------------------------------------------------------------
// Safe UiState & ScreenState Extension Functions
// -------------------------------------------------------------------------------------

/**
 * Safely extracts data from [LoadableUiState], falling back to [defaultValue] if null.
 */
inline fun <T> LoadableUiState<T>.getDataOrDefault(defaultValue: () -> T): T {
    return safeData ?: defaultValue()
}

/**
 * Safely runs [block] when data is available in [LoadableUiState].
 */
inline fun <T> LoadableUiState<T>.onData(block: (T) -> Unit): LoadableUiState<T> {
    safeData?.let(block)
    return this
}

/**
 * Converts a [LoadableUiState] into a reactive [ScreenState].
 */
fun <T> LoadableUiState<T>.toScreenState(): ScreenState<T> {
    val current = safeData
    return when {
        isLoading && current == null -> ScreenState.Loading()
        isLoading && current != null -> ScreenState.Loading(cachedData = current)
        errorMessage != null -> ScreenState.Error(message = errorMessage, cachedData = current)
        current != null -> ScreenState.Success(current)
        else -> ScreenState.Empty
    }
}

/**
 * Safely extracts data from [ScreenState], falling back to [defaultValue] if null.
 */
inline fun <T> ScreenState<T>.getDataOrDefault(defaultValue: () -> T): T {
    return dataOrNull ?: defaultValue()
}

/**
 * Safely runs [block] when data is available in [ScreenState].
 */
inline fun <T> ScreenState<T>.onData(block: (T) -> Unit): ScreenState<T> {
    dataOrNull?.let(block)
    return this
}

/**
 * Transitions this [ScreenState] to [ScreenState.Loading] while retaining [cachedData].
 */
fun <T> ScreenState<T>.toLoading(preserveCache: Boolean = true): ScreenState<T> {
    return ScreenState.Loading(cachedData = if (preserveCache) dataOrNull else null)
}

/**
 * Transitions this [ScreenState] to [ScreenState.Error] while retaining [cachedData].
 */
fun <T> ScreenState<T>.toError(
    message: String?,
    throwable: Throwable? = null,
    preserveCache: Boolean = true,
): ScreenState<T> {
    return ScreenState.Error(
        throwable = throwable,
        message = message,
        cachedData = if (preserveCache) dataOrNull else null,
    )
}

/**
 * Transitions this [ScreenState] to [ScreenState.Success] with new [data].
 */
fun <T> ScreenState<T>.toSuccess(data: T): ScreenState<T> {
    return ScreenState.Success(data)
}
