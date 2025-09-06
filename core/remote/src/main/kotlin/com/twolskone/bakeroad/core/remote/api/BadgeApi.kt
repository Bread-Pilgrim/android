package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.badge.BadgeResponse
import retrofit2.http.GET

internal interface BadgeApi {

    @GET("badges")
    suspend fun getBadges(): BaseResponse<List<BadgeResponse>, Unit>
}