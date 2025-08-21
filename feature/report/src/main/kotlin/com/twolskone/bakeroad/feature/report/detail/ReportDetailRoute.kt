package com.twolskone.bakeroad.feature.report.detail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun ReportDetailRoute(
    viewModel: ReportDetailViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    viewModel
    ReportDetailScreen(
        onBackClick = {}
    )
}