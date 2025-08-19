package com.twolskone.bakeroad.feature.review.myreviews.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface MyReviewsIntent : BaseUiIntent {
    data class GetMyReviews(val refresh: Boolean) : MyReviewsIntent
}