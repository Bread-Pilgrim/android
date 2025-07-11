package com.twolskone.bakeroad.feature.onboard.nickname.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class NicknameSettingsState(
    val isLoading: Boolean = false,
    val nicknameText: String = "",
    val errorMessage: String = ""
)