package com.twolskone.bakeroad.navigation

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.twolskone.bakeroad.feature.home.navigation.HomeRoute
import com.twolskone.bakeroad.feature.mybakery.navigation.MyBakeryRoute
import com.twolskone.bakeroad.feature.mypage.navigation.MyPageRoute
import com.twolskone.bakeroad.feature.search.navigation.SearchRoute
import com.twolskone.bakeroad.navigation.BakeRoadDestination.Home
import com.twolskone.bakeroad.navigation.BakeRoadDestination.MyBakery
import com.twolskone.bakeroad.navigation.BakeRoadDestination.MyPage
import com.twolskone.bakeroad.navigation.BakeRoadDestination.Search
import kotlin.reflect.KClass
import com.twolskone.bakeroad.feature.home.R as homeR
import com.twolskone.bakeroad.feature.mybakery.R as myBakeryR
import com.twolskone.bakeroad.feature.mypage.R as myPageR
import com.twolskone.bakeroad.feature.search.R as searchR

/**
 * 빵글 메인 메뉴
 * @property Home       홈
 * @property Search     검색
 * @property MyBakery   내 빵집
 * @property MyPage     나의 빵글
 */
internal enum class BakeRoadDestination(
    val route: KClass<*>,
    val icon: @Composable () -> Unit,
    val selectedIcon: @Composable () -> Unit,
    @StringRes val labelId: Int,
) {
    Home(
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
    Search(
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
    MyBakery(
        route = MyBakeryRoute::class,
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = myBakeryR.drawable.feature_mybakery_ic_menu_stroke),
                contentDescription = "MyBakery"
            )
        },
        selectedIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = myBakeryR.drawable.feature_mybakery_ic_menu_fill),
                contentDescription = "MyBakerySelected"
            )
        },
        labelId = myBakeryR.string.feature_mybakery
    ),
    MyPage(
        route = MyPageRoute::class,
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = myPageR.drawable.feature_mypage_ic_menu_stroke),
                contentDescription = "MyPage"
            )
        },
        selectedIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = myPageR.drawable.feature_mypage_ic_menu_fill),
                contentDescription = "MyPageSelected"
            )
        },
        labelId = myPageR.string.feature_mypage
    )
}