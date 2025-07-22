package com.twolskone.bakeroad.core.data.repository

import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.domain.repository.HomeRepository
import com.twolskone.bakeroad.core.model.Area
import com.twolskone.bakeroad.core.model.RecommendBakery
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import com.twolskone.bakeroad.core.remote.datasource.AreaDataSource
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import com.twolskone.bakeroad.core.remote.datasource.TourDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class HomeRepositoryImpl @Inject constructor(
    private val areaDataSource: AreaDataSource,
    private val bakeryDataSource: BakeryDataSource,
    private val tourDataSource: TourDataSource
) : HomeRepository {

    override fun getAreas(): Flow<List<Area>> {
        return areaDataSource.getAreas()
            .map { areas ->
                areas.map {
                    Area(code = it.areaCode, name = it.areaName)
                }
            }
    }

    override fun getBakeries(areaCodes: Set<Int>, type: BakeryType): Flow<List<RecommendBakery>> {
        return when (type) {
            BakeryType.PREFERENCE -> bakeryDataSource.getRecommendPreferenceBakeries(areaCodes = areaCodes.joinToString(separator = ","))
                .map { bakeries ->
                    bakeries.map { bakery ->
                        bakery.toExternalModel()
                    }
                }

            BakeryType.HOT -> bakeryDataSource.getRecommendHotBakeries(areaCodes = areaCodes.joinToString(separator = ","))
                .map { bakeries ->
                    bakeries.map { bakery ->
                        bakery.toExternalModel()
                    }
                }
        }
    }

    override fun getTourAreas(areaCodes: Set<Int>, tourCategories: Set<TourAreaCategory>): Flow<List<TourArea>> {
        return tourDataSource.getAreas(
            areaCodes = areaCodes.joinToString(separator = ","),
            tourCategories = tourCategories.map { category -> category.code }.joinToString(separator = ",")
        ).map { areas ->
            areas.map { area ->
                area.toExternalModel()
            }
        }
    }
}