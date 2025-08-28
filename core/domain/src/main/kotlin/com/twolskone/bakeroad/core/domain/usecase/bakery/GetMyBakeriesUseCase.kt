package com.twolskone.bakeroad.core.domain.usecase.bakery

import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.paging.CursorPaging
import com.twolskone.bakeroad.core.model.type.BakerySortType
import com.twolskone.bakeroad.core.model.type.MyBakeryType
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class GetMyBakeriesUseCase @Inject constructor(private val bakeryRepository: BakeryRepository) {

    suspend operator fun invoke(
        cursor: String,
        myBakeryType: MyBakeryType,
        sort: BakerySortType
    ): CursorPaging<Bakery> = bakeryRepository.getMyBakeries(
        cursor = cursor,
        myBakeryType = myBakeryType,
        sort = sort
    ).first()
}