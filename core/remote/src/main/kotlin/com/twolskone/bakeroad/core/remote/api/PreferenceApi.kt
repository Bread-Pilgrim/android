package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.preference.PreferenceOptionsResponse
import retrofit2.http.GET

internal interface PreferenceApi {

    /* 취향 항목 조회 */
    @GET("preferences/options")
    suspend fun getOptions(): BaseResponse<PreferenceOptionsResponse, Unit>
}