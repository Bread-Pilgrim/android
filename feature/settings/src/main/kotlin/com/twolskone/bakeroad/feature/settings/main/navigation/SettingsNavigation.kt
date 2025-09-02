package com.twolskone.bakeroad.feature.settings.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.settings.main.SettingsRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object SettingsRoute

internal fun NavGraphBuilder.settingsScreen(
    goBack: () -> Unit,
    navigateToNotice: () -> Unit,
    navigateToAppInfo: () -> Unit,
    navigateToLogin: () -> Unit
) {
    composable<SettingsRoute> {
        SettingsRoute(
            goBack = goBack,
            navigateToNotice = navigateToNotice,
            navigateToAppInfo = navigateToAppInfo,
            navigateToLogin = navigateToLogin
        )
    }
}