package com.twolskone.bakeroad.feature.report.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.report.detail.ReportDetailRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object ReportDetailRoute

internal fun NavGraphBuilder.reportDetailScreen(
    goBack: () -> Unit
) {
    composable<ReportDetailRoute> {
        ReportDetailRoute(goBack = goBack)
    }
}

internal fun NavController.navigateToReportDetail(navOptions: NavOptions? = null) =
    navigate(route = ReportDetailRoute, navOptions = navOptions)