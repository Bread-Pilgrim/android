package com.twolskone.bakeroad.feature.splash

import androidx.lifecycle.SavedStateHandle
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.feature.splash.mvi.IntroIntent
import com.twolskone.bakeroad.feature.splash.mvi.IntroSideEffect
import com.twolskone.bakeroad.feature.splash.mvi.IntroUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
internal class IntroViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<IntroUiState, IntroIntent, IntroSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): IntroUiState {
        return IntroUiState()
    }

    override fun handleIntent(intent: IntroIntent) {
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