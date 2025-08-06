package com.twolskone.bakeroad.core.domain.usecase.bakery

import androidx.paging.PagingData
import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakerySortType
import com.twolskone.bakeroad.core.model.type.MyBakeryType
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMyBakeriesUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    operator fun invoke(myBakeryType: MyBakeryType, sort: BakerySortType): Flow<PagingData<Bakery>> =
        bakeryRepository.getMyBakeries(myBakeryType = myBakeryType, sort = sort)
}