package com.twolskone.bakeroad.core.domain.usecase.bakery

import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.BakeryReview
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class GetBakeryPreviewReviewsUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    suspend operator fun invoke(bakeryId: Int): List<BakeryReview> =
        bakeryRepository.getPreviewReviews(bakeryId).first()
}