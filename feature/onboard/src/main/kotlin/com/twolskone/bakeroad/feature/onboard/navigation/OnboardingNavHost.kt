package com.twolskone.bakeroad.feature.onboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.twolskone.bakeroad.core.common.android.base.extension.isRouteInHierarchy
import com.twolskone.bakeroad.feature.onboard.OnboardingViewModel
import com.twolskone.bakeroad.feature.onboard.nickname.navigation.NicknameSettingsRoute
import com.twolskone.bakeroad.feature.onboard.nickname.navigation.navigateToNicknameSettings
import com.twolskone.bakeroad.feature.onboard.nickname.navigation.nicknameSettingsScreen
import com.twolskone.bakeroad.feature.onboard.preference.navigation.PreferenceRoute
import com.twolskone.bakeroad.feature.onboard.preference.navigation.navigateToPreferenceOptions
import com.twolskone.bakeroad.feature.onboard.preference.navigation.preferenceOptionsScreen

@Composable
internal fun OnBoardingNavHost(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
    navHostController: NavHostController,
    finish: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = PreferenceRoute,
    ) {
        // 취향 설정
        preferenceOptionsScreen(
            viewModel = viewModel,
            navigateToNicknameSettings = navHostController::navigateToNicknameSettings,
            finish = finish
        )
        // 닉네임 설정
        nicknameSettingsScreen(
            viewModel = viewModel,
            onBackClick = {
                val canBack = navHostController.currentDestination.isRouteInHierarchy(route = NicknameSettingsRoute::class)
                if (canBack) {
                    navHostController.navigateToPreferenceOptions()
                }
            }
        )
    }
}