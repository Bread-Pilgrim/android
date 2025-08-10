package com.twolskone.bakeroad.feature.settings.appinfo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.settings.appinfo.AppInfoRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object AppInfoRoute

internal fun NavGraphBuilder.appInfoScreen(
    goBack: () -> Unit
) {
    composable<AppInfoRoute> {
        AppInfoRoute(
            goBack = goBack
        )
    }
}

internal fun NavController.navigateToAppInfo(navOptions: NavOptions? = null) =
    navigate(route = AppInfoRoute, navOptions = navOptions)