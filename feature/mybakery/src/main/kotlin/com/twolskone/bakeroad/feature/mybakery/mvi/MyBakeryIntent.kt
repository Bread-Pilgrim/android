package com.twolskone.bakeroad.feature.mybakery.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent
import com.twolskone.bakeroad.core.model.type.BakerySortType
import com.twolskone.bakeroad.feature.mybakery.model.Tab

internal sealed interface MyBakeryIntent : BaseUiIntent {
    data class SelectTab(val tab: Tab) : MyBakeryIntent
    data class SelectVisitedSort(val sort: BakerySortType) : MyBakeryIntent
    data class SelectLikeSort(val sort: BakerySortType) : MyBakeryIntent
    data class GetVisitedBakeries(val refresh: Boolean) : MyBakeryIntent
    data class GetLikeBakeries(val refresh: Boolean) : MyBakeryIntent
    data class DeleteBakery(val bakeryId: Int) : MyBakeryIntent
    data object Refresh : MyBakeryIntent
}