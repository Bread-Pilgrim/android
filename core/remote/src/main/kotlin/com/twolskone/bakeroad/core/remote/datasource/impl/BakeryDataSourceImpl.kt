package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.BakeryApi
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryDetailResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import com.twolskone.bakeroad.core.remote.model.emitData
import com.twolskone.bakeroad.core.remote.model.toData
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class BakeryDataSourceImpl @Inject constructor(
    private val api: BakeryApi,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : BakeryDataSource {

    override fun getRecommendPreferenceBakeries(areaCode: String): Flow<List<RecommendBakeryResponse>> = flow {
        emitData(api.getRecommendPreferenceBakeries(areaCode = areaCode))
    }.flowOn(networkDispatcher)

    override fun getRecommendHotBakeries(areaCode: String): Flow<List<RecommendBakeryResponse>> = flow {
        emitData(api.getRecommendHotBakeries(areaCode = areaCode))
    }.flowOn(networkDispatcher)

    override suspend fun getPreferenceBakeries(
        areaCode: String,
        cursorId: Int,
        pageSize: Int
    ): BakeriesResponse {
        val response = api.getPreferenceBakeries(
            areaCode = areaCode,
            cursorId = cursorId,
            pageSize = pageSize
        )
        return response.toData()
    }

    override suspend fun getHotBakeries(areaCode: String, cursorId: Int, pageSize: Int): BakeriesResponse {
        val response = api.getHotBakeries(
            areaCode = areaCode,
            cursorId = cursorId,
            pageSize = pageSize
        )
        return response.toData()
    }

    override fun getBakeryDetail(bakeryId: Int): Flow<BakeryDetailResponse> = flow {
        emitData(api.getBakeryDetail(bakeryId = bakeryId))
    }.flowOn(networkDispatcher)
}