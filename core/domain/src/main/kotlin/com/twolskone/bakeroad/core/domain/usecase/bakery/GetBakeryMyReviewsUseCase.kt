package com.twolskone.bakeroad.core.domain.usecase.bakery

import androidx.paging.PagingData
import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.BakeryReview
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetBakeryMyReviewsUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    operator fun invoke(bakeryId: Int): Flow<PagingData<BakeryReview>> =
        bakeryRepository.getReviews(
            myReview = true,
            bakeryId = bakeryId,
            sort = null
        )
}