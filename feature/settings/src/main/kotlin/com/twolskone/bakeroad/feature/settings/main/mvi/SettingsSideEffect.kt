package com.twolskone.bakeroad.feature.settings.main.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiSideEffect

internal sealed interface SettingsSideEffect : BaseUiSideEffect {
    data object NavigateToLogin : SettingsSideEffect
}