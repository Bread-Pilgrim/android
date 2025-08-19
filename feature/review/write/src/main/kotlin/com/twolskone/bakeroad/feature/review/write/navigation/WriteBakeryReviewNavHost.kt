package com.twolskone.bakeroad.feature.review.write.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.core.common.android.extension.isRouteInHierarchy
import com.twolskone.bakeroad.feature.review.write.WriteReviewViewModel
import com.twolskone.bakeroad.feature.review.write.completion.navigation.completionScreen
import com.twolskone.bakeroad.feature.review.write.completion.navigation.navigateToComplete
import com.twolskone.bakeroad.feature.review.write.menu.navigation.MenuSelectionRoute
import com.twolskone.bakeroad.feature.review.write.menu.navigation.menuSelectionScreen

@Composable
internal fun WriteBakeryReviewNavHost(
    modifier: Modifier = Modifier,
    viewModel: WriteReviewViewModel = hiltViewModel(),
    navController: NavHostController,
    finish: () -> Unit,
    setResult: (code: Int, withFinish: Boolean) -> Unit,
    goHome: () -> Unit
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
                setResult = setResult,
                navigateToComplete = {
                    navController.navigateToComplete(
                        navOptions = navOptions {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    )
                }
            )
            completionScreen(
                goHome = goHome,
                confirmReview = { setResult(Activity.RESULT_OK, true) }
            )
        }
    }
}