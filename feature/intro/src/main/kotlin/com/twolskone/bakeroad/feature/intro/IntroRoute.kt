package com.twolskone.bakeroad.feature.intro

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.user.UserApiClient
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.feature.intro.login.LoginScreen
import com.twolskone.bakeroad.feature.intro.mvi.IntroIntent
import com.twolskone.bakeroad.feature.intro.mvi.IntroSideEffect
import com.twolskone.bakeroad.feature.intro.mvi.IntroType
import timber.log.Timber

@Composable
internal fun IntroRoute(
    viewModel: IntroViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToOnboarding: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { tokens, error ->
        when {
            (error != null) -> viewModel.handleException(cause = error)
            (tokens != null) -> {
                Timber.i("카카오 로그인 성공 : ${tokens.accessToken}")
                viewModel.intent(IntroIntent.LoginKakao(accessToken = tokens.accessToken))
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.sideEffect.collect {
            when (it) {
                IntroSideEffect.NavigateToHome -> navigateToHome()
                IntroSideEffect.NavigateToOnboarding -> navigateToOnboarding()
            }
        }
    }

    BaseComposable(baseViewModel = viewModel) {
        when (state.type) {
            IntroType.SPLASH -> {
                Timber.e("SPLASH")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = BakeRoadTheme.colorScheme.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = com.twolskone.bakeroad.core.designsystem.R.drawable.core_designsystem_ic_logo_splash),
                        contentDescription = "Logo"
                    )
                }
            }

            IntroType.LOGIN -> {
                Timber.e("LOGIN")
                LoginScreen(
                    modifier = Modifier.fillMaxSize(),
                    onKakaoLoginClick = {
                        loginKakao(context = context, callback = kakaoLoginCallback)
                    }
                )
            }
        }
    }
}

private fun loginKakao(context: Context, callback: (OAuthToken?, Throwable?) -> Unit) {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context = context, callback = callback)
    } else {
        UserApiClient.instance.loginWithKakaoAccount(
            context = context,
            prompts = listOf(Prompt.SELECT_ACCOUNT),
            callback = callback
        )
    }
}