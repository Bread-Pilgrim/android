package com.twolskone.bakeroad.feature.settings.main.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface SettingsIntent : BaseUiIntent {
    data object ShowLogoutAlert : SettingsIntent
    data object ShowDeleteAccountAlert : SettingsIntent
    data object ShowDeleteAccountCompletionAlert : SettingsIntent
    data object DismissAlert : SettingsIntent
    data object Logout : SettingsIntent
    data object DeleteAccount : SettingsIntent
}