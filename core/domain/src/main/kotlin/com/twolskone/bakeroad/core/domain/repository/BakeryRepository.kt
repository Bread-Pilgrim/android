package com.twolskone.bakeroad.core.domain.repository

import androidx.paging.PagingData
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakeryType
import kotlinx.coroutines.flow.Flow

interface BakeryRepository {
    fun getBakeries(areaCodes: Set<Int>, bakeryType: BakeryType): Flow<PagingData<Bakery>>
}