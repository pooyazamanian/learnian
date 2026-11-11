package com.learnian.base

/**
 * Marker interface representing the UI state in the MVI architecture.
 * UiState belongs strictly to the presentation layer (ViewModel/MVI).
 * It is NOT the application cache.
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
 * Representation of generic presentation states (Loading, Success, Error, Empty).
 */
sealed interface ScreenState<out T> {
    data object Loading : ScreenState<Nothing>
    data class Success<out T>(val data: T) : ScreenState<T>
    data class Error(val throwable: Throwable? = null, val message: String? = null) : ScreenState<Nothing>
    data object Empty : ScreenState<Nothing>
}
