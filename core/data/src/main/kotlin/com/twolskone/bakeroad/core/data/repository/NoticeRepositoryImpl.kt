package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.domain.repository.NoticeRepository
import com.twolskone.bakeroad.core.model.Notice
import com.twolskone.bakeroad.core.remote.datasource.NoticeDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class NoticeRepositoryImpl @Inject constructor(
    private val noticeDataSource: NoticeDataSource
) : NoticeRepository {

    override fun getNotices(): Flow<List<Notice>> {
        return noticeDataSource.getNotices()
            .map { notices ->
                notices.map {
                    it.toExternalModel()
                }
            }
    }
}