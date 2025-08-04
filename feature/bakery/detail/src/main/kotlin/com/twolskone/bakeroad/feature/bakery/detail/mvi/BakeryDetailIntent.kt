package com.twolskone.bakeroad.feature.bakery.detail.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import com.twolskone.bakeroad.feature.bakery.detail.model.BakeryDetailTab
import com.twolskone.bakeroad.feature.bakery.detail.model.ReviewTab

internal sealed interface BakeryDetailIntent : BaseUiIntent {
    data class SelectTab(val tab: BakeryDetailTab) : BakeryDetailIntent
    data class SelectReviewTab(val tab: ReviewTab) : BakeryDetailIntent
    data class SelectReviewSort(val sort: ReviewSortType) : BakeryDetailIntent
    data class ClickBakeryLike(val isLike: Boolean) : BakeryDetailIntent
    data class ClickReviewLike(val reviewId: Int, val isLike: Boolean) : BakeryDetailIntent
    data object RefreshPreviewReviews : BakeryDetailIntent
    data class UpdateReviewInfo(val avgRating: Float, val count: Int) : BakeryDetailIntent
}