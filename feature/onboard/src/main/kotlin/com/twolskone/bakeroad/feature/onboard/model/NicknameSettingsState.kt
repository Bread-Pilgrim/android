package com.twolskone.bakeroad.feature.onboard.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class NicknameSettingsState(
    val isLoading: Boolean = false,
    val nicknameText: String = "",
    val descriptionId: Int? = null,
    val description: String = ""
)