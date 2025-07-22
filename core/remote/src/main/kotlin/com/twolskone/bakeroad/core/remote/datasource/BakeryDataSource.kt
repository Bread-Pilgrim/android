package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryDetailResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import kotlinx.coroutines.flow.Flow

interface BakeryDataSource {
    fun getRecommendPreferenceBakeries(areaCodes: String): Flow<List<RecommendBakeryResponse>>
    fun getRecommendHotBakeries(areaCodes: String): Flow<List<RecommendBakeryResponse>>
    suspend fun getPreferenceBakeries(areaCodes: String, cursorId: Int, pageSize: Int): BakeriesResponse
    suspend fun getHotBakeries(areaCodes: String, cursorId: Int, pageSize: Int): BakeriesResponse
    fun getBakeryDetail(bakeryId: Int): Flow<BakeryDetailResponse>
}