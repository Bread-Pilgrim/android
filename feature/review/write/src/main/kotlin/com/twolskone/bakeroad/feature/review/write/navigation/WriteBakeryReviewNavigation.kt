package com.twolskone.bakeroad.feature.review.write.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.review.write.WriteBakeryReviewRoute
import com.twolskone.bakeroad.feature.review.write.WriteReviewViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object WriteBakeryReviewRoute

internal fun NavController.navigateToWriteBakeryReview(navOptions: NavOptions? = null) =
    navigate(route = WriteBakeryReviewRoute, navOptions = navOptions)

internal fun NavGraphBuilder.writeBakeryReviewScreen(
    viewModel: WriteReviewViewModel,
    onBackClick: () -> Unit,
    setResult: (code: Int, withFinish: Boolean) -> Unit
) {
    composable<WriteBakeryReviewRoute> {
        WriteBakeryReviewRoute(
            viewModel = viewModel,
            onBackClick = onBackClick,
            setResult = setResult
        )
    }
}