package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.extra.BadgeExtraResponse
import com.twolskone.bakeroad.core.remote.model.user.MyBakeryReviewsResponse
import com.twolskone.bakeroad.core.remote.model.user.OnboardingRequest
import com.twolskone.bakeroad.core.remote.model.user.PreferencesGetResponse
import com.twolskone.bakeroad.core.remote.model.user.PreferencesPatchRequest
import com.twolskone.bakeroad.core.remote.model.user.ProfileResponse
import com.twolskone.bakeroad.core.remote.model.user.ReportMonthlyListResponse
import com.twolskone.bakeroad.core.remote.model.user.ReportResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface UserApi {

    /* 온보딩 저장 (취향, 닉네임) */
    @POST("users/me/onboarding")
    suspend fun postOnboarding(@Body request: OnboardingRequest): BaseResponse<Unit, List<BadgeExtraResponse>>

    /* 취향 조회 */
    @GET("users/preferences")
    suspend fun getPreferences(): BaseResponse<PreferencesGetResponse, Unit>

    /* 취향 변경 */
    @PATCH("users/preferences")
    suspend fun patchPreferences(@Body request: PreferencesPatchRequest): BaseResponse<Unit, Unit>

    /* 내가 쓴 리뷰 목록 */
    @GET("users/me/reviews")
    suspend fun getMyReviews(
        @Query("cursor_value") cursorValue: String,
        @Query("page_size") pageSize: Int
    ): BaseResponse<MyBakeryReviewsResponse, Unit>

    /* 프로필 조회 */
    @GET("users/me")
    suspend fun getProfile(): BaseResponse<ProfileResponse, Unit>

    /* 빵말정산 월 리스트 조회 */
    @GET("users/me/bread-report/monthly")
    suspend fun getReportMonthlyList(
        @Query("cursor_value") cursorValue: String,
        @Query("page_size") pageSize: Int
    ): BaseResponse<ReportMonthlyListResponse, Unit>

    /* 빵말정산 조회 */
    @GET("users/me/bread-report")
    suspend fun getReport(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): BaseResponse<ReportResponse, Unit>

    /* 대표뱃지 설정 */
    @POST("users/me/badges/{badge_id}/represent")
    suspend fun enableBadge(@Path("badge_id") badgeId: Int): BaseResponse<Unit, Unit>

    /* 대표뱃지 해제 */
    @POST("users/me/badges/{badge_id}/derepresent")
    suspend fun disableBadge(@Path("badge_id") badgeId: Int): BaseResponse<Unit, Unit>
}