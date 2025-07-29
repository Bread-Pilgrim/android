package com.twolskone.bakeroad.core.domain.usecase

import com.twolskone.bakeroad.core.common.kotlin.extension.orFalse
import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.WriteBakeryReview
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class WriteBakeryReviewUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    suspend operator fun invoke(bakeryId: Int, review: WriteBakeryReview): Boolean =
        bakeryRepository.writeReview(bakeryId = bakeryId, review = review)
            .map { true }
            .firstOrNull()
            .orFalse()
}