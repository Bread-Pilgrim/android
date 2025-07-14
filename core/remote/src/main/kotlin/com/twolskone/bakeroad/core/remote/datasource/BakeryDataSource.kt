package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import kotlinx.coroutines.flow.Flow

interface BakeryDataSource {
    fun getRecommendPreferenceBakeries(areaCodes: Set<Int>): Flow<List<RecommendBakeryResponse>>
    fun getRecommendHotBakeries(areaCodes: Set<Int>): Flow<List<RecommendBakeryResponse>>
    suspend fun getPreferenceBakeries(areaCodes: Set<Int>, cursorId: Int, pageSize: Int): BakeriesResponse
}