package com.twolskone.bakeroad.feature.mybakery.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiSideEffect

internal sealed interface MyBakerySideEffect : BaseUiSideEffect {
    data object RefreshBakeries : MyBakerySideEffect
}