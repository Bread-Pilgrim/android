package com.twolskone.bakeroad.feature.intro.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiState

internal data class IntroUiState(
    val type: IntroType = IntroType.SPLASH
) : BaseUiState

internal enum class IntroType {
    SPLASH, LOGIN
}