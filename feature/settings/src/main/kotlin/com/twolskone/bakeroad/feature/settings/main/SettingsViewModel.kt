package com.twolskone.bakeroad.feature.settings.main

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.auth.LogoutUseCase
import com.twolskone.bakeroad.core.domain.usecase.user.DeleteAccountUseCase
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.feature.settings.main.mvi.SettingsDialogState
import com.twolskone.bakeroad.feature.settings.main.mvi.SettingsIntent
import com.twolskone.bakeroad.feature.settings.main.mvi.SettingsSideEffect
import com.twolskone.bakeroad.feature.settings.main.mvi.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val logoutUseCase: LogoutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : BaseViewModel<SettingsState, SettingsIntent, SettingsSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): SettingsState {
        return SettingsState()
    }

    override fun handleException(cause: Throwable) {
        Timber.e(cause)
        when (cause) {
            is ClientException -> {
                showSnackbar(
                    type = SnackbarType.ERROR,
                    message = cause.message,
                    messageRes = cause.error?.messageRes
                )
            }

            is BakeRoadException -> {
                showSnackbar(type = SnackbarType.ERROR, message = cause.message)
            }
        }
    }

    override suspend fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            SettingsIntent.ShowLogoutAlert -> reduce { copy(dialog = SettingsDialogState.Logout) }

            SettingsIntent.ShowDeleteAccountAlert -> reduce { copy(dialog = SettingsDialogState.DeleteAccount) }

            SettingsIntent.DismissAlert -> reduce { copy(dialog = SettingsDialogState.None) }

            SettingsIntent.Logout -> {
                reduce { copy(dialog = SettingsDialogState.None) }
                val result = logoutUseCase()
                if (result) {
                    postSideEffect(SettingsSideEffect.NavigateToLogin)
                }
            }

            SettingsIntent.DeleteAccount -> {
                reduce { copy(dialog = SettingsDialogState.None) }
                val result = deleteAccountUseCase()
                if (result) {
                    reduce { copy(dialog = SettingsDialogState.DeleteAccountCompletion) }
                }
            }
        }
    }
}