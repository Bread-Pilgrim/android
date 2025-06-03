package com.twolskone.bakeroad.core.common.android.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : BaseUiState, I : BaseUiIntent, SE : BaseUiSideEffect>(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val initialState: S by lazy { initState(savedStateHandle) }

    private val _state = MutableStateFlow(initialState)
    private val _sideEffect = MutableSharedFlow<SE>()

    protected val currentState: S
        get() = _state.value

    protected val ceh = CoroutineExceptionHandler { _, throwable ->
        handleException(cause = throwable)
    }

    protected abstract fun initState(savedStateHandle: SavedStateHandle): S

    protected inline fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        crossinline action: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(
            context = context + ceh,
            start = start
        ) {
            action()
        }
    }

    fun intent(intent: I) {
        launch {
            handleIntent(intent)
        }
    }

    abstract fun handleIntent(intent: I)

    protected fun reduce(reduce: S.() -> S) {
        val newState = currentState.reduce()
        _state.value = newState
    }

    protected fun postSideEffect(sideEffect: SE) {
        launch {
            _sideEffect.emit(sideEffect)
        }
    }

    abstract fun handleException(cause: Throwable)
}