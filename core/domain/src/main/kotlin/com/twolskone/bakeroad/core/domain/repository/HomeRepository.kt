package com.twolskone.bakeroad.core.domain.repository

import com.twolskone.bakeroad.core.model.Area
import com.twolskone.bakeroad.core.model.RecommendBakery
import com.twolskone.bakeroad.core.model.type.BakeryType
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAreas(): Flow<List<Area>>
    fun getBakeries(areaCodes: Set<Int>, type: BakeryType): Flow<List<RecommendBakery>>
}