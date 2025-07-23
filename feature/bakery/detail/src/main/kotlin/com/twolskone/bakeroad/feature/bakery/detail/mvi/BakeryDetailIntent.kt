package com.twolskone.bakeroad.feature.bakery.detail.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent
import com.twolskone.bakeroad.feature.bakery.detail.model.BakeryDetailTab
import com.twolskone.bakeroad.feature.bakery.detail.model.ReviewTab

internal sealed interface BakeryDetailIntent : BaseUiIntent {
    data class SelectTab(val tab: BakeryDetailTab) : BakeryDetailIntent
    data class SelectReviewTab(val tab: ReviewTab) : BakeryDetailIntent
}