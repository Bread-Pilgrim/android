package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.user.UserOnboardingRequest
import com.twolskone.bakeroad.core.remote.model.user.UserPreferencesRequest
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

internal interface UserApi {

    /* 온보딩 저장 (취향, 닉네임) */
    @POST("users/me/onboarding")
    suspend fun postOnboarding(@Body request: UserOnboardingRequest): BaseResponse<String>

    /* 취향 변경 */
    @PATCH("users/preferences")
    suspend fun patchPreferences(@Body request: UserPreferencesRequest): BaseResponse<Unit>
}