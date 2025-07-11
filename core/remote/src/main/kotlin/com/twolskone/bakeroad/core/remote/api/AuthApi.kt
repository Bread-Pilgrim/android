package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.auth.AuthLoginRequest
import com.twolskone.bakeroad.core.remote.model.auth.AuthLoginResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Header("access-token") accessToken: String,
        @Body request: AuthLoginRequest
    ): BaseResponse<AuthLoginResponse>

    @POST("auth/token/verify")
    suspend fun verify(
        @Header("access-token") accessToken: String,
        @Header("refresh-token") refreshToken: String
    ): BaseResponse<Unit>
}