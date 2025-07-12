package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.domain.repository.HomeRepository
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import com.twolskone.bakeroad.core.remote.datasource.AreaDataSource
import com.twolskone.bakeroad.core.remote.datasource.TourDataSource
import com.twolskone.bakeroad.core.remote.model.area.AreaResponse
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class HomeRepositoryImpl @Inject constructor(
    private val areaDataSource: AreaDataSource,
    private val tourDataSource: TourDataSource
) : HomeRepository {

    override fun getAreas(): Flow<Map<Int, String>> {
        return areaDataSource.getAreas()
            .map { areas ->
                areas.associateBy(
                    keySelector = AreaResponse::areaCode,
                    valueTransform = AreaResponse::areaName
                )
            }
    }

    override fun getTourAreas(areaCode: String, tourAreaCategory: TourAreaCategory): Flow<List<TourArea>> {
        return tourDataSource.getAreas(areaCode = areaCode, tourCategory = tourAreaCategory.code)
            .map { areas ->
                areas.map { area -> area.toExternalModel() }
            }
    }
}