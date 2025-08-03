package com.twolskone.bakeroad.navigation

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.twolskone.bakeroad.feature.home.navigation.HomeRoute
import com.twolskone.bakeroad.feature.search.navigation.SearchRoute
import com.twolskone.bakeroad.navigation.BakeRoadDestination.HOME
import com.twolskone.bakeroad.navigation.BakeRoadDestination.SEARCH
import kotlin.reflect.KClass
import com.twolskone.bakeroad.feature.home.R as homeR
import com.twolskone.bakeroad.feature.search.R as searchR

/**
 * 빵글 메인 메뉴
 * @property HOME   홈
 * @property SEARCH 검색
 */
internal enum class BakeRoadDestination(
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
    SEARCH(
        route = SearchRoute::class,
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = searchR.drawable.feature_search_ic_menu_stroke),
                contentDescription = "Search"
            )
        },
        selectedIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = searchR.drawable.feature_search_ic_menu_fill),
                contentDescription = "SearchSelected"
            )
        },
        labelId = searchR.string.feature_search
    ),
}