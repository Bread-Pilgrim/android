package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.tour.TourEventResponse
import com.twolskone.bakeroad.core.remote.model.tour.AttractionResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface TourApi {

    /* 주변 관광지 목록 */
    @GET("tour/region/{region_code}")
    suspend fun getAttractions(@Path("region_code") regionCode: String): BaseResponse<List<AttractionResponse>>

    /* 해당 지역의 행사정보 */
    @GET("tour/events/region/{region_code}")
    suspend fun getEvent(@Path("region_code") regionCode: String): BaseResponse<TourEventResponse>
}