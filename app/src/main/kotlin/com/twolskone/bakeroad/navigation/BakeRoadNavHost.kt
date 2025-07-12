package com.twolskone.bakeroad.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.twolskone.bakeroad.feature.home.navigation.HomeRoute
import com.twolskone.bakeroad.feature.home.navigation.homeScreen

@Composable
internal fun BakeRoadNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute
    ) {
        homeScreen()
    }
}