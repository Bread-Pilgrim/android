package com.twolskone.bakeroad.feature.mypage.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.twolskone.bakeroad.feature.mypage.MyPageRoute
import kotlinx.serialization.Serializable

@Serializable
data object MyPageRoute

fun NavGraphBuilder.myPageScreen(
    padding: PaddingValues,
    navigateToSettings: () -> Unit,
    navigateToReport: () -> Unit,
    navigateToBadgeList: () -> Unit,
    navigateToMyReviews: () -> Unit,
    navigateToEditPreference: (ActivityResultLauncher<Intent>) -> Unit,
    goBack: () -> Unit
) {
    composable<MyPageRoute> {
        MyPageRoute(
            padding = padding,
            navigateToSettings = navigateToSettings,
            navigateToReport = navigateToReport,
            navigateToEditPreference = navigateToEditPreference,
            navigateToMyReviews = navigateToMyReviews,
            navigateToBadgeList = navigateToBadgeList,
            goBack = goBack
        )
    }
}

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) =
    navigate(route = MyPageRoute, navOptions = navOptions)