package com.twolskone.bakeroad.feature.settings.appinfo

import androidx.compose.runtime.Composable

@Composable
internal fun AppInfoRoute(
    goBack: () -> Unit
) {
    AppInfoScreen(
        onBackClick = goBack
    )
}