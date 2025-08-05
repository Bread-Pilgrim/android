package com.twolskone.bakeroad.feature.mybakery.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState

@Immutable
internal data class MyBakeryState(
    val loading: Boolean = false
) : BaseUiState