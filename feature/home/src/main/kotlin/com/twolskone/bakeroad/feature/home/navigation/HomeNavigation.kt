package com.twolskone.bakeroad.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.feature.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeScreen(
    padding: PaddingValues,
    navigateToBakeryList: (areaCodes: String, BakeryType) -> Unit,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int) -> Unit
) {
    composable<HomeRoute> {
        HomeRoute(
            padding = padding,
            navigateToBakeryList = navigateToBakeryList,
            navigateToBakeryDetail = navigateToBakeryDetail
        )
    }
}