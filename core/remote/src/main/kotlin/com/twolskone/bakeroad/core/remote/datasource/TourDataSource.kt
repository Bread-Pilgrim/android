package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.tour.TourAreaEventResponse
import com.twolskone.bakeroad.core.remote.model.tour.TourAreaResponse
import kotlinx.coroutines.flow.Flow

interface TourDataSource {
    fun getAreas(areaCodes: String, tourCategories: String): Flow<List<TourAreaResponse>>
    fun getAreaEvent(areaCodes: String): Flow<TourAreaEventResponse>
}