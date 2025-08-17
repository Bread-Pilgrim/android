package com.twolskone.bakeroad.feature.settings.notice

import androidx.compose.runtime.Composable

@Composable
internal fun NoticeRoute(
    goBack: () -> Unit
) {
    NoticeScreen(
        onBackClick = goBack
    )
}