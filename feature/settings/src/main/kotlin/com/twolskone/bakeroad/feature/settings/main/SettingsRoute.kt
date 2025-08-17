package com.twolskone.bakeroad.feature.settings.main

import androidx.compose.runtime.Composable

@Composable
internal fun SettingsRoute(
    navigateToNotice: () -> Unit,
    navigateToAppInfo: () -> Unit
) {
    SettingsScreen(
        onBackClick = {},
        onNoticeClick = navigateToNotice,
        onAppInfoClick = navigateToAppInfo,
        onLogoutClick = {}
    )
}