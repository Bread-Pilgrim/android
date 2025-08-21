package com.twolskone.bakeroad.feature.onboard

import android.app.Activity
import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.common.kotlin.extension.orFalse
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.prefer.GetPreferenceOptionsUseCase
import com.twolskone.bakeroad.core.domain.usecase.user.GetPreferencesUseCase
import com.twolskone.bakeroad.core.domain.usecase.user.PatchPreferencesUseCase
import com.twolskone.bakeroad.core.domain.usecase.user.PostOnboardingUseCase
import com.twolskone.bakeroad.core.domain.usecase.user.SetOnboardingStatusUseCase
import com.twolskone.bakeroad.core.exception.BakeRoadError
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.core.model.PreferenceOptionIds
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingIntent
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingSideEffect
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingState
import com.twolskone.bakeroad.feature.onboard.preference.model.copy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import timber.log.Timber

private const val DELAY_START_BAKE_ROAD = 1_500L
private const val IS_EDIT_PREFERENCE = "isEditPreference"

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPreferenceOptionsUseCase: GetPreferenceOptionsUseCase,
    private val postOnboardingUseCase: PostOnboardingUseCase,
    private val setOnboardingStatusUseCase: SetOnboardingStatusUseCase,
    private val getPreferenceUseCase: GetPreferencesUseCase,
    private val patchPreferenceUseCase: PatchPreferencesUseCase
) : BaseViewModel<OnboardingState, OnboardingIntent, OnboardingSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): OnboardingState {
        return OnboardingState()
    }

    val isEditPreference: Boolean = savedStateHandle.get<Boolean>(IS_EDIT_PREFERENCE).orFalse()

    init {
        fetchPreferences()
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

            is OnboardingIntent.MoveToPage -> reduce {
                copy(preferenceOptionsState = preferenceOptionsState.copy(page = intent.page))
            }

            is OnboardingIntent.UpdateNicknameText -> reduce {
                copy(nicknameSettingsState = nicknameSettingsState.copy(nicknameText = intent.text, errorMessage = ""))
            }

            OnboardingIntent.StartBakeRoad -> {
                reduce { copy(isLoading = true) }
                delay(DELAY_START_BAKE_ROAD)
                postOnboardingUseCase(
                    nickname = state.value.nicknameSettingsState.nicknameText,
                    selectedPreferenceOptions = with(state.value.preferenceOptionsState) {
                        PreferenceOptionIds(
                            breadTypes = selectedBreadTypes.toList(),
                            flavors = selectedFlavors.toList(),
                            atmospheres = selectedBakeryTypes.toList()
                        )
                    }
                )
                setOnboardingStatusUseCase(isOnboardingCompleted = true)
                postSideEffect(OnboardingSideEffect.NavigateToMain)
            }

            OnboardingIntent.EditPreferences -> {
                reduce { copy(isLoading = true) }
                val preferenceOptionsState = state.value.preferenceOptionsState
                val result = patchPreferenceUseCase(
                    addPreferences = preferenceOptionsState.addedList,
                    deletePreferences = preferenceOptionsState.deletedList
                )
                if (result) {
                    postSideEffect(OnboardingSideEffect.SetResult(code = Activity.RESULT_OK, withFinish = true))
                }
            }
        }
    }

    override fun handleException(cause: Throwable) {
        Timber.e(cause)
        reduce { copy(isLoading = false) }
        when (cause) {
            is ClientException -> {
                showSnackbar(
                    type = SnackbarType.ERROR,
                    message = cause.message,
                    messageRes = cause.error?.messageRes
                )
            }

            is BakeRoadException -> {
                when (cause.error) {
                    BakeRoadError.DuplicateNickname -> reduce { copy(nicknameSettingsState = nicknameSettingsState.copy(errorMessage = cause.message)) }
                    BakeRoadError.AlreadyOnboarding -> postSideEffect(OnboardingSideEffect.NavigateToMain)
                    else -> showSnackbar(type = SnackbarType.ERROR, message = cause.message)
                }
            }
        }
    }

    private fun fetchPreferences() {
        launch {
            val preferenceOptionsJob = launch {
                val options = getPreferenceOptionsUseCase()
                reduce { copy(preferenceOptionsState = preferenceOptionsState.copy(loading = isEditPreference, preferenceOptions = options)) }
            }
            if (isEditPreference) {
                val selectedPreferencesJob = launch {
                    val preferences = getPreferenceUseCase()
                    reduce {
                        copy(
                            preferenceOptionsState = preferenceOptionsState.copy(
                                selectedBreadTypes = preferences.breadTypes.toPersistentSet(),
                                selectedFlavors = preferences.flavors.toPersistentSet(),
                                selectedBakeryTypes = preferences.atmospheres.toPersistentSet(),
                                originSelectedTypes = preferences
                            )
                        )
                    }
                }
                joinAll(preferenceOptionsJob, selectedPreferencesJob)
                reduce { copy(preferenceOptionsState = preferenceOptionsState.copy(loading = false)) }
            }
        }
    }
}