package com.twolskone.bakeroad.core.domain.usecase

import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.ReviewMenu
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class GetBakeryReviewMenusUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    suspend operator fun invoke(bakeryId: Int): List<ReviewMenu> =
        bakeryRepository.getReviewMenus(bakeryId = bakeryId).first()
}