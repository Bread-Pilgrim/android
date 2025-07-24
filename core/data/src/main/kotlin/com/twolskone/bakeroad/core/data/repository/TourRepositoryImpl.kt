package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.domain.repository.TourRepository
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import com.twolskone.bakeroad.core.remote.datasource.TourDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TourRepositoryImpl @Inject constructor(
    private val tourDataSource: TourDataSource
) : TourRepository {

    override fun getTourAreas(areaCodes: Set<Int>, tourCategories: Set<TourAreaCategory>): Flow<List<TourArea>> {
        return tourDataSource.getAreas(
            areaCodes = areaCodes.joinToString(separator = ","),
            tourCategories = tourCategories.joinToString(separator = ",") { category -> category.code }
        ).map { areas ->
            areas.map { area ->
                area.toExternalModel()
            }
        }
    }
}