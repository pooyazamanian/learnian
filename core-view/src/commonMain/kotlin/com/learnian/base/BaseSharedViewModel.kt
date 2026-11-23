package com.learnian.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * Base Shared ViewModel for multi-step flows, pagers, or coordinated sibling screens.
 *
 * Inherits standard MVI architecture from [BaseViewModel] and extends it with:
 * - Shared multi-subscriber event broadcasting across child screens
 * - Step/Page synchronization
 *
 * @param S The shared UI state type implementing [UiState].
 * @param I The shared UI intent type implementing [UiIntent].
 * @param E The shared UI effect type implementing [UiEffect].
 */
abstract class BaseSharedViewModel<S : UiState, I : UiIntent, E : UiEffect>(
    initialState: S
) : BaseViewModel<S, I, E>(initialState) {

    private val _sharedEvents = MutableSharedFlow<E>(replay = 0)
    val sharedEvents: SharedFlow<E> = _sharedEvents.asSharedFlow()

    /**
     * Broadcasts an event to all active child destinations / subscribers.
     */
    protected fun broadcastEvent(event: E) {
        viewModelScope.launch {
            _sharedEvents.emit(event)
            sendEffect(event)
        }
    }
}
