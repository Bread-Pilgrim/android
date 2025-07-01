package com.twolskone.bakeroad.feature.onboard.preference

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.feature.onboard.OnboardingViewModel
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingIntent
import timber.log.Timber

@Composable
internal fun PreferenceRoute(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
    navigateToNicknameSettings: () -> Unit,
    finish: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value.preferenceState

    BackHandler {
        if (state.page > 1) {
            viewModel.intent(OnboardingIntent.MoveToPage(page = state.page - 1))
        } else {
            finish()
        }
    }

    PreferenceScreen(
        modifier = modifier,
        state = state,
        onPreferenceSelected = {},
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