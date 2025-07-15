package com.twolskone.bakeroad.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.data.paging.BakeryPagingSource
import com.twolskone.bakeroad.core.data.paging.PageSize
import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

internal class BakeryRepositoryImpl @Inject constructor(
    private val bakeryDataSource: BakeryDataSource,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : BakeryRepository {

    override fun getBakeries(areaCode: String, bakeryType: BakeryType): Flow<PagingData<Bakery>> =
        Pager(
            config = PagingConfig(
                pageSize = PageSize,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                BakeryPagingSource(
                    bakeryDataSource = bakeryDataSource,
                    areaCode = areaCode,
                    bakeryType = bakeryType
                )
            }
        ).flow.flowOn(networkDispatcher)
}