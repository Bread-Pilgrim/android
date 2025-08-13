package com.twolskone.bakeroad.feature.report.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
internal data object ReportDetailRoute

internal fun NavController.navigateToReportDetail(navOptions: NavOptions? = null) =
    navigate(route = ReportDetailRoute, navOptions = navOptions)