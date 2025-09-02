package com.twolskone.bakeroad.core.domain.repository

import com.twolskone.bakeroad.core.model.Notice
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {
    fun getNotices(): Flow<List<Notice>>
}