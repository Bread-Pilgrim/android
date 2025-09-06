package com.twolskone.bakeroad.core.domain.usecase.bakery

import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.model.WriteBakeryReview
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class PostBakeryReviewUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    suspend operator fun invoke(bakeryId: Int, review: WriteBakeryReview): List<Badge> =
        bakeryRepository.postReview(bakeryId = bakeryId, review = review).first()
}