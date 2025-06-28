package com.twolskone.bakeroad.feature.splash.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiState

internal data class IntroUiState(
    val isLoading: Boolean = false
) : BaseUiState