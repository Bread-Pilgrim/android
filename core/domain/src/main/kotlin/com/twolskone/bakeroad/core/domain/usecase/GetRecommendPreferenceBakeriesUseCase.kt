package com.twolskone.bakeroad.core.domain.usecase

import com.twolskone.bakeroad.core.domain.repository.HomeRepository
import com.twolskone.bakeroad.core.model.RecommendBakery
import com.twolskone.bakeroad.core.model.type.BakeryType
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class GetRecommendPreferenceBakeriesUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend operator fun invoke(areaCodes: Set<Int>): List<RecommendBakery> =
        homeRepository.getBakeries(
            type = BakeryType.PREFERENCE,
            areaCode = areaCodes.map { it.toString() }.toTypedArray()
        ).first()
}