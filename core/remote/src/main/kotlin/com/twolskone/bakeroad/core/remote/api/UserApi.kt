package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.user.MyBakeryReviewsResponse
import com.twolskone.bakeroad.core.remote.model.user.OnboardingRequest
import com.twolskone.bakeroad.core.remote.model.user.PreferencesGetResponse
import com.twolskone.bakeroad.core.remote.model.user.PreferencesPatchRequest
import com.twolskone.bakeroad.core.remote.model.user.ProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

internal interface UserApi {

    /* 온보딩 저장 (취향, 닉네임) */
    @POST("users/me/onboarding")
    suspend fun postOnboarding(@Body request: OnboardingRequest): BaseResponse<Unit>

    /* 취향 조회 */
    @GET("users/preferences")
    suspend fun getPreferences(): BaseResponse<PreferencesGetResponse>

    /* 취향 변경 */
    @PATCH("users/preferences")
    suspend fun patchPreferences(@Body request: PreferencesPatchRequest): BaseResponse<Unit>

    /* 내가 쓴 리뷰 목록 */
    @GET("users/me/reviews")
    suspend fun getMyReviews(
        @Query("page_no") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): BaseResponse<MyBakeryReviewsResponse>

    /* 프로필 조회 */
    @GET("users/me")
    suspend fun getProfile(): BaseResponse<ProfileResponse>
}