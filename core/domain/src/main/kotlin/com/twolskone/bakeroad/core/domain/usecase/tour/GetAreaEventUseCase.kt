package com.twolskone.bakeroad.core.domain.usecase.tour

import com.twolskone.bakeroad.core.domain.repository.TourRepository
import com.twolskone.bakeroad.core.model.AreaEvent
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class GetAreaEventUseCase @Inject constructor(private val tourRepository: TourRepository) {

    suspend operator fun invoke(areaCodes: Set<Int>): AreaEvent =
        tourRepository.getAreaEvent(areaCodes = areaCodes).first()
}