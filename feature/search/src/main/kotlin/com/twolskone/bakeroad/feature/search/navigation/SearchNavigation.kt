package com.twolskone.bakeroad.feature.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.search.SearchRoute
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute

fun NavGraphBuilder.searchScreen(
    padding: PaddingValues
) {
    composable<SearchRoute> {
        SearchRoute(
            padding = padding
        )
    }
}

fun NavController.navigateToSearch(navOptions: NavOptions? = null) =
    navigate(route = SearchRoute, navOptions = navOptions)