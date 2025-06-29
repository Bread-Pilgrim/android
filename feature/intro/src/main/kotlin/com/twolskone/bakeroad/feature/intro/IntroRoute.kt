package com.twolskone.bakeroad.feature.intro

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.user.UserApiClient
import com.twolskone.bakeroad.feature.intro.login.LoginScreen
import com.twolskone.bakeroad.feature.intro.mvi.IntroIntent
import timber.log.Timber

@Composable
internal fun IntroRoute(
    viewModel: IntroViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { tokens, error ->
        when {
            (error != null) -> viewModel.handleException(cause = error)
            (tokens != null) -> {
                Timber.i("카카오 로그인 성공 : ${tokens.accessToken}")
                viewModel.intent(IntroIntent.LoginKakao(accessToken = tokens.accessToken))
            }
        }
    }

    LoginScreen(onKakaoLoginClick = { loginKakao(context = context, callback = kakaoLoginCallback) })
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