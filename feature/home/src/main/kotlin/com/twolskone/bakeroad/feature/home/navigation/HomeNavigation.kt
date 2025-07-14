package com.twolskone.bakeroad.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.feature.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeScreen(navigateToBakeryList: (BakeryType) -> Unit) {
    composable<HomeRoute> {
        HomeRoute(navigateToBakeryList = navigateToBakeryList)
    }
}