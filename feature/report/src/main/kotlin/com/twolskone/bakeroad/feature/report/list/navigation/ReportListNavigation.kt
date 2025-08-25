package com.twolskone.bakeroad.feature.report.list.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.core.model.ReportDate
import com.twolskone.bakeroad.feature.report.list.ReportListRoute
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

@Serializable
internal data object ReportListRoute

internal fun NavGraphBuilder.reportListScreen(
    goBack: () -> Unit,
    navigateToReportDetail: (ImmutableList<ReportDate>, Int) -> Unit
) {
    composable<ReportListRoute> {
        ReportListRoute(
            goBack = goBack,
            navigateToReportDetail = navigateToReportDetail
        )
    }
}