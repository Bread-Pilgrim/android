package com.twolskone.bakeroad.feature.review.write.menu.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.review.write.WriteReviewViewModel
import com.twolskone.bakeroad.feature.review.write.menu.MenuSelectionRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object MenuSelectionRoute

internal fun NavGraphBuilder.menuSelectionScreen(
    viewModel: WriteReviewViewModel
) {
    composable<MenuSelectionRoute> {
        MenuSelectionRoute(viewModel = viewModel)
    }
}