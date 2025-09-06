package com.twolskone.bakeroad.eventbus

import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.core.eventbus.MainEventBus
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class MainEventBusImpl @Inject constructor() : MainEventBus {

    private val _homeRefreshState = MutableStateFlow(false)
    override val homeRefreshState: StateFlow<Boolean>
        get() = _homeRefreshState.asStateFlow()

    override fun setHomeRefreshState(value: Boolean) {
        Timber.i("xxx setHomeRefreshState : $value")
        _homeRefreshState.value = value
    }

    private val _searchRefreshState = MutableStateFlow(false)
    override val searchRefreshState: StateFlow<Boolean>
        get() = _searchRefreshState.asStateFlow()

    override fun setSearchRefreshState(value: Boolean) {
        Timber.i("xxx setSearchRefreshState : $value")
        _searchRefreshState.value = value
    }

    private val _myPageRefreshState = MutableStateFlow(false)
    override val myPageRefreshState: StateFlow<Boolean>
        get() = _myPageRefreshState.asStateFlow()

    override fun setMyPageRefreshState(value: Boolean) {
        Timber.i("xxx setMyPageRefreshState : $value")
        _myPageRefreshState.value = value
    }

    private val _homeReselectEvent = MutableSharedFlow<Unit>(/*extraBufferCapacity = 1*/)
    override val homeReselectEvent: SharedFlow<Unit>
        get() = _homeReselectEvent.asSharedFlow()

    override suspend fun reselectHome() {
        Timber.i("xxx reselectHome")
        _homeReselectEvent.emit(Unit)
    }

    private val _myBakeryReselectEvent = MutableSharedFlow<Unit>()
    override val myBakeryReselectEvent: SharedFlow<Unit>
        get() = _myBakeryReselectEvent.asSharedFlow()

    override suspend fun reselectMyBakery() {
        Timber.i("xxx reselectMyBakery")
        _myBakeryReselectEvent.emit(Unit)
    }

    private val _snackbarEvent = MutableSharedFlow<SnackbarState>()
    override val snackbarEvent: SharedFlow<SnackbarState>
        get() = _snackbarEvent.asSharedFlow()

    override suspend fun showSnackbar(snackbarState: SnackbarState) {
        _snackbarEvent.emit(snackbarState)
    }
}