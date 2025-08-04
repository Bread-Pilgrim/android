package com.twolskone.bakeroad.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.feature.home.navigation.HomeRoute
import com.twolskone.bakeroad.feature.home.navigation.homeScreen
import com.twolskone.bakeroad.feature.search.navigation.searchScreen

@Composable
internal fun BakeRoadNavHost(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    navController: NavHostController,
    navigateToBakeryList: (areaCodes: String, BakeryType, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToEditPreference: (ActivityResultLauncher<Intent>) -> Unit,
    showSnackbar: (SnackbarState) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute
    ) {
        homeScreen(
            padding = padding,
            navigateToBakeryList = navigateToBakeryList,
            navigateToBakeryDetail = navigateToBakeryDetail,
            navigateToEditPreference = navigateToEditPreference,
            showSnackbar = showSnackbar
        )
        searchScreen(
            padding = padding,
            navigateToBakeryDetail = navigateToBakeryDetail,
            showSnackbar = showSnackbar
        )
    }
}