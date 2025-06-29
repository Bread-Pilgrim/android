package com.twolskone.bakeroad.feature.intro.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiState

internal data class IntroUiState(
    val shouldKeepSplashScreen: Boolean = true
) : BaseUiState