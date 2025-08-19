package com.twolskone.bakeroad.feature.review.myreviews.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.MyBakeryReview
import com.twolskone.bakeroad.core.ui.model.PagingUiState

@Immutable
internal data class MyReviewsState(
    val paging: PagingUiState<MyBakeryReview> = PagingUiState()
) : BaseUiState