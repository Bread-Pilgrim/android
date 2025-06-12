package com.twolskone.bakeroad.feature.login

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.user.UserApiClient

@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { tokens, error ->
        when {
            (error != null) -> {
//                Log.e(TAG, "로그인 실패", error)
            }

            (tokens != null) -> {
//                Log.i(TAG, "로그인 성공 ${token.accessToken}")
            }
        }
    }
    LoginScreen()
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