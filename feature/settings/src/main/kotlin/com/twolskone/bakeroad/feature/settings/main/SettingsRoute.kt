package com.twolskone.bakeroad.feature.settings.main

import androidx.compose.runtime.Composable

@Composable
internal fun SettingsRoute(
    navigateToAppInfo: () -> Unit
) {
    SettingsScreen(
        onBackClick = {},
        onNoticeClick = {},
        onAppInfoClick = navigateToAppInfo,
        onLogoutClick = {}
    )
}