package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SearchApi {

    @GET("search/bakeries")
    suspend fun searchBakery(
        @Query("keyword") keyword: String,
        @Query("page_no") pageNo: Int,
        @Query("page_size") pageSize: Int,
    ): BaseResponse<BakeriesResponse>
}