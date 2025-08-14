package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryDetailResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryLikeResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryMenuResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryReviewEligibilityResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryReviewsResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

internal interface BakeryApi {

    /* 내 취향 기반 추천 빵집 목록 */
    @GET("bakeries/recommend/preference")
    suspend fun getRecommendPreferenceBakeries(@Query("area_code") areaCode: String): BaseResponse<List<RecommendBakeryResponse>>

    /* Hot한 추천 빵집 목록 */
    @GET("bakeries/recommend/hot")
    suspend fun getRecommendHotBakeries(@Query("area_code") areaCode: String): BaseResponse<List<RecommendBakeryResponse>>

    /* 내 취향 기반 빵집 목록 */
    @GET("bakeries/preference")
    suspend fun getPreferenceBakeries(
        @Query("area_code") areaCode: String,
        @Query("page_no") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): BaseResponse<BakeriesResponse>

    /* Hot한 빵집 목록 */
    @GET("bakeries/hot")
    suspend fun getHotBakeries(
        @Query("area_code") areaCode: String,
        @Query("page_no") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): BaseResponse<BakeriesResponse>

    /* 빵집 상세 조회 */
    @GET("bakeries/{bakeryId}")
    suspend fun getBakeryDetail(@Path("bakeryId") bakeryId: Int): BaseResponse<BakeryDetailResponse>

    /* 빵집 리뷰 미리보기 */
    @GET("bakeries/{bakery_id}/reviews")
    suspend fun getPreviewReviews(
        @Path("bakery_id") bakeryId: Int,
        @Query("page_no") pageNo: Int,
        @Query("page_size") pageSize: Int = 5,
        @Query("sort_clause") sort: String = "LIKE_COUNT.DESC"
    ): BaseResponse<BakeryReviewsResponse>

    /* 빵집 리뷰 목록 */
    @GET("bakeries/{bakery_id}/reviews")
    suspend fun getReviews(
        @Path("bakery_id") bakeryId: Int,
        @Query("page_no") pageNo: Int,
        @Query("page_size") pageSize: Int,
        @Query("sort_clause") sort: String
    ): BaseResponse<BakeryReviewsResponse>

    /* 내가 작성한 빵집 리뷰 목록 */
    @GET("bakeries/{bakery_id}/my-review")
    suspend fun getMyReviews(
        @Path("bakery_id") bakeryId: Int,
        @Query("page_no") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): BaseResponse<BakeryReviewsResponse>

    /* 빵집 메뉴 조회 */
    @GET("bakeries/{bakery_id}/menus")
    suspend fun getMenus(@Path("bakery_id") bakeryId: Int): BaseResponse<List<BakeryMenuResponse>>

    /* 빵집 리뷰 작성 */
    @Multipart
    @POST("bakeries/{bakery_id}/reviews")
    suspend fun postReview(
        @Path("bakery_id") bakeryId: Int,
        @Part("rating") rating: RequestBody,
        @Part("content") content: RequestBody,
        @Part("is_private") isPrivate: RequestBody,
        @Part("consumed_menus") consumedMenus: RequestBody,
        @Part reviewImgs: List<MultipartBody.Part>
    ): BaseResponse<Unit>

    /* 빵집 좋아요 */
    @POST("bakeries/{bakery_id}/like")
    suspend fun postLike(@Path("bakery_id") bakeryId: Int): BaseResponse<BakeryLikeResponse>

    /* 빵집 좋아요 취소 */
    @DELETE("bakeries/{bakery_id}/like")
    suspend fun deleteLike(@Path("bakery_id") bakeryId: Int): BaseResponse<BakeryLikeResponse>

    /* 빵집 리뷰 작성 가능 여부 체크 */
    @GET("bakeries/{bakery_id}/review/eligibility")
    suspend fun checkReviewEligibility(@Path("bakery_id") bakeryId: Int): BaseResponse<BakeryReviewEligibilityResponse>

    /* 내가 찜한 빵집 목록 */
    @GET("bakeries/like")
    suspend fun getLikeBakeries(
        @Query("page_no") pageNo: Int,
        @Query("page_size") pageSize: Int,
        @Query("sort_clause") sort: String
    ): BaseResponse<BakeriesResponse>

    /* 내가 방문한 빵집 목록 */
    @GET("bakeries/visited")
    suspend fun getVisitedBakeries(
        @Query("page_no") pageNo: Int,
        @Query("page_size") pageSize: Int,
        @Query("sort_clause") sort: String
    ): BaseResponse<BakeriesResponse>
}