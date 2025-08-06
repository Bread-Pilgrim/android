package com.twolskone.bakeroad.feature.mybakery.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.model.type.MyBakeryType
import com.twolskone.bakeroad.feature.mybakery.R
import com.twolskone.bakeroad.feature.mybakery.model.Tab.LIKE
import com.twolskone.bakeroad.feature.mybakery.model.Tab.VISITED

/**
 * 내 빵집 탭
 * @property VISITED    다녀온 빵집
 * @property LIKE       찜한 빵집
 */
@Immutable
internal enum class Tab(
    val type: MyBakeryType,
    @StringRes val labelRes: Int
) {
    VISITED(
        type = MyBakeryType.VISITED,
        labelRes = R.string.feature_mybakery_tab_visited_bakery
    ),
    LIKE(
        type = MyBakeryType.LIKE,
        labelRes = R.string.feature_mybakery_tab_like_bakery
    )
}