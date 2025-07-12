package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.AreaApi
import com.twolskone.bakeroad.core.remote.datasource.AreaDataSource
import com.twolskone.bakeroad.core.remote.model.area.AreaResponse
import com.twolskone.bakeroad.core.remote.model.emitData
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class AreaDataSourceImpl @Inject constructor(
    private val api: AreaApi,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : AreaDataSource {

    override fun getAreas(): Flow<List<AreaResponse>> = flow {
        emitData(api.getAreas())
    }.flowOn(networkDispatcher)
}