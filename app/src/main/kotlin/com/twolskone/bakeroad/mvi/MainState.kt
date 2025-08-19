package com.twolskone.bakeroad.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState

@Immutable
internal data class MainState(
    val loading: Boolean = false
) : BaseUiState