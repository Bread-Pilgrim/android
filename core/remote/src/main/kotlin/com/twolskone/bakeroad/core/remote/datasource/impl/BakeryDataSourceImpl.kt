package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.BakeryApi
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryDetailResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryReviewResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryReviewsResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import com.twolskone.bakeroad.core.remote.model.emitData
import com.twolskone.bakeroad.core.remote.model.toData
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

private const val PreviewReviewCount = 3

internal class BakeryDataSourceImpl @Inject constructor(
    private val api: BakeryApi,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : BakeryDataSource {

    override fun getRecommendPreferenceBakeries(areaCodes: String): Flow<List<RecommendBakeryResponse>> = flow {
        emitData(api.getRecommendPreferenceBakeries(areaCode = areaCodes))
    }.flowOn(networkDispatcher)

    override fun getRecommendHotBakeries(areaCodes: String): Flow<List<RecommendBakeryResponse>> = flow {
        emitData(api.getRecommendHotBakeries(areaCode = areaCodes))
    }.flowOn(networkDispatcher)

    override suspend fun getPreferenceBakeries(
        areaCodes: String,
        cursorId: Int,
        pageSize: Int
    ): BakeriesResponse {
        val response = api.getPreferenceBakeries(
            areaCode = areaCodes,
            cursorId = cursorId,
            pageSize = pageSize
        )
        return response.toData()
    }

    override suspend fun getHotBakeries(areaCodes: String, cursorId: Int, pageSize: Int): BakeriesResponse {
        val response = api.getHotBakeries(
            areaCode = areaCodes,
            cursorId = cursorId,
            pageSize = pageSize
        )
        return response.toData()
    }

    override fun getBakeryDetail(bakeryId: Int): Flow<BakeryDetailResponse> = flow {
        emitData(api.getBakeryDetail(bakeryId = bakeryId))
    }.flowOn(networkDispatcher)

    override fun getPreviewReviews(bakeryId: Int): Flow<List<BakeryReviewResponse>> = flow {
        val items = api.getPreviewReviews(bakeryId = bakeryId)
            .toData()
            .items
            .take(PreviewReviewCount)
        emit(items)
    }.flowOn(networkDispatcher)

    override suspend fun getReviews(
        bakeryId: Int,
        sort: String,
        cursorId: Int,
        pageSize: Int
    ): BakeryReviewsResponse {
        val response = api.getReviews(
            bakeryId = bakeryId,
            sort = sort,
            cursorId = cursorId,
            pageSize = pageSize
        )
        return response.toData()
    }

    override suspend fun getMyReviews(
        bakeryId: Int,
        cursorId: Int,
        pageSize: Int
    ): BakeryReviewsResponse {
        val response = api.getMyReviews(
            bakeryId = bakeryId,
            cursorId = cursorId,
            pageSize = pageSize
        )
        return response.toData()
    }
}