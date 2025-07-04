package com.twolskone.bakeroad.feature.onboard.preference

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.model.PreferenceOptionType
import com.twolskone.bakeroad.feature.onboard.OnboardingViewModel
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingIntent
import timber.log.Timber

@Composable
internal fun PreferenceOptionsRoute(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
    navigateToNicknameSettings: () -> Unit,
    finish: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value.preferenceOptionsState

    BackHandler {
        if (state.page > 1) {
            viewModel.intent(OnboardingIntent.MoveToPage(page = state.page - 1))
        } else {
            finish()
        }
    }

    PreferenceOptionsScreen(
        modifier = modifier,
        state = state,
        onOptionSelected = { selected, option ->
            when (option.type) {
                PreferenceOptionType.BREAD_TYPE -> viewModel.intent(OnboardingIntent.SelectBreadTypeOption(selected = selected, option = option))
                PreferenceOptionType.FLAVOR -> viewModel.intent(OnboardingIntent.SelectFlavorOption(selected = selected, option = option))
                PreferenceOptionType.ATMOSPHERE -> viewModel.intent(OnboardingIntent.SelectBakeryTypeOption(selected = selected, option = option))
                PreferenceOptionType.COMMERCIAL_AREA -> {}
            }
        },
        onPreviousPage = { page ->
            if (page > 0) {
                viewModel.intent(OnboardingIntent.MoveToPage(page = page))
            } else {
                Timber.e("onPreviousPage :: page is $page")
            }
        },
        onNextPage = { page -> viewModel.intent(OnboardingIntent.MoveToPage(page = page)) },
        onComplete = navigateToNicknameSettings
    )
}