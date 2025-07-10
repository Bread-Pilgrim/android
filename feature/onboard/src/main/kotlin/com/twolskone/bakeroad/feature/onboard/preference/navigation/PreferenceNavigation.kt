package com.twolskone.bakeroad.feature.onboard.preference.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.onboard.OnboardingViewModel
import com.twolskone.bakeroad.feature.onboard.preference.PreferenceOptionsRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object PreferenceRoute

internal fun NavController.navigateToPreferenceOptions(navOptions: NavOptions? = null) =
    navigate(route = PreferenceRoute, navOptions = navOptions)

internal fun NavGraphBuilder.preferenceOptionsScreen(
    viewModel: OnboardingViewModel,
    navigateToNicknameSettings: () -> Unit,
    finish: () -> Unit,
) {
    composable<PreferenceRoute> {
        PreferenceOptionsRoute(
            viewModel = viewModel,
            navigateToNicknameSettings = navigateToNicknameSettings,
            finish = finish
        )
    }
}