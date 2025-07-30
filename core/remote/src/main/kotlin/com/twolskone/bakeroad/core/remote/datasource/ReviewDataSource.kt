package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.review.ReviewLikeResponse
import kotlinx.coroutines.flow.Flow

interface ReviewDataSource {
    fun postLike(reviewId: Int): Flow<ReviewLikeResponse>
    fun deleteLike(reviewId: Int): Flow<ReviewLikeResponse>
}