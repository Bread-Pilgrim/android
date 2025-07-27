package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryDetailResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryReviewsResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface BakeryApi {

    @GET("bakeries/recommend/preference")
    suspend fun getRecommendPreferenceBakeries(@Query("area_code") areaCode: String): BaseResponse<List<RecommendBakeryResponse>>

    @GET("bakeries/recommend/hot")
    suspend fun getRecommendHotBakeries(@Query("area_code") areaCode: String): BaseResponse<List<RecommendBakeryResponse>>

    @GET("bakeries/preference")
    suspend fun getPreferenceBakeries(
        @Query("area_code") areaCode: String,
        @Query("cursor_value") cursorValue: String,
        @Query("page_size") pageSize: Int
    ): BaseResponse<BakeriesResponse>

    @GET("bakeries/hot")
    suspend fun getHotBakeries(
        @Query("area_code") areaCode: String,
        @Query("cursor_value") cursorValue: String,
        @Query("page_size") pageSize: Int
    ): BaseResponse<BakeriesResponse>

    @GET("bakeries/{bakeryId}")
    suspend fun getBakeryDetail(@Path("bakeryId") bakeryId: Int): BaseResponse<BakeryDetailResponse>

    @GET("bakeries/{bakery_id}/reviews")
    suspend fun getPreviewReviews(
        @Path("bakery_id") bakeryId: Int,
        @Query("cursor_value") cursorValue: String = "0:0",
        @Query("page_size") pageSize: Int = 5,
        @Query("sort_clause") sort: String = "LIKE_COUNT.DESC"
    ): BaseResponse<BakeryReviewsResponse>

    @GET("bakeries/{bakery_id}/reviews")
    suspend fun getReviews(
        @Path("bakery_id") bakeryId: Int,
        @Query("cursor_value") cursorValue: String,
        @Query("page_size") pageSize: Int,
        @Query("sort_clause") sort: String
    ): BaseResponse<BakeryReviewsResponse>

    @GET("bakeries/{bakery_id}/my-review")
    suspend fun getMyReviews(
        @Path("bakery_id") bakeryId: Int,
        @Query("cursor_value") cursorValue: String,
        @Query("page_size") pageSize: Int
    ): BaseResponse<BakeryReviewsResponse>
}