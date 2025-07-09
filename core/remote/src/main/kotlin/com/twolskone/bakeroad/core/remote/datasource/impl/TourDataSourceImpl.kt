package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.TourApi
import com.twolskone.bakeroad.core.remote.datasource.TourDataSource
import com.twolskone.bakeroad.core.remote.model.emitData
import com.twolskone.bakeroad.core.remote.model.tour.TourEventResponse
import com.twolskone.bakeroad.core.remote.model.tour.AttractionResponse
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class TourDataSourceImpl @Inject constructor(
    private val api: TourApi,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : TourDataSource {

    override fun getAttractions(regionCode: String): Flow<List<AttractionResponse>> = flow {
        emitData(api.getAttractions(regionCode = regionCode))
    }.flowOn(networkDispatcher)

    override fun getEvent(regionCode: String): Flow<TourEventResponse> = flow {
        emitData(api.getEvent(regionCode = regionCode))
    }.flowOn(networkDispatcher)
}