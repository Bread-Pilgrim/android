package com.twolskone.bakeroad.core.domain.usecase

import com.twolskone.bakeroad.core.domain.repository.HomeRepository
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class GetTourAreasUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend operator fun invoke(areaCodes: Set<Int>, tourAreaCategory: TourAreaCategory): List<TourArea> =
        homeRepository.getTourAreas(
            areaCode = areaCodes.map { it.toString() }.toTypedArray(),
            tourAreaCategory = tourAreaCategory
        ).first()
}