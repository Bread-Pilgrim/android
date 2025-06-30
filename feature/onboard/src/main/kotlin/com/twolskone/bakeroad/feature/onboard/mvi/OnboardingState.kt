package com.twolskone.bakeroad.feature.onboard.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState

@Immutable
internal data class OnboardingState(
    val nicknameSettingsState: NicknameSettingsState = NicknameSettingsState()
) : BaseUiState

@Immutable
internal data class NicknameSettingsState(
    val nicknameText: String = "",
    val descriptionId: Int? = null
)
