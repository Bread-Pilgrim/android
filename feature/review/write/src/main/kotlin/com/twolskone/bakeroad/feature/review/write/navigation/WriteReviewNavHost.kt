package com.twolskone.bakeroad.feature.review.write.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.twolskone.bakeroad.core.common.android.base.extension.isRouteInHierarchy
import com.twolskone.bakeroad.feature.review.write.WriteReviewViewModel
import com.twolskone.bakeroad.feature.review.write.menu.navigation.MenuSelectionRoute
import com.twolskone.bakeroad.feature.review.write.menu.navigation.menuSelectionScreen
import com.twolskone.bakeroad.feature.review.write.menu.navigation.navigateToMenuSelection

@Composable
internal fun WriteReviewNavHost(
    modifier: Modifier = Modifier,
    viewModel: WriteReviewViewModel = hiltViewModel(),
    navController: NavHostController,
    finish: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MenuSelectionRoute
    ) {
        menuSelectionScreen(
            viewModel = viewModel,
            onNextClick = navController::navigateToWriteReview,
            onBackClick = finish
        )
        writeReviewScreen(
            viewModel = viewModel,
            onBackClick = {
                val canBack = navController.currentDestination.isRouteInHierarchy(route = WriteReviewRoute::class)
                if (canBack) {
//                    navController.navigateToMenuSelection()
                    navController.popBackStack()
                }
            }
        )
    }
}