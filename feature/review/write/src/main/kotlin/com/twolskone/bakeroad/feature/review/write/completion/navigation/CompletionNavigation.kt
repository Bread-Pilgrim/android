package com.twolskone.bakeroad.feature.review.write.completion.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.review.write.completion.CompletionRoute
import kotlinx.serialization.Serializable

@Serializable
internal data object CompletionRoute

internal fun NavController.navigateToComplete(navOptions: NavOptions? = null) =
    navigate(route = CompletionRoute, navOptions = navOptions)

internal fun NavGraphBuilder.completionScreen(
    goHome: () -> Unit,
    confirmReview: () -> Unit
) {
    composable<CompletionRoute> {
        CompletionRoute(
            goHome = goHome,
            confirmReview = confirmReview
        )
    }
}