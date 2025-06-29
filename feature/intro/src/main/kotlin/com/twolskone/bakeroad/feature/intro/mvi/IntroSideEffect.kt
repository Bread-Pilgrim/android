package com.twolskone.bakeroad.feature.intro.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiSideEffect

internal sealed interface IntroSideEffect : BaseUiSideEffect {
    data object NavigateToHome : IntroSideEffect
}