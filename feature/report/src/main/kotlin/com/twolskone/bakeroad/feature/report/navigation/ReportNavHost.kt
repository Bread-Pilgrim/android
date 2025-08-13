package com.twolskone.bakeroad.feature.report.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.twolskone.bakeroad.feature.report.detail.navigation.navigateToReportDetail
import com.twolskone.bakeroad.feature.report.list.navigation.ReportListRoute
import com.twolskone.bakeroad.feature.report.list.navigation.reportListScreen

@Composable
internal fun ReportNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ReportListRoute
    ) {
        reportListScreen(
            navigateToReportDetail = navController::navigateToReportDetail
        )
    }
}