package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.bakery.RecommendBakeryResponse
import kotlinx.coroutines.flow.Flow

interface BakeryDataSource {
    fun getRecommendPreferenceBakeries(areaCodes: Array<out String>): Flow<List<RecommendBakeryResponse>>
    fun getRecommendAreaBakeries(areaCodes: Array<out String>): Flow<List<RecommendBakeryResponse>>
}