package com.twolskone.bakeroad.feature.bakery.detail.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import com.twolskone.bakeroad.feature.bakery.detail.model.BakeryDetailTab
import com.twolskone.bakeroad.feature.bakery.detail.model.BakeryInfo
import com.twolskone.bakeroad.feature.bakery.detail.model.ReviewTab
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class BakeryDetailState(
    val tab: BakeryDetailTab = BakeryDetailTab.HOME,
    val bakeryImageList: ImmutableList<String> = persistentListOf(),
    val bakeryInfo: BakeryInfo? = null,
    val menuList: ImmutableList<BakeryDetail.Menu> = persistentListOf(),
    val tourAreaList: ImmutableList<TourArea> = persistentListOf(),
    val reviewState: ReviewState = ReviewState()
) : BaseUiState

@Immutable
internal data class ReviewState(
    val reviewCount: Int = 0,
    val tab: ReviewTab = ReviewTab.MY_REVIEW,
    val reviewSortType: ReviewSortType = ReviewSortType.LIKE_COUNT_DESC,
    val previewReviewList: ImmutableList<BakeryReview> = persistentListOf()
)