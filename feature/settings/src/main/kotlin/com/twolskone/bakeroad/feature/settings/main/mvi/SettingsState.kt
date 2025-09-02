package com.twolskone.bakeroad.feature.settings.main.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiState

internal data class SettingsState(
    val dialog: SettingsDialogState = SettingsDialogState.None
) : BaseUiState

internal enum class SettingsDialogState {
    None,                   // 없음
    Logout,                 // 로그아웃
    DeleteAccount,          // 계정탈퇴
    DeleteAccountCompletion // 계정탈퇴 완료
}