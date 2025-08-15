package com.twolskone.bakeroad.core.domain.repository

import com.twolskone.bakeroad.core.model.AreaEvent
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import kotlinx.coroutines.flow.Flow

interface TourRepository {
    fun getTourAreas(areaCodes: Set<Int>, tourCategories: Set<TourAreaCategory>): Flow<List<TourArea>>
    fun getAreaEvent(areaCodes: Set<Int>): Flow<AreaEvent>
    suspend fun setAreaEventDismissedTimeMillis(timeMillis: Long)
}