package com.twolskone.bakeroad.feature.onboard.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.feature.onboard.model.NicknameSettingsState
import com.twolskone.bakeroad.feature.onboard.model.PreferenceOptionsState

@Immutable
internal data class OnboardingState(
    val isLoading: Boolean = false,
    val preferenceOptionsState: PreferenceOptionsState = PreferenceOptionsState(),
    val nicknameSettingsState: NicknameSettingsState = NicknameSettingsState()
) : BaseUiState