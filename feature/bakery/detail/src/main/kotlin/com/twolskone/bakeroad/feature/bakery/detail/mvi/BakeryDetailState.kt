package com.twolskone.bakeroad.feature.bakery.detail.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.feature.bakery.detail.model.BakeryInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class BakeryDetailState(
    val bakeryImageList: ImmutableList<String> = persistentListOf(),
    val bakeryInfo: BakeryInfo? = null,
    val menuList: ImmutableList<BakeryDetail.Menu> = persistentListOf(),
    val tourAreaList: ImmutableList<TourArea> = persistentListOf(),
    val reviewState: ReviewState = ReviewState()
) : BaseUiState

@Immutable
internal data class ReviewState(
    val count: Int = 0,
    val avgRating: Float = 0.0f,
    val previewReviewList: ImmutableList<BakeryReview> = persistentListOf()
)