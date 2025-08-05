package com.twolskone.bakeroad.feature.bakery.detail.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.feature.bakery.detail.R

@Immutable
internal enum class BakeryDetailTab(@StringRes val labelRes: Int) {
    HOME(labelRes = R.string.feature_bakery_detail_tab_home),
    MENU(labelRes = R.string.feature_bakery_detail_tab_menu),
    REVIEW(labelRes = R.string.feature_bakery_detail_tab_review),
    TOUR_AREA(labelRes = R.string.feature_bakery_detail_tab_tour_area)
}