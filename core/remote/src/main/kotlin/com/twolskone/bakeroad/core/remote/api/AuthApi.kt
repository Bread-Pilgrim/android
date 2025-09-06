package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.auth.LoginRequest
import com.twolskone.bakeroad.core.remote.model.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Header("access-token") accessToken: String,
        @Body request: LoginRequest
    ): BaseResponse<LoginResponse, Unit>

    @POST("auth/token/verify")
    suspend fun verify(
        @Header("access-token") accessToken: String,
        @Header("refresh-token") refreshToken: String
    ): BaseResponse<Unit, Unit>
}