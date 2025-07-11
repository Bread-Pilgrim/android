package com.twolskone.bakeroad.feature.onboard

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.common.kotlin.network.exception.BakeRoadException
import com.twolskone.bakeroad.core.domain.usecase.GetPreferenceOptionsUseCase
import com.twolskone.bakeroad.core.domain.usecase.SetOnboardingStatusUseCase
import com.twolskone.bakeroad.core.domain.usecase.SetOnboardingUseCase
import com.twolskone.bakeroad.core.model.SelectedPreferenceOptions
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingIntent
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingSideEffect
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingState
import com.twolskone.bakeroad.feature.onboard.preference.model.copy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import timber.log.Timber

private const val DELAY_START_BAKE_ROAD = 1_500L

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPreferenceOptionsUseCase: GetPreferenceOptionsUseCase,
    private val setOnboardingUseCase: SetOnboardingUseCase,
    private val setOnboardingStatusUseCase: SetOnboardingStatusUseCase
) : BaseViewModel<OnboardingState, OnboardingIntent, OnboardingSideEffect>(savedStateHandle) {

    init {
        launch {
            val options = getPreferenceOptionsUseCase()
            reduce { copy(preferenceOptionsState = preferenceOptionsState.copy(preferenceOptions = options)) }
        }
    }

    override fun initState(savedStateHandle: SavedStateHandle): OnboardingState {
        return OnboardingState()
    }

    override suspend fun handleIntent(intent: OnboardingIntent) {
        when (intent) {
            is OnboardingIntent.SelectBreadTypeOption -> reduce {
                val options = preferenceOptionsState.selectedBreadTypes.run {
                    if (intent.selected) add(intent.option.id) else remove(intent.option.id)
                }
                copy(preferenceOptionsState = preferenceOptionsState.copy(selectedBreadTypes = options))
            }

            is OnboardingIntent.SelectFlavorOption -> reduce {
                val options = preferenceOptionsState.selectedFlavors.run {
                    if (intent.selected) add(intent.option.id) else remove(intent.option.id)
                }
                copy(preferenceOptionsState = preferenceOptionsState.copy(selectedFlavors = options))
            }

            is OnboardingIntent.SelectBakeryTypeOption -> reduce {
                val options = preferenceOptionsState.selectedBakeryTypes.run {
                    if (intent.selected) add(intent.option.id) else remove(intent.option.id)
                }
                copy(preferenceOptionsState = preferenceOptionsState.copy(selectedBakeryTypes = options))
            }

            is OnboardingIntent.SelectCommercialAreaOption -> reduce {
                val options = preferenceOptionsState.selectedCommercialAreas.run {
                    if (intent.selected) add(intent.option.id) else remove(intent.option.id)
                }
                copy(preferenceOptionsState = preferenceOptionsState.copy(selectedCommercialAreas = options))
            }

            is OnboardingIntent.MoveToPage -> reduce {
                copy(preferenceOptionsState = preferenceOptionsState.copy(page = intent.page))
            }

            is OnboardingIntent.UpdateNicknameText -> reduce {
                copy(nicknameSettingsState = nicknameSettingsState.copy(nicknameText = intent.text, errorMessage = ""))
            }

            OnboardingIntent.StartBakeRoad -> {
                reduce { copy(isLoading = true) }
                delay(DELAY_START_BAKE_ROAD)
                setOnboardingUseCase(
                    nickname = state.value.nicknameSettingsState.nicknameText,
                    selectedPreferenceOptions = with(state.value.preferenceOptionsState) {
                        SelectedPreferenceOptions(
                            breadTypes = selectedBreadTypes.toList(),
                            flavors = selectedFlavors.toList(),
                            atmospheres = selectedBakeryTypes.toList(),
                            commercialAreas = selectedCommercialAreas.toList()
                        )
                    }
                )
                setOnboardingStatusUseCase(isOnboardingCompleted = true)
                // TODO. Navigation.
            }
        }
    }

    override fun handleException(cause: Throwable) {
        Timber.e(cause)
        reduce { copy(isLoading = false) }
        when (cause) {
            is BakeRoadException -> {
                when (cause.code) {
                    // 닉네임 중복.
                    BakeRoadException.ERROR_CODE_DUPLICATE_ENTRY -> {
                        reduce { copy(nicknameSettingsState = nicknameSettingsState.copy(errorMessage = cause.message)) }
                    }
                }
            }
        }
    }
}