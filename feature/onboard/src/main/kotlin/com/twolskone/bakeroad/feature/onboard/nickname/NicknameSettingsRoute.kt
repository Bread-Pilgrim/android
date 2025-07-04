package com.twolskone.bakeroad.feature.onboard.nickname

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.feature.onboard.OnboardingViewModel
import com.twolskone.bakeroad.feature.onboard.R
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingIntent

@Composable
internal fun NicknameSettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val nicknameState = state.nicknameSettingsState
    val textFieldState = rememberTextFieldState(initialText = nicknameState.nicknameText)
    val descriptionId = if (textFieldState.text.length == 1) {
        R.string.feature_onboarding_description_nickname_min_length
    } else if (textFieldState.text.length > 8) {
        R.string.feature_onboarding_description_nickname_max_length
    } else {
        null
    }

    NicknameSettingsScreen(
        modifier = modifier,
        textFieldState = textFieldState,
        description = descriptionId?.let { stringResource(id = it) } ?: nicknameState.description,
        isLoading = state.isLoading,
        onBackClick = onBackClick,
        onStartClick = { viewModel.intent(OnboardingIntent.StartBakeRoad) }
    )
}