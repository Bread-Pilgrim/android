package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.user.UserOnboardingRequest
import retrofit2.http.Body
import retrofit2.http.POST

internal interface UserApi {

    @POST("users/me/onboarding")
    suspend fun postOnboarding(@Body request: UserOnboardingRequest): BaseResponse<String>
}