package com.twolskone.bakeroad.navigation

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.twolskone.bakeroad.feature.home.navigation.HomeRoute
import kotlin.reflect.KClass
import com.twolskone.bakeroad.feature.home.R as homeR

/**
 * 빵글 메인 메뉴
 * @property HOME   홈
 */
internal enum class BakeRoadMenu(
    val route: KClass<*>,
    val icon: @Composable () -> Unit,
    val selectedIcon: @Composable () -> Unit,
    @StringRes val labelId: Int,
) {
    HOME(
        route = HomeRoute::class,
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