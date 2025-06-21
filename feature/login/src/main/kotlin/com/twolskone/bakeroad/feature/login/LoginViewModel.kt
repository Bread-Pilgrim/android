package com.twolskone.bakeroad.feature.login

import androidx.lifecycle.SavedStateHandle
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.feature.login.mvi.LoginIntent
import com.twolskone.bakeroad.feature.login.mvi.LoginSideEffect
import com.twolskone.bakeroad.feature.login.mvi.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

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
        when (cause) {
            // 카카오 로그인
            is KakaoSdkError -> {
                if (cause is ClientError) {
                    if (cause.reason == ClientErrorCause.Cancelled) Timber.e("카카오 로그인 취소")
                } else if (cause is AuthError) {
                    Timber.e("카카오 로그인 에러 : [${cause.response.error}] ${cause.response.errorDescription} // $cause")
                } else {
                    Timber.e("카카오 로그인 실패 : 알 수 없는 오류 (${cause.msg})")
                }
            }
        }
    }
}