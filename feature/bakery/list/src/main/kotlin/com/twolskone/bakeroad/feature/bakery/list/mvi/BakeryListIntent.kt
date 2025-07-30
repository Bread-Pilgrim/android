package com.twolskone.bakeroad.feature.bakery.list.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface BakeryListIntent : BaseUiIntent {
    data class ClickBakeryLike(val bakeryId: Int, val isLike: Boolean) : BakeryListIntent
}