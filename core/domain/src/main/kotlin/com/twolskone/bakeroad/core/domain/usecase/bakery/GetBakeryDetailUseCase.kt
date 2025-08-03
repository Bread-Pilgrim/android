package com.twolskone.bakeroad.core.domain.usecase.bakery

import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.BakeryDetail
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class GetBakeryDetailUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    suspend operator fun invoke(bakeryId: Int): BakeryDetail =
        bakeryRepository.getBakeryDetail(bakeryId).first()
}