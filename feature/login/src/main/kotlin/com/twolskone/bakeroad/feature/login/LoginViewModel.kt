package com.twolskone.bakeroad.feature.login

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.feature.login.mvi.LoginIntent
import com.twolskone.bakeroad.feature.login.mvi.LoginSideEffect
import com.twolskone.bakeroad.feature.login.mvi.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<LoginUiState, LoginIntent, LoginSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): LoginUiState {
        return LoginUiState()
    }

    override fun handleIntent(intent: LoginIntent) {
        when (intent) {
            else -> {}
        }
    }

    override fun handleException(cause: Throwable) {

    }
}