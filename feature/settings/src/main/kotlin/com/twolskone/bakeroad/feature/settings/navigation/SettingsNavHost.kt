package com.twolskone.bakeroad.feature.settings.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.twolskone.bakeroad.core.common.android.extension.isRouteInHierarchy
import com.twolskone.bakeroad.feature.settings.appinfo.navigation.AppInfoRoute
import com.twolskone.bakeroad.feature.settings.appinfo.navigation.appInfoScreen
import com.twolskone.bakeroad.feature.settings.appinfo.navigation.navigateToAppInfo
import com.twolskone.bakeroad.feature.settings.main.navigation.SettingsRoute
import com.twolskone.bakeroad.feature.settings.main.navigation.settingsScreen
import com.twolskone.bakeroad.feature.settings.notice.navigation.NoticeRoute
import com.twolskone.bakeroad.feature.settings.notice.navigation.navigateToNotice
import com.twolskone.bakeroad.feature.settings.notice.navigation.noticeScreen

@Composable
internal fun SettingsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigateToLogin: () -> Unit,
    finish: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SettingsRoute
    ) {
        settingsScreen(
            goBack = finish,
            navigateToNotice = navController::navigateToNotice,
            navigateToAppInfo = navController::navigateToAppInfo,
            navigateToLogin = navigateToLogin
        )
        appInfoScreen(
            goBack = {
                val canBack = navController.currentDestination.isRouteInHierarchy(route = AppInfoRoute::class)
                if (canBack) {
                    navController.popBackStack()
                }
            }
        )
        noticeScreen(
            goBack = {
                val canBack = navController.currentDestination.isRouteInHierarchy(route = NoticeRoute::class)
                if (canBack) {
                    navController.popBackStack()
                }
            }
        )
    }
}