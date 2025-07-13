package com.twolskone.bakeroad.core.domain.repository

import com.twolskone.bakeroad.core.model.Area
import com.twolskone.bakeroad.core.model.RecommendBakery
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAreas(): Flow<List<Area>>
    fun getBakeries(type: BakeryType, vararg areaCode: String): Flow<List<RecommendBakery>>
    fun getTourAreas(vararg areaCode: String, tourAreaCategory: TourAreaCategory): Flow<List<TourArea>>
}