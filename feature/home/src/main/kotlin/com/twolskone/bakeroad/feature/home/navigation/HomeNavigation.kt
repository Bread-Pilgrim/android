package com.twolskone.bakeroad.feature.home.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.feature.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeScreen(
    padding: PaddingValues,
    showSnackbar: (SnackbarState) -> Unit,
    navigateToBakeryList: (areaCodes: String, BakeryType) -> Unit,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int) -> Unit,
    navigateToEditPreference: (ActivityResultLauncher<Intent>) -> Unit
) {
    composable<HomeRoute> {
        HomeRoute(
            padding = padding,
            showSnackbar = showSnackbar,
            navigateToBakeryList = navigateToBakeryList,
            navigateToBakeryDetail = navigateToBakeryDetail,
            navigateToEditPreference = navigateToEditPreference
        )
    }
}