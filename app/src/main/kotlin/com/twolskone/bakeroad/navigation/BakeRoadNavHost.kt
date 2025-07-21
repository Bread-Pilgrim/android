package com.twolskone.bakeroad.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.feature.home.navigation.HomeRoute
import com.twolskone.bakeroad.feature.home.navigation.homeScreen

@Composable
internal fun BakeRoadNavHost(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    navController: NavHostController,
    navigateToBakeryList: (String, BakeryType) -> Unit,
    navigateToBakeryDetail: (bakeryId: Int) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute
    ) {
        homeScreen(
            padding = padding,
            navigateToBakeryList = navigateToBakeryList,
            navigateToBakeryDetail = navigateToBakeryDetail
        )
    }
}