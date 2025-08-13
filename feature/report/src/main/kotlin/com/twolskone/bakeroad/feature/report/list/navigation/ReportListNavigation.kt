package com.twolskone.bakeroad.feature.report.list.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.report.list.ReportListRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object ReportListRoute

internal fun NavGraphBuilder.reportListScreen(
    navigateToReportDetail: () -> Unit
) {
    composable<ReportListRoute> {
        ReportListRoute(navigateToReportDetail = navigateToReportDetail)
    }
}