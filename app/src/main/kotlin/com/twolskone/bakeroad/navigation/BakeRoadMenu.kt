package com.twolskone.bakeroad.navigation

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.twolskone.bakeroad.feature.home.R as homeR

internal enum class BakeRoadMenu(
    val icon: @Composable () -> Unit,
    val selectedIcon: @Composable () -> Unit,
    @StringRes val labelId: Int,
) {
    // 홈? 추천?
    HOME(
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = homeR.drawable.feature_home_ic_menu_stroke),
                contentDescription = "Home"
            )
        },
        selectedIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = homeR.drawable.feature_home_ic_menu_fill),
                contentDescription = "HomeSelected"
            )
        },
        labelId = homeR.string.feature_home
    ),
//    SEARCH,
//    MY_BAKERY,
//    MY_PAGE
}