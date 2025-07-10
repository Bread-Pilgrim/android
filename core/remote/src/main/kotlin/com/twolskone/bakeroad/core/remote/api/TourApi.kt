package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.tour.TourAreaResponse
import com.twolskone.bakeroad.core.remote.model.tour.TourAreaEventResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface TourApi {

    /* 주변 관광지 목록 */
    @GET("tour/area")
    suspend fun getAreas(
        @Query("area_code") areaCode: String,
        @Query("tour_cat") tourCategory: String
    ): BaseResponse<List<TourAreaResponse>>

    /* 해당 지역의 행사정보 */
    @GET("tour/events/area")
    suspend fun getAreaEvent(@Query("area_code") areaCode: String): BaseResponse<TourAreaEventResponse>
}