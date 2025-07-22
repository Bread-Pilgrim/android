package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.TourApi
import com.twolskone.bakeroad.core.remote.datasource.TourDataSource
import com.twolskone.bakeroad.core.remote.model.emitData
import com.twolskone.bakeroad.core.remote.model.tour.TourAreaEventResponse
import com.twolskone.bakeroad.core.remote.model.tour.TourAreaResponse
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class TourDataSourceImpl @Inject constructor(
    private val api: TourApi,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : TourDataSource {

    override fun getAreas(areaCodes: String, tourCategories: String): Flow<List<TourAreaResponse>> = flow {
        emitData(api.getAreas(areaCode = areaCodes, tourCategory = tourCategories))
    }.flowOn(networkDispatcher)

    override fun getAreaEvent(areaCodes: String): Flow<TourAreaEventResponse> = flow {
        emitData(api.getAreaEvent(areaCode = areaCodes))
    }.flowOn(networkDispatcher)
}