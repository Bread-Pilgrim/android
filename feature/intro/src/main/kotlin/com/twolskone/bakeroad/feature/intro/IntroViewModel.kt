package com.twolskone.bakeroad.feature.intro

import androidx.lifecycle.SavedStateHandle
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.domain.usecase.GetOnboardingStatusUseCase
import com.twolskone.bakeroad.core.domain.usecase.LoginUseCase
import com.twolskone.bakeroad.core.domain.usecase.VerifyTokenUseCase
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientError
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.feature.intro.mvi.IntroIntent
import com.twolskone.bakeroad.feature.intro.mvi.IntroSideEffect
import com.twolskone.bakeroad.feature.intro.mvi.IntroType
import com.twolskone.bakeroad.feature.intro.mvi.IntroUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber
import com.kakao.sdk.common.model.ClientError as KakaoClientError

@HiltViewModel
internal class IntroViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val verifyTokenUseCase: VerifyTokenUseCase,
    private val loginUseCase: LoginUseCase,
    private val getOnboardingStatusUseCase: GetOnboardingStatusUseCase
) : BaseViewModel<IntroUiState, IntroIntent, IntroSideEffect>(savedStateHandle) {

    init {
        launch {
            if (verifyTokenUseCase()) {
                // 토큰 유효성 통과 : Home 또는 Onboarding 화면 이동
                navigate(isOnboardingCompleted = getOnboardingStatusUseCase())
            } else {
                // 토큰 유효성 실패 : 로그인 화면
                showLoginScreen()
            }
        }
    }

    override fun initState(savedStateHandle: SavedStateHandle): IntroUiState {
        return IntroUiState()
    }

    override suspend fun handleIntent(intent: IntroIntent) {
        when (intent) {
            is IntroIntent.LoginKakao -> {
                navigate(isOnboardingCompleted = loginUseCase(accessToken = intent.accessToken))
            }
        }
    }

    override fun handleException(cause: Throwable) {
        Timber.e(cause.message)

        when (cause) {
            // 카카오 로그인
            is KakaoSdkError -> {
                when (cause) {
                    is KakaoClientError -> {
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
                when (cause.error) {
                    ClientError.EmptyToken -> showLoginScreen() // 저장된 토큰이 없음 : 로그인 화면
                    else -> {}  // TODO. Alert
                }
            }

            is BakeRoadException -> {
                when (cause.statusCode) {
                    BakeRoadException.STATUS_CODE_INVALID_TOKEN -> showLoginScreen()    // 유효하지 않은 토큰 : 로그인 화면
                    else -> {}  // TODO. Alert
                }
            }
        }
    }

    private fun navigate(isOnboardingCompleted: Boolean) {
        postSideEffect(
            if (isOnboardingCompleted) {
                IntroSideEffect.NavigateToHome
            } else {
                IntroSideEffect.NavigateToOnboarding
            }
        )
    }

    private fun showLoginScreen() {
        reduce { copy(type = IntroType.LOGIN) }
    }
}