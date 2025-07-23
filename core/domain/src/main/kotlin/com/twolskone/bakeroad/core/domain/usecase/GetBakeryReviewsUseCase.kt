package com.twolskone.bakeroad.core.domain.usecase

import androidx.paging.PagingData
import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetBakeryReviewsUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    operator fun invoke(bakeryId: Int, reviewSortType: ReviewSortType): Flow<PagingData<BakeryReview>> =
        bakeryRepository.getReviews(
            myReview = false,
            bakeryId = bakeryId,
            sort = reviewSortType
        )
}