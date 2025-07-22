package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.review.ReviewByBakeryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ReviewApi {

    @GET("reviews/bakery/{bakery_id}")
    suspend fun getReviewsByBakery(
        @Path("bakery_id") bakeryId: Int,
        @Query("cursor_id") cursorId: Int,
        @Query("page_size") pageSize: Int,
        @Query("sort_clause") sort: String
    ): BaseResponse<List<ReviewByBakeryResponse>>
}