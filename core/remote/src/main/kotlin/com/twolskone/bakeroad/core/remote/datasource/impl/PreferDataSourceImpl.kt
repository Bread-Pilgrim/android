package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.PreferenceApi
import com.twolskone.bakeroad.core.remote.datasource.PreferDataSource
import com.twolskone.bakeroad.core.remote.model.emitData
import com.twolskone.bakeroad.core.remote.model.preference.PreferenceOptionsResponse
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class PreferDataSourceImpl @Inject constructor(
    private val api: PreferenceApi,
    @Dispatcher(BakeRoadDispatcher.IO) val networkDispatcher: CoroutineDispatcher
) : PreferDataSource {

    override fun getOptions(): Flow<PreferenceOptionsResponse> = flow {
        emitData(api.getOptions())
    }.flowOn(networkDispatcher)
}