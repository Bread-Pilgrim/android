package com.twolskone.bakeroad.feature.mybakery.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.mybakery.MyBakeryRoute
import kotlinx.serialization.Serializable

@Serializable
data object MyBakeryRoute

fun NavGraphBuilder.myBakeryScreen(
    padding: PaddingValues,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, launcher: ActivityResultLauncher<Intent>) -> Unit,
    goBack: () -> Unit
) {
    composable<MyBakeryRoute> {
        MyBakeryRoute(
            padding = padding,
            navigateToBakeryDetail = navigateToBakeryDetail,
            goBack = goBack
        )
    }
}

fun NavController.navigateToMyBakery(navOptions: NavOptions? = null) =
    navigate(route = MyBakeryRoute, navOptions = navOptions)