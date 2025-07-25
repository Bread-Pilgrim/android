package com.twolskone.bakeroad.feature.bakery.detail.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.feature.bakery.detail.R

@Immutable
internal enum class BakeryDetailTab(@StringRes val labelId: Int) {
    HOME(labelId = R.string.feature_bakery_detail_tab_home),
    MENU(labelId = R.string.feature_bakery_detail_tab_menu),
    REVIEW(labelId = R.string.feature_bakery_detail_tab_review),
    TOUR_AREA(labelId = R.string.feature_bakery_detail_tab_tour_area)
}