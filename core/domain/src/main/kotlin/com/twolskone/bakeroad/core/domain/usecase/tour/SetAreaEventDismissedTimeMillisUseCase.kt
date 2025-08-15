package com.twolskone.bakeroad.core.domain.usecase.tour

import com.twolskone.bakeroad.core.domain.repository.TourRepository
import javax.inject.Inject

class SetAreaEventDismissedTimeMillisUseCase @Inject constructor(private val tourRepository: TourRepository) {

    suspend operator fun invoke(timeMillis: Long) =
        tourRepository.setAreaEventDismissedTimeMillis(timeMillis = timeMillis)
}