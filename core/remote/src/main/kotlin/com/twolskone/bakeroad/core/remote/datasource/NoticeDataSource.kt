package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.notice.NoticeResponse
import kotlinx.coroutines.flow.Flow

interface NoticeDataSource {
    fun getNotices(): Flow<List<NoticeResponse>>
}