package com.twolskone.bakeroad.feature.onboard

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.domain.usecase.GetPreferenceOptionsUseCase
import com.twolskone.bakeroad.feature.onboard.model.toUiState
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingIntent
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingSideEffect
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPreferenceOptionsUseCase: GetPreferenceOptionsUseCase,
) : BaseViewModel<OnboardingState, OnboardingIntent, OnboardingSideEffect>(savedStateHandle) {

    init {
        launch {
            val options = getPreferenceOptionsUseCase()
            reduce {
                copy(preferenceState = options.toUiState())
            }
        }
    }

    override fun initState(savedStateHandle: SavedStateHandle): OnboardingState {
        return OnboardingState()
    }

    override suspend fun handleIntent(intent: OnboardingIntent) {
        when (intent) {
            is OnboardingIntent.SelectBreadTypeOption -> reduce {
                val options = preferenceState.selectedBreadTypes.run {
                    if (intent.selected) add(intent.option.id) else remove(intent.option.id)
                }
                copy(preferenceState = preferenceState.copy(selectedBreadTypes = options))
            }

            is OnboardingIntent.SelectFlavorOption -> reduce {
                val options = preferenceState.selectedFlavors.run {
                    if (intent.selected) add(intent.option.id) else remove(intent.option.id)
                }
                copy(preferenceState = preferenceState.copy(selectedFlavors = options))
            }

            is OnboardingIntent.SelectBakeryTypeOption -> reduce {
                val options = preferenceState.selectedBakeryTypes.run {
                    if (intent.selected) add(intent.option.id) else remove(intent.option.id)
                }
                copy(preferenceState = preferenceState.copy(selectedBakeryTypes = options))
            }

            is OnboardingIntent.MoveToPage -> reduce {
                copy(preferenceState = preferenceState.copy(page = intent.page))
            }

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