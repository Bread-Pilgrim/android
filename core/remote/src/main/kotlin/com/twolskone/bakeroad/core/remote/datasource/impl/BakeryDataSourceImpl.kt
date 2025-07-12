package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.BakeryApi
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import com.twolskone.bakeroad.core.remote.model.emitData
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class BakeryDataSourceImpl @Inject constructor(
    private val api: BakeryApi,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : BakeryDataSource {

    override fun getRecommendPreferenceBakeries(areaCodes: Array<out String>): Flow<List<RecommendBakeryResponse>> = flow {
        emitData(api.getRecommendPreferenceBakeries(areaCode = areaCodes.joinToString(separator = ",")))
    }.flowOn(networkDispatcher)

    override fun getRecommendAreaBakeries(areaCodes: Array<out String>): Flow<List<RecommendBakeryResponse>> = flow {
        emitData(api.getRecommendAreaBakeries(areaCode = areaCodes.joinToString(separator = ",")))
    }.flowOn(networkDispatcher)
}