package com.twolskone.bakeroad.feature.onboard.nickname

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.feature.onboard.OnboardingViewModel
import com.twolskone.bakeroad.feature.onboard.mvi.OnboardingIntent

@Composable
internal fun NicknameSettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value.nicknameSettingsState
    val textFieldState = rememberTextFieldState(initialText = state.nicknameText)

    LaunchedEffect(textFieldState.text) {
        viewModel.intent(OnboardingIntent.OnNicknameTextChanged(textFieldState.text.toString()))
    }

    NicknameSettingsScreen(
        modifier = modifier,
        textFieldState = textFieldState,
        description = state.descriptionId?.let { stringResource(id = it) }.orEmpty(),
        onBackClick = onBackClick
    )
}