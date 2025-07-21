package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.bakery.BakeriesResponse
import com.twolskone.bakeroad.core.remote.model.bakery.BakeryDetailResponse
import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import kotlinx.coroutines.flow.Flow

interface BakeryDataSource {
    fun getRecommendPreferenceBakeries(areaCode: String): Flow<List<RecommendBakeryResponse>>
    fun getRecommendHotBakeries(areaCode: String): Flow<List<RecommendBakeryResponse>>
    suspend fun getPreferenceBakeries(areaCode: String, cursorId: Int, pageSize: Int): BakeriesResponse
    suspend fun getHotBakeries(areaCode: String, cursorId: Int, pageSize: Int): BakeriesResponse
    fun getBakeryDetail(bakeryId: Int): Flow<BakeryDetailResponse>
}