package com.twolskone.bakeroad.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiSideEffect

internal sealed interface MainSideEffect : BaseUiSideEffect {
    data object NavigateToHome : MainSideEffect
}