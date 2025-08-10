package com.twolskone.bakeroad.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.feature.mypage.MyPageRoute
import kotlinx.serialization.Serializable

@Serializable
data object MyPageRoute

fun NavGraphBuilder.myPageScreen(
    padding: PaddingValues,
    navigateToSettings: () -> Unit,
    showSnackbar: (SnackbarState) -> Unit
) {
    composable<MyPageRoute> {
        MyPageRoute(
            padding = padding,
            navigateToSettings = navigateToSettings,
            showSnackbar = showSnackbar
        )
    }
}

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) =
    navigate(route = MyPageRoute, navOptions = navOptions)