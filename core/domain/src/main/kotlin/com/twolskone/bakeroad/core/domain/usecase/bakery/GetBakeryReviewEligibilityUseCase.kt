package com.twolskone.bakeroad.core.domain.usecase.bakery

import com.twolskone.bakeroad.core.common.kotlin.extension.orFalse
import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

class GetBakeryReviewEligibilityUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    suspend operator fun invoke(bakeryId: Int): Boolean =
        bakeryRepository.checkReviewEligibility(bakeryId = bakeryId).firstOrNull().orFalse()
}