package com.twolskone.bakeroad.core.domain.usecase.tour

import com.twolskone.bakeroad.core.domain.repository.TourRepository
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class GetTourAreasUseCase @Inject constructor(private val tourRepository: TourRepository) {

    suspend operator fun invoke(areaCodes: Set<Int>, tourCategories: Set<TourAreaCategory>): List<TourArea> =
        tourRepository.getTourAreas(
            areaCodes = areaCodes,
            tourCategories = tourCategories
        ).first()
}