package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface BakeryApi {

    @GET("bakery/recommend/preference")
    suspend fun getRecommendPreferenceBakeries(@Query("area_code") areaCode: String): BaseResponse<List<RecommendBakeryResponse>>

    @GET("bakery/recommend/hot")
    suspend fun getRecommendHotBakeries(@Query("area_code") areaCode: String): BaseResponse<List<RecommendBakeryResponse>>

    @GET("bakery/preference")
    suspend fun getPreferenceBakeries(
        @Query("area_code") areaCode: String,
        @Query("cursor_id") cursorId: Int,
        @Query("page_size") pageSize: Int
    ): BaseResponse<BakeriesResponse>
}