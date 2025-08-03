package com.twolskone.bakeroad.core.domain.usecase.bakery

import androidx.paging.PagingData
import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakeryType
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetBakeriesUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    operator fun invoke(areaCodes: String, bakeryType: BakeryType): Flow<PagingData<Bakery>> =
        bakeryRepository.getBakeries(areaCodes = areaCodes, bakeryType = bakeryType)
}