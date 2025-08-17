package com.twolskone.bakeroad.feature.mybakery.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.feature.mybakery.MyBakeryRoute
import kotlinx.serialization.Serializable

@Serializable
data object MyBakeryRoute

fun NavGraphBuilder.myBakeryScreen(
    padding: PaddingValues,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, launcher: ActivityResultLauncher<Intent>) -> Unit,
    goBack: () -> Unit,
    showSnackbar: (SnackbarState) -> Unit
) {
    composable<MyBakeryRoute> {
        MyBakeryRoute(
            padding = padding,
            navigateToBakeryDetail = navigateToBakeryDetail,
            goBack = goBack,
            showSnackbar = showSnackbar
        )
    }
}

fun NavController.navigateToMyBakery(navOptions: NavOptions? = null) =
    navigate(route = MyBakeryRoute, navOptions = navOptions)