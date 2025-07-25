package com.twolskone.bakeroad.feature.intro.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState

@Immutable
internal data class IntroState(val type: IntroType = IntroType.SPLASH) : BaseUiState

@Immutable
internal enum class IntroType {
    SPLASH, LOGIN
}