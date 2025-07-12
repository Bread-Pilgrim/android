package com.twolskone.bakeroad.core.domain.repository

import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAreas(): Flow<Map<Int, String>>
    fun getTourAreas(areaCode: String, tourAreaCategory: TourAreaCategory): Flow<List<TourArea>>
}