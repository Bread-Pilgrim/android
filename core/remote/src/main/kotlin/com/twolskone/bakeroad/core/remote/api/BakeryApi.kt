package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface BakeryApi {

    @GET("bakery/recommend/preference")
    suspend fun getRecommendPreferenceBakeries(@Query("area_code") areaCode: String): BaseResponse<List<RecommendBakeryResponse>>

    @GET("bakery/recommend/area")
    suspend fun getRecommendAreaBakeries(@Query("area_code") areaCode: String): BaseResponse<List<RecommendBakeryResponse>>
}