package com.twolskone.bakeroad.feature.review.myreviews.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.MyBakeryReview
import com.twolskone.bakeroad.core.ui.model.CursorPagingUiState

@Immutable
internal data class MyReviewsState(
    val paging: CursorPagingUiState<MyBakeryReview> = CursorPagingUiState(isLoading = true)
) : BaseUiState