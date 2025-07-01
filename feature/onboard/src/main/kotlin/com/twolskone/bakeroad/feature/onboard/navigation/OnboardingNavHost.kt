package com.twolskone.bakeroad.feature.onboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
internal fun OnBoardingNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    finish: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = PreferenceRoute,
    ) {
        preferenceScreen(
            navigateToNicknameSettings = navHostController::navigateToNicknameSettings,
            finish = finish
        )
        nicknameSettingsScreen(onBackClick = navHostController::navigateToPreference)
    }
}