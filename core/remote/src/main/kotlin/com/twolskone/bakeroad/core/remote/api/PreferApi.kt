package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.prefer.PreferOptionsResponse
import retrofit2.http.GET

internal interface PreferApi {

    @GET("prefer/options")
    suspend fun getOptions(): BaseResponse<PreferOptionsResponse>
}