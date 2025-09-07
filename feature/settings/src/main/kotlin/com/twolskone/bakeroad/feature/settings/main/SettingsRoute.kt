package com.twolskone.bakeroad.feature.settings.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.core.designsystem.component.popup.BakeRoadAlert
import com.twolskone.bakeroad.core.designsystem.component.popup.PopupButton
import com.twolskone.bakeroad.feature.settings.R
import com.twolskone.bakeroad.feature.settings.main.mvi.SettingsDialogState
import com.twolskone.bakeroad.feature.settings.main.mvi.SettingsIntent
import com.twolskone.bakeroad.feature.settings.main.mvi.SettingsSideEffect

@Composable
internal fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
    goBack: () -> Unit,
    navigateToNotice: () -> Unit,
    navigateToAppInfo: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                SettingsSideEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    BaseComposable(baseViewModel = viewModel) {
        SettingsScreen(
            onBackClick = goBack,
            onNoticeClick = navigateToNotice,
            onAppInfoClick = navigateToAppInfo,
            onLogoutClick = { viewModel.intent(SettingsIntent.ShowLogoutAlert) },
            onDeleteAccountClick = { viewModel.intent(SettingsIntent.ShowDeleteAccountAlert) }
        )

        when (state.dialog) {
            SettingsDialogState.Logout -> {
                BakeRoadAlert(
                    buttonType = PopupButton.SHORT,
                    title = stringResource(id = R.string.feature_settings_alert_title_logout),
                    primaryText = stringResource(id = R.string.feature_settings_logout),
                    secondaryText = stringResource(id = com.twolskone.bakeroad.core.designsystem.R.string.core_designsystem_button_cancel),
                    onDismissRequest = { viewModel.intent(SettingsIntent.DismissAlert) },
                    onPrimaryAction = { viewModel.intent(SettingsIntent.Logout) },
                    onSecondaryAction = { viewModel.intent(SettingsIntent.DismissAlert) }
                )
            }

            SettingsDialogState.DeleteAccount -> {
                BakeRoadAlert(
                    buttonType = PopupButton.SHORT,
                    title = stringResource(id = R.string.feature_settings_alert_title_delete_account),
                    content = stringResource(id = R.string.feature_settings_alert_description_delete_account),
                    primaryText = stringResource(id = R.string.feature_settings_button_delete_account),
                    secondaryText = stringResource(id = com.twolskone.bakeroad.core.designsystem.R.string.core_designsystem_button_cancel),
                    onDismissRequest = { viewModel.intent(SettingsIntent.DismissAlert) },
                    onPrimaryAction = { viewModel.intent(SettingsIntent.DeleteAccount) },
                    onSecondaryAction = { viewModel.intent(SettingsIntent.DismissAlert) }
                )
            }

            SettingsDialogState.DeleteAccountCompletion -> {
                BakeRoadAlert(
                    buttonType = PopupButton.SHORT,
                    content = stringResource(id = R.string.feature_settings_alert_description_delete_account_completion),
                    primaryText = stringResource(id = com.twolskone.bakeroad.core.designsystem.R.string.core_designsystem_button_confirm),
                    properties = DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false
                    ),
                    onDismissRequest = {
                        viewModel.intent(SettingsIntent.DismissAlert)
                        navigateToLogin()
                    },
                    onPrimaryAction = {
                        viewModel.intent(SettingsIntent.DismissAlert)
                        navigateToLogin()
                    }
                )
            }

            SettingsDialogState.None -> {}
        }
    }
}