package com.twolskone.bakeroad.feature.mybakery.model

import androidx.annotation.DrawableRes
import com.twolskone.bakeroad.feature.mybakery.R
import com.twolskone.bakeroad.feature.mybakery.model.ViewMode.LIST
import com.twolskone.bakeroad.feature.mybakery.model.ViewMode.MAP

/**
 * 내 빵집 보기 타입
 * @property LIST   리스트
 * @property MAP    지도
 */
internal enum class ViewMode(
    @DrawableRes val iconRes: Int,
    val contentDescription: String
) {
    LIST(
        iconRes = R.drawable.feature_mybakery_ic_list,
        contentDescription = "List"
    ),
    MAP(
        iconRes = R.drawable.feature_mybakery_ic_location,
        contentDescription = "Map"
    )
}