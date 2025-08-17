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
import com.twolskone.bakeroad.feature.mybakery.navigation.myBakeryScreen
import com.twolskone.bakeroad.feature.mypage.navigation.myPageScreen
import com.twolskone.bakeroad.feature.search.navigation.searchScreen

@Composable
internal fun BakeRoadNavHost(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    navController: NavHostController,
    navigateToBakeryList: (areaCodes: String, BakeryType, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, launcher: ActivityResultLauncher<Intent>) -> Unit,
    navigateToEditPreference: (ActivityResultLauncher<Intent>) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToReport: () -> Unit,
    openBrowser: (url: String) -> Unit,
    goBack: () -> Unit,
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
            openBrowser = openBrowser,
            showSnackbar = showSnackbar
        )
        searchScreen(
            padding = padding,
            navigateToBakeryDetail = navigateToBakeryDetail,
            goBack = goBack,
            showSnackbar = showSnackbar
        )
        myBakeryScreen(
            padding = padding,
            navigateToBakeryDetail = navigateToBakeryDetail,
            goBack = goBack,
            showSnackbar = showSnackbar
        )
        myPageScreen(
            padding = padding,
            navigateToSettings = navigateToSettings,
            navigateToReport = navigateToReport,
            navigateToEditPreference = navigateToEditPreference,
            goBack = goBack,
            showSnackbar = showSnackbar
        )
    }
}