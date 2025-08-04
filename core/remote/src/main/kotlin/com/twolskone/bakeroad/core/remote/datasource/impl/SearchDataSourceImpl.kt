package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.remote.api.SearchApi
import com.twolskone.bakeroad.core.remote.datasource.SearchDataSource
import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import com.twolskone.bakeroad.core.remote.model.toData
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

internal class SearchDataSourceImpl @Inject constructor(
    private val api: SearchApi,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : SearchDataSource {

    override suspend fun searchBakery(
        query: String,
        pageNo: Int,
        pageSize: Int
    ): BakeriesResponse {
        val response = api.searchBakery(
            keyword = query,
            pageNo = pageNo,
            pageSize = pageSize
        )
        return response.toData()
    }
}