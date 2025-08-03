package com.twolskone.bakeroad.core.domain.usecase.review

import com.twolskone.bakeroad.core.domain.repository.ReviewRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class PostReviewLikeUseCase @Inject constructor(private val reviewRepository: ReviewRepository) {

    suspend operator fun invoke(reviewId: Int): Pair<Int, Boolean> =
        reviewRepository.postLike(reviewId = reviewId).first()
}