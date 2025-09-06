package com.twolskone.bakeroad.feature.onboard.preference

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.core.designsystem.component.loading.BakeRoadLoadingScreen
import com.twolskone.bakeroad.core.designsystem.component.loading.LoadingType
import com.twolskone.bakeroad.core.designsystem.component.popup.BakeRoadAlert
import com.twolskone.bakeroad.core.designsystem.component.popup.PopupButton
import com.twolskone.bakeroad.core.model.PreferenceOptionType
import com.twolskone.bakeroad.feature.onboard.OnboardingViewModel
import com.twolskone.bakeroad.feature.onboard.R
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingIntent
import timber.log.Timber

@Composable
internal fun PreferenceOptionsRoute(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
    navigateToNicknameSettings: () -> Unit,
    finish: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val preferenceOptionsState = state.preferenceOptionsState
    var showCancelAlert by remember { mutableStateOf(false) }

    BackHandler {
        if (preferenceOptionsState.page > 1) {
            viewModel.intent(OnboardingIntent.MoveToPage(page = preferenceOptionsState.page - 1))
        } else if (viewModel.isEditPreference && state.preferenceOptionsState.isPreferenceChanged) {
            showCancelAlert = true
        } else {
            finish()
        }
    }

    BaseComposable(baseViewModel = viewModel) {
        PreferenceOptionListScreen(
            modifier = modifier,
            state = preferenceOptionsState,
            isEdit = viewModel.isEditPreference,
            onOptionSelected = { selected, option ->
                when (option.type) {
                    PreferenceOptionType.BREAD_TYPE -> viewModel.intent(OnboardingIntent.SelectBreadTypeOption(selected = selected, option = option))
                    PreferenceOptionType.FLAVOR -> viewModel.intent(OnboardingIntent.SelectFlavorOption(selected = selected, option = option))
                    PreferenceOptionType.ATMOSPHERE -> viewModel.intent(OnboardingIntent.SelectBakeryTypeOption(selected = selected, option = option))
                }
            },
            onPreviousPage = { page ->
                Timber.e("onPreviousPage :: page is $page")
                if (page > 0) {
                    viewModel.intent(OnboardingIntent.MoveToPage(page = page))
                } else if (viewModel.isEditPreference && state.preferenceOptionsState.isPreferenceChanged) {
                    showCancelAlert = true
                } else {
                    finish()
                }
            },
            onNextPage = { page -> viewModel.intent(OnboardingIntent.MoveToPage(page = page)) },
            onComplete = {
                if (viewModel.isEditPreference) {
                    viewModel.intent(OnboardingIntent.EditPreferences)
                } else {
                    navigateToNicknameSettings()
                }
            }
        )

        BakeRoadLoadingScreen(
            modifier = Modifier.fillMaxSize(),
            isLoading = state.isLoading,
            type = LoadingType.Default
        )

        if (showCancelAlert) {
            BakeRoadAlert(
                buttonType = PopupButton.SHORT,
                title = stringResource(id = R.string.feature_onboarding_title_cancel_edit_preference),
                content = stringResource(id = R.string.feature_onboarding_description_cancel_edit_preference),
                primaryText = stringResource(id = com.twolskone.bakeroad.core.designsystem.R.string.core_designsystem_button_exit),
                secondaryText = stringResource(id = com.twolskone.bakeroad.core.designsystem.R.string.core_designsystem_button_cancel),
                onDismissRequest = { showCancelAlert = false },
                onPrimaryAction = {
                    showCancelAlert = false
                    finish()
                },
                onSecondaryAction = { showCancelAlert = false }
            )
        }
    }
}