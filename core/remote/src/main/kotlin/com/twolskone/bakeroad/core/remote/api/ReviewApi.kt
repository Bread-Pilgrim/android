package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.review.ReviewLikeResponse
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

internal interface ReviewApi {

    @POST("reviews/{review_id}/like")
    suspend fun postLike(@Path("review_id") reviewId: Int): BaseResponse<ReviewLikeResponse>

    @DELETE("reviews/{review_id}/like")
    suspend fun deleteLike(@Path("review_id") reviewId: Int): BaseResponse<ReviewLikeResponse>
}