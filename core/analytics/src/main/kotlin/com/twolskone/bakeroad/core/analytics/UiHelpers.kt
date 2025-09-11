package com.twolskone.bakeroad.core.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAnalyticsHelper = staticCompositionLocalOf<AnalyticsHelper> { EmptyAnalyticsHelper() }

@Composable
fun LogComposeScreenEvent(
    screen: String,
    analyticsHelper: AnalyticsHelper = LocalAnalyticsHelper.current
) {
    LaunchedEffect(Unit) {
        analyticsHelper.logEvent(
            event = AnalyticsEvent(
                type = AnalyticsEvent.Types.SCREEN_VIEW,
                extras = listOf(
                    AnalyticsEvent.Param(
                        key = AnalyticsEvent.ParamKeys.SCREEN_NAME,
                        value = screen
                    )
                )
            )
        )
    }
}