package com.twolskone.bakeroad.feature.mypage.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiState

internal data class MyPageState(
    val loading: Boolean = true,
) : BaseUiState