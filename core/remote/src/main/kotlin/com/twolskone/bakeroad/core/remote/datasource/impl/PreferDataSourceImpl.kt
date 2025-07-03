package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.PreferApi
import com.twolskone.bakeroad.core.remote.datasource.PreferDataSource
import com.twolskone.bakeroad.core.remote.model.emitData
import com.twolskone.bakeroad.core.remote.model.prefer.PreferOptionsResponse
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class PreferDataSourceImpl @Inject constructor(
    private val api: PreferApi,
    @Dispatcher(BakeRoadDispatcher.IO) val networkDispatcher: CoroutineDispatcher
) : PreferDataSource {

    override fun getOptions(): Flow<PreferOptionsResponse> = flow {
        emitData(api.getOptions())
    }.flowOn(networkDispatcher)
}