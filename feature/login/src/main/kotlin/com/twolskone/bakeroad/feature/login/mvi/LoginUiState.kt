package com.twolskone.bakeroad.feature.login.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiState

internal data class LoginUiState(
    val isLoading: Boolean = false
) : BaseUiState