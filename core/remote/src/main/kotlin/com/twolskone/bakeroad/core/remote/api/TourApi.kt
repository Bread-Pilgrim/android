package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.tour.TourAreaEventResponse
import com.twolskone.bakeroad.core.remote.model.tour.TourAreaResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface TourApi {

    /* 주변 관광지 목록 */
    @GET("tours/area")
    suspend fun getAreas(
        @Query("area_code") areaCode: String,
        @Query("tour_cat") tourCategory: String
    ): BaseResponse<List<TourAreaResponse>, Unit>

    /* 해당 지역의 행사정보 */
    @GET("tours/events/area")
    suspend fun getAreaEvent(@Query("area_code") areaCode: String): BaseResponse<TourAreaEventResponse, Unit>
}