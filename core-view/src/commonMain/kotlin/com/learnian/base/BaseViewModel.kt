package com.learnian.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Reusable generic BaseViewModel for MVI architecture.
 *
 * @param S The UI state type implementing [UiState].
 * @param I The UI intent type implementing [UiIntent].
 * @param E The UI effect type implementing [UiEffect].
 */
abstract class BaseViewModel<S : UiState, I : UiIntent, E : UiEffect>(
    initialState: S
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect: Flow<E> = _effect.receiveAsFlow()

    /**
     * Retrieves the current UI state value.
     */
    protected val currentState: S
        get() = _uiState.value

    /**
     * Dispatches an intent to be handled by the ViewModel.
     */
    fun dispatch(intent: I) {
        onIntent(intent)
    }

    /**
     * Handles incoming intents from the UI layer.
     */
    protected abstract fun onIntent(intent: I)

    /**
     * Updates the UI state atomically using the provided reducer.
     */
    protected fun updateState(reducer: S.() -> S) {
        _uiState.update(reducer)
    }

    /**
     * Emits a one-shot side-effect to the UI layer.
     */
    protected fun sendEffect(effect: E) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    /**
     * Safe coroutine launcher attached to [viewModelScope] with error handling that preserves job cancellation.
     */
    protected fun launchWithHandler(
        onError: ((Throwable) -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch {
            try {
                block()
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (throwable: Throwable) {
                onError?.invoke(throwable)
            }
        }
    }
}
