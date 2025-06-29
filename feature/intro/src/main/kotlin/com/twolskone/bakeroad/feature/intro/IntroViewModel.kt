package com.twolskone.bakeroad.feature.intro

import androidx.lifecycle.SavedStateHandle
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.common.kotlin.network.exception.BakeRoadException
import com.twolskone.bakeroad.core.common.kotlin.network.exception.ClientException
import com.twolskone.bakeroad.core.domain.usecase.LoginUseCase
import com.twolskone.bakeroad.core.domain.usecase.VerifyTokenUseCase
import com.twolskone.bakeroad.feature.intro.mvi.IntroIntent
import com.twolskone.bakeroad.feature.intro.mvi.IntroSideEffect
import com.twolskone.bakeroad.feature.intro.mvi.IntroUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
internal class IntroViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val verifyTokenUseCase: VerifyTokenUseCase,
    private val loginUseCase: LoginUseCase
) : BaseViewModel<IntroUiState, IntroIntent, IntroSideEffect>(savedStateHandle) {

    init {
        launch {
            if (state.value.shouldKeepSplashScreen && verifyTokenUseCase()) {
                postSideEffect(IntroSideEffect.NavigateToHome)
            }
        }
    }

    override fun initState(savedStateHandle: SavedStateHandle): IntroUiState {
        return IntroUiState()
    }

    override suspend fun handleIntent(intent: IntroIntent) {
        when (intent) {
            is IntroIntent.LoginKakao -> {
                val completeLogin = loginUseCase(accessToken = intent.accessToken)
                if (completeLogin) {
                    // TODO. Navigate to Home or Taste
                }
            }
        }
    }

    override fun handleException(cause: Throwable) {
        when (cause) {
            // 카카오 로그인
            is KakaoSdkError -> {
                when (cause) {
                    is ClientError -> {
                        if (cause.reason == ClientErrorCause.Cancelled) Timber.e("카카오 로그인 취소")
                    }

                    is AuthError -> {
                        Timber.e("카카오 로그인 에러 : [${cause.response.error}] ${cause.response.errorDescription} // $cause")
                    }

                    else -> {
                        Timber.e("카카오 로그인 실패 : 알 수 없는 오류 (${cause.msg})")
                    }
                }
            }

            is ClientException -> {
                Timber.e(cause.message)
                when (cause.code) {
                    ClientException.ERROR_CODE_EMPTY_TOKEN -> stopSplashScreen()
                }
            }

            is BakeRoadException -> {
                Timber.e(cause.message)
                when (cause.code) {
                    BakeRoadException.ERROR_CODE_REFRESH_TOKEN_EXPIRED -> stopSplashScreen()
                }
            }
        }
    }

    private fun stopSplashScreen() {
        reduce { copy(shouldKeepSplashScreen = false) }
    }
}