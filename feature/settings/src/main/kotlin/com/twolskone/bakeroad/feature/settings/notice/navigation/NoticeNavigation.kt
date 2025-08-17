package com.twolskone.bakeroad.feature.settings.notice.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.settings.notice.NoticeRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object NoticeRoute

internal fun NavGraphBuilder.noticeScreen(
    goBack: () -> Unit
) {
    composable<NoticeRoute> {
        NoticeRoute(
            goBack = goBack
        )
    }
}

internal fun NavController.navigateToNotice(navOptions: NavOptions? = null) =
    navigate(route = NoticeRoute, navOptions = navOptions)