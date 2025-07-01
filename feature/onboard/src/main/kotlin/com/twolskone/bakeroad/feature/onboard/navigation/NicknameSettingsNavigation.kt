package com.twolskone.bakeroad.feature.onboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.onboard.nickname.NicknameSettingsRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object NicknameSettingsRoute

internal fun NavController.navigateToNicknameSettings(navOptions: NavOptions? = null) =
    navigate(route = NicknameSettingsRoute, navOptions = navOptions)

internal fun NavGraphBuilder.nicknameSettingsScreen(
    onBackClick: () -> Unit,
) {
    composable<NicknameSettingsRoute> {
        NicknameSettingsRoute(onBackClick = onBackClick)
    }
}