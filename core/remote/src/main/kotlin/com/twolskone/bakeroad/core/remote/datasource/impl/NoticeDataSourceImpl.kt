package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.NoticeApi
import com.twolskone.bakeroad.core.remote.datasource.NoticeDataSource
import com.twolskone.bakeroad.core.remote.model.emitData
import com.twolskone.bakeroad.core.remote.model.notice.NoticeResponse
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class NoticeDataSourceImpl @Inject constructor(
    private val api: NoticeApi,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : NoticeDataSource {

    override fun getNotices(): Flow<List<NoticeResponse>> = flow {
        emitData(api.getNotices())
    }.flowOn(networkDispatcher)
}