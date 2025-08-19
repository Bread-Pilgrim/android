package com.twolskone.bakeroad

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.eventbus.MainEventBus
import com.twolskone.bakeroad.mvi.MainIntent
import com.twolskone.bakeroad.mvi.MainSideEffect
import com.twolskone.bakeroad.mvi.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val mainEventBus: MainEventBus
) : BaseViewModel<MainState, MainIntent, MainSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): MainState {
        return MainState()
    }

    override fun handleException(cause: Throwable) {

    }

    override suspend fun handleIntent(intent: MainIntent) {
        when (intent) {
            MainIntent.RefreshHome -> {
                mainEventBus.setHomeRefreshState(value = true)
                postSideEffect(MainSideEffect.NavigateToHome)
            }
        }
    }
}