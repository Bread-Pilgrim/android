package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse

interface SearchDataSource {
    suspend fun searchBakery(query: String, pageNo: Int, pageSize: Int): BakeriesResponse
}