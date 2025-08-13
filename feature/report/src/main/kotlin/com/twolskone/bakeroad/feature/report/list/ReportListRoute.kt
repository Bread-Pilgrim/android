package com.twolskone.bakeroad.feature.report.list

import androidx.compose.runtime.Composable

@Composable
internal fun ReportListRoute(
    navigateToReportDetail: () -> Unit
) {
    ReportListScreen(
        onBackClick = {},
        onItemClick = navigateToReportDetail
    )
}