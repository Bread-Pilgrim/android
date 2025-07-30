package com.twolskone.bakeroad.feature.review.write.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.core.common.android.extension.isRouteInHierarchy
import com.twolskone.bakeroad.feature.review.write.WriteReviewViewModel
import com.twolskone.bakeroad.feature.review.write.menu.navigation.MenuSelectionRoute
import com.twolskone.bakeroad.feature.review.write.menu.navigation.menuSelectionScreen

@Composable
internal fun WriteBakeryReviewNavHost(
    modifier: Modifier = Modifier,
    viewModel: WriteReviewViewModel = hiltViewModel(),
    navController: NavHostController,
    finish: () -> Unit,
    setResult: (code: Int, withFinish: Boolean) -> Unit
) {
    BaseComposable(baseViewModel = viewModel) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = MenuSelectionRoute
        ) {
            menuSelectionScreen(
                viewModel = viewModel,
                onNextClick = navController::navigateToWriteBakeryReview,
                onBackClick = finish
            )
            writeBakeryReviewScreen(
                viewModel = viewModel,
                onBackClick = {
                    val canBack = navController.currentDestination.isRouteInHierarchy(route = WriteBakeryReviewRoute::class)
                    if (canBack) {
//                    navController.navigateToMenuSelection()
                        navController.popBackStack()
                    }
                },
                setResult = setResult
            )
        }
    }
}