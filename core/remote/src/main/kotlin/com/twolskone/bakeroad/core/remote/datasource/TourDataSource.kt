package com.twolskone.bakeroad.core.remote.datasource

import com.twolskone.bakeroad.core.remote.model.tour.TourEventResponse
import com.twolskone.bakeroad.core.remote.model.tour.AttractionResponse
import kotlinx.coroutines.flow.Flow

interface TourDataSource {
    fun getAttractions(regionCode: String): Flow<List<AttractionResponse>>
    fun getEvent(regionCode: String): Flow<TourEventResponse>
}