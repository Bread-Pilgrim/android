package com.twolskone.bakeroad.core.domain.usecase.user

import com.twolskone.bakeroad.core.domain.repository.UserRepository
import com.twolskone.bakeroad.core.model.MyBakeryReview
import com.twolskone.bakeroad.core.model.paging.Paging
import javax.inject.Inject
import kotlinx.coroutines.flow.first

/**
 * 내가 쓴 리뷰 조회
 */
class GetMyBakeryReviewsUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(page: Int): Paging<MyBakeryReview> =
        userRepository.getMyReviews(page = page).first()
}