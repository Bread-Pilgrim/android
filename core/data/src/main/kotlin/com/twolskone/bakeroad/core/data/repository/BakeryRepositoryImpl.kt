package com.twolskone.bakeroad.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.data.paging.BakeryPagingSource
import com.twolskone.bakeroad.core.data.paging.BakeryReviewPagingSource
import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class BakeryRepositoryImpl @Inject constructor(
    private val bakeryDataSource: BakeryDataSource,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : BakeryRepository {

    override fun getBakeries(areaCodes: String, bakeryType: BakeryType): Flow<PagingData<Bakery>> =
        Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                BakeryPagingSource(
                    bakeryDataSource = bakeryDataSource,
                    areaCodes = areaCodes,
                    bakeryType = bakeryType
                )
            }
        ).flow.flowOn(networkDispatcher)

    override fun getBakeryDetail(bakeryId: Int): Flow<BakeryDetail> {
        return bakeryDataSource.getBakeryDetail(bakeryId = bakeryId)
            .map { bakeryDetail -> bakeryDetail.toExternalModel() }
    }

    override fun getPreviewReviews(bakeryId: Int): Flow<List<BakeryReview>> {
        return bakeryDataSource.getPreviewReviews(bakeryId = bakeryId)
            .map { bakeryReviews ->
                bakeryReviews.map { bakeryReview ->
                    bakeryReview.toExternalModel()
                }
            }
    }

    override fun getReviews(
        myReview: Boolean,
        bakeryId: Int,
        sort: ReviewSortType?
    ): Flow<PagingData<BakeryReview>> =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                BakeryReviewPagingSource(
                    bakeryDataSource = bakeryDataSource,
                    myReview = myReview,
                    bakeryId = bakeryId,
                    sort = sort
                )
            }
        ).flow.flowOn(networkDispatcher)
}