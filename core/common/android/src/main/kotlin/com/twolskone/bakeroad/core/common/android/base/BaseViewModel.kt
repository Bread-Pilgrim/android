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
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<US : BaseUiState, I : BaseUiIntent, SE : BaseUiSideEffect>(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val initialState: US by lazy { initState(savedStateHandle) }

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<US>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SE>()
    val sideEffect: SharedFlow<SE>
        get() = _sideEffect.asSharedFlow()

    protected val ceh = CoroutineExceptionHandler { _, throwable ->
        handleException(cause = throwable)
    }

    protected abstract fun initState(savedStateHandle: SavedStateHandle): US

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

    abstract suspend fun handleIntent(intent: I)

    protected fun reduce(reduce: US.() -> US) {
        _state.update(reduce)
    }

    protected fun postSideEffect(sideEffect: SE) {
        launch {
            _sideEffect.emit(sideEffect)
        }
    }

    abstract fun handleException(cause: Throwable)
}