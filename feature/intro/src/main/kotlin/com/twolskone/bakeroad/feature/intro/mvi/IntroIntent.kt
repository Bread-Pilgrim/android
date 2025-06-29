package com.twolskone.bakeroad.feature.intro.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface IntroIntent : BaseUiIntent {
    data class LoginKakao(val accessToken: String) : IntroIntent
}