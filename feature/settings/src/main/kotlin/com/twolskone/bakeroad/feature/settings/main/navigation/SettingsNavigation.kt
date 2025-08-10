package com.twolskone.bakeroad.feature.settings.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.settings.main.SettingsRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object SettingsRoute

internal fun NavGraphBuilder.settingsScreen(
    navigateToAppInfo: () -> Unit
) {
    composable<SettingsRoute> {
        SettingsRoute(
            navigateToAppInfo = navigateToAppInfo
        )
    }
}