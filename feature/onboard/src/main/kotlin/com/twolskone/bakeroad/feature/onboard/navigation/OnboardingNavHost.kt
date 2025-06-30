package com.twolskone.bakeroad.feature.onboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.twolskone.bakeroad.feature.onboard.nickname.navigation.NicknameSettingsRoute
import com.twolskone.bakeroad.feature.onboard.nickname.navigation.nicknameSettingsScreen

@Composable
internal fun OnBoardingNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = NicknameSettingsRoute,
    ) {
        nicknameSettingsScreen(
            onBackClick = {
                // TODO. Navigate to taste selection screen.
            }
        )
    }
}