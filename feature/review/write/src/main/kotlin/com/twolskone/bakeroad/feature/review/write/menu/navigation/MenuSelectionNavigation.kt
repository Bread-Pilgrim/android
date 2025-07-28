package com.twolskone.bakeroad.feature.review.write.menu.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.review.write.WriteReviewViewModel
import com.twolskone.bakeroad.feature.review.write.menu.MenuSelectionRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object MenuSelectionRoute

internal fun NavController.navigateToMenuSelection(navOptions: NavOptions? = null) =
    navigate(route = MenuSelectionRoute, navOptions = navOptions)

internal fun NavGraphBuilder.menuSelectionScreen(
    viewModel: WriteReviewViewModel,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    composable<MenuSelectionRoute> {
        MenuSelectionRoute(
            viewModel = viewModel,
            onNextClick = onNextClick,
            onBackClick = onBackClick
        )
    }
}