package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryDetailResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryMenuResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryReviewsResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface BakeryApi {

    /* 내 취향 기반 추천 빵집 목록 */
    @GET("bakeries/recommend/preference")
    suspend fun getRecommendPreferenceBakeries(@Query("area_code") areaCode: String): BaseResponse<List<RecommendBakeryResponse>>

    /* Hot한 추천 빵집 목록 */
    @GET("bakeries/recommend/hot")
    suspend fun getRecommendHotBakeries(@Query("area_code") areaCode: String): BaseResponse<List<RecommendBakeryResponse>>

    /* 내 취향 기반 빵집 목록 (페이징) */
    @GET("bakeries/preference")
    suspend fun getPreferenceBakeries(
        @Query("area_code") areaCode: String,
        @Query("cursor_value") cursorValue: String,
        @Query("page_size") pageSize: Int
    ): BaseResponse<BakeriesResponse>

    /* Hot한 빵집 목록 (페이징) */
    @GET("bakeries/hot")
    suspend fun getHotBakeries(
        @Query("area_code") areaCode: String,
        @Query("cursor_value") cursorValue: String,
        @Query("page_size") pageSize: Int
    ): BaseResponse<BakeriesResponse>

    /* 빵집 상세 조회 */
    @GET("bakeries/{bakeryId}")
    suspend fun getBakeryDetail(@Path("bakeryId") bakeryId: Int): BaseResponse<BakeryDetailResponse>

    /* 빵집 리뷰 프리뷰 목록 (페이징 x) */
    @GET("bakeries/{bakery_id}/reviews")
    suspend fun getPreviewReviews(
        @Path("bakery_id") bakeryId: Int,
        @Query("cursor_value") cursorValue: String = "0:0",
        @Query("page_size") pageSize: Int = 5,
        @Query("sort_clause") sort: String = "LIKE_COUNT.DESC"
    ): BaseResponse<BakeryReviewsResponse>

    /* 빵집 리뷰 목록 */
    @GET("bakeries/{bakery_id}/reviews")
    suspend fun getReviews(
        @Path("bakery_id") bakeryId: Int,
        @Query("cursor_value") cursorValue: String,
        @Query("page_size") pageSize: Int,
        @Query("sort_clause") sort: String
    ): BaseResponse<BakeryReviewsResponse>

    /* 내가 작성한 빵집 리뷰 목록 */
    @GET("bakeries/{bakery_id}/my-review")
    suspend fun getMyReviews(
        @Path("bakery_id") bakeryId: Int,
        @Query("cursor_value") cursorValue: String,
        @Query("page_size") pageSize: Int
    ): BaseResponse<BakeryReviewsResponse>

    /* 빵집 메뉴 조회 */
    @GET("bakeries/{bakery_id}/menus")
    suspend fun getMenus(@Path("bakery_id") bakeryId: Int): BaseResponse<List<BakeryMenuResponse>>
}