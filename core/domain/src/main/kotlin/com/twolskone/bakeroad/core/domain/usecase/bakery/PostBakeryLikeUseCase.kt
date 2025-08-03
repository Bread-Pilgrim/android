package com.twolskone.bakeroad.core.domain.usecase.bakery

import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class PostBakeryLikeUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    suspend operator fun invoke(bakeryId: Int): Pair<Int, Boolean> =
        bakeryRepository.postLike(bakeryId = bakeryId).first()
}