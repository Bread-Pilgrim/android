package com.twolskone.bakeroad.core.remote.datasource.impl

import com.twolskone.bakeroad.core.remote.api.SearchApi
import com.twolskone.bakeroad.core.remote.datasource.SearchDataSource
import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import com.twolskone.bakeroad.core.remote.model.toData
import javax.inject.Inject

internal class SearchDataSourceImpl @Inject constructor(
    private val api: SearchApi
) : SearchDataSource {

    override suspend fun searchBakery(
        query: String,
        cursorValue: String,
        pageSize: Int
    ): BakeriesResponse {
        val response = api.searchBakery(
            keyword = query,
            cursorValue = cursorValue,
            pageSize = pageSize
        )
        return response.toData()
    }
}