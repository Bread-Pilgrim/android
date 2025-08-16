package com.twolskone.bakeroad.eventbus

import com.twolskone.bakeroad.core.eventbus.MainTabEventBus
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class MainTabEventBusImpl @Inject constructor() : MainTabEventBus {

    private val _homeRefreshEvent = MutableStateFlow(false)
    override val homeRefreshState: StateFlow<Boolean>
        get() = _homeRefreshEvent.asStateFlow()

    override fun setHomeRefreshState(value: Boolean) {
        Timber.i("xxx setRefreshHomeState : $value")
        _homeRefreshEvent.value = value
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
}