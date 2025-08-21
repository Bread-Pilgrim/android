package com.twolskone.bakeroad.feature.report.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.core.model.ReportDate
import com.twolskone.bakeroad.feature.report.detail.ReportDetailRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
internal data class ReportDetailRoute(
    val dateListJsonString: String,
    val index: Int
)

internal fun NavGraphBuilder.reportDetailScreen(
    goBack: () -> Unit
) {
    composable<ReportDetailRoute> {
        ReportDetailRoute(goBack = goBack)
    }
}

internal fun NavController.navigateToReportDetail(
    dateList: List<ReportDate>,
    index: Int,
    navOptions: NavOptions? = null
) {
    val jsonString = Json.encodeToString(dateList)
    navigate(route = ReportDetailRoute(dateListJsonString = jsonString, index = index), navOptions = navOptions)
}