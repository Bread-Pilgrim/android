package com.twolskone.bakeroad.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState

@Immutable
internal data class MainState(
    val dialog: MainDialogState = MainDialogState.None
) : BaseUiState

@Immutable
internal enum class MainDialogState {
    None,
    TokenExpired
}