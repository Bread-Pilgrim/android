package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.area.AreaResponse
import retrofit2.http.GET

internal interface AreaApi {

    @GET("areas")
    suspend fun getAreas(): BaseResponse<List<AreaResponse>>
}