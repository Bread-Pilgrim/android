package com.twolskone.bakeroad.core.domain.usecase.bakery

import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.RecommendBakery
import javax.inject.Inject
import kotlinx.coroutines.flow.first

/**
 * 최근 조회한 빵집
 */
class GetRecentBakeriesUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    suspend operator fun invoke(): List<RecommendBakery> =
        bakeryRepository.getRecentBakeries().first()
}