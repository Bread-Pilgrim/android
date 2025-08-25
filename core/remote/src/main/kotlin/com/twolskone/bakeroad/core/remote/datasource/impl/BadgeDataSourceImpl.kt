package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.BadgeApi
import com.twolskone.bakeroad.core.remote.datasource.BadgeDataSource
import com.twolskone.bakeroad.core.remote.model.badge.BadgeResponse
import com.twolskone.bakeroad.core.remote.model.emitData
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class BadgeDataSourceImpl @Inject constructor(
    private val api: BadgeApi,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : BadgeDataSource {

    override fun getBadges(): Flow<List<BadgeResponse>> = flow {
        emitData(api.getBadges())
    }.flowOn(networkDispatcher)
}