package com.twolskone.bakeroad.feature.mybakery.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent
import com.twolskone.bakeroad.core.model.type.BakerySortType
import com.twolskone.bakeroad.feature.mybakery.model.Tab

internal sealed interface MyBakeryIntent : BaseUiIntent {
    data class SelectTab(val tab: Tab) : MyBakeryIntent
    data class SelectSort(val sort: BakerySortType) : MyBakeryIntent
    data class ClickBakeryLike(val bakeryId: Int, val isLike: Boolean) : MyBakeryIntent
}