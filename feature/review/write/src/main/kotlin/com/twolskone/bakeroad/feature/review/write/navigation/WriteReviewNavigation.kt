package com.twolskone.bakeroad.feature.review.write.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.review.write.WriteReviewRoute
import com.twolskone.bakeroad.feature.review.write.WriteReviewViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object WriteReviewRoute

internal fun NavController.navigateToWriteReview(navOptions: NavOptions? = null) =
    navigate(route = WriteReviewRoute, navOptions = navOptions)

internal fun NavGraphBuilder.writeReviewScreen(
    viewModel: WriteReviewViewModel,
    onBackClick: () -> Unit
) {
    composable<WriteReviewRoute> {
        WriteReviewRoute(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}