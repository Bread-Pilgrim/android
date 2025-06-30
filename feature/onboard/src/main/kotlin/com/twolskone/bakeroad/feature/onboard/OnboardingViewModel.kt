package com.twolskone.bakeroad.feature.onboard

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingIntent
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingSideEffect
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<OnboardingState, OnboardingIntent, OnboardingSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): OnboardingState {
        return OnboardingState()
    }

    override suspend fun handleIntent(intent: OnboardingIntent) {
        when (intent) {
            is OnboardingIntent.OnNicknameTextChanged -> validateNickname(text = intent.text)
        }
    }

    override fun handleException(cause: Throwable) {

    }

    private fun validateNickname(text: String) {
        val descriptionId = if (text.length > 8) R.string.feature_onboarding_description_nickname_length else null
        reduce {
            copy(
                nicknameSettingsState = nicknameSettingsState.copy(
                    nicknameText = text,
                    descriptionId = descriptionId
                )
            )
        }
    }
}