package com.twolskone.bakeroad.core.remote.api

import com.twolskone.bakeroad.core.remote.model.BaseResponse
import com.twolskone.bakeroad.core.remote.model.notice.NoticeResponse
import retrofit2.http.GET

internal interface NoticeApi {

    /* 공지사항 조회 */
    @GET("notices")
    suspend fun getNotices(): BaseResponse<List<NoticeResponse>>
}