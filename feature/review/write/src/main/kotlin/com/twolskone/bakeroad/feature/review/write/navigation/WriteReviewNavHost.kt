package com.twolskone.bakeroad.feature.review.write.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.twolskone.bakeroad.feature.review.write.WriteReviewViewModel
import com.twolskone.bakeroad.feature.review.write.menu.navigation.MenuSelectionRoute
import com.twolskone.bakeroad.feature.review.write.menu.navigation.menuSelection

@Composable
internal fun WriteReviewNavHost(
    modifier: Modifier = Modifier,
    viewModel: WriteReviewViewModel = hiltViewModel(),
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MenuSelectionRoute
    ) {
        menuSelection(viewModel = viewModel)
    }
}