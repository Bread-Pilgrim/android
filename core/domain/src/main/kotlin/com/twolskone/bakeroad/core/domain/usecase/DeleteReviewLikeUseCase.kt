package com.twolskone.bakeroad.core.domain.usecase

import com.twolskone.bakeroad.core.domain.repository.ReviewRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class DeleteReviewLikeUseCase @Inject constructor(private val reviewRepository: ReviewRepository) {

    suspend operator fun invoke(reviewId: Int): Pair<Int, Boolean> =
        reviewRepository.deleteLike(reviewId = reviewId).first()
}