package com.twolskone.bakeroad.feature.onboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.onboard.preference.PreferenceOptionsRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object PreferenceRoute

internal fun NavController.navigateToPreference(navOptions: NavOptions? = null) =
    navigate(route = PreferenceRoute, navOptions = navOptions)

internal fun NavGraphBuilder.preferenceScreen(
    navigateToNicknameSettings: () -> Unit,
    finish: () -> Unit,
) {
    composable<PreferenceRoute> {
        PreferenceOptionsRoute(
            navigateToNicknameSettings = navigateToNicknameSettings,
            finish = finish
        )
    }
}