package com.twolskone.bakeroad.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.data.mapper.toReviewMenu
import com.twolskone.bakeroad.core.data.paging.BakeryPagingSource
import com.twolskone.bakeroad.core.data.paging.BakeryReviewPagingSource
import com.twolskone.bakeroad.core.data.paging.DefaultPageSize
import com.twolskone.bakeroad.core.data.paging.defaultPagingConfig
import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.RecommendBakery
import com.twolskone.bakeroad.core.model.ReviewMenu
import com.twolskone.bakeroad.core.model.WriteBakeryReview
import com.twolskone.bakeroad.core.model.paging.CursorPaging
import com.twolskone.bakeroad.core.model.type.BakerySortType
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.model.type.MyBakeryType
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import com.twolskone.bakeroad.core.remote.model.bakery.WriteBakeryReviewRequest
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class BakeryRepositoryImpl @Inject constructor(
    private val bakeryDataSource: BakeryDataSource,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : BakeryRepository {

    override fun getBakeries(areaCodes: String, bakeryType: BakeryType): Flow<PagingData<Bakery>> =
        Pager(
            config = defaultPagingConfig(),
            pagingSourceFactory = {
                BakeryPagingSource(
                    pageSize = DefaultPageSize,
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
        val previewReviewCount = 3
        return bakeryDataSource.getPreviewReviews(bakeryId = bakeryId)
            .map { response ->
                response.items
                    .take(previewReviewCount)
                    .map { bakeryReview ->
                        bakeryReview.toExternalModel(
                            avgRating = response.avgRating,
                            reviewCount = response.reviewCount
                        )
                    }
            }
    }

    override fun getReviews(
        myReview: Boolean,
        bakeryId: Int,
        sort: ReviewSortType?
    ): Flow<PagingData<BakeryReview>> =
        Pager(
            config = defaultPagingConfig(pageSize = 5),
            pagingSourceFactory = {
                BakeryReviewPagingSource(
                    pageSize = 5,
                    bakeryDataSource = bakeryDataSource,
                    myReview = myReview,
                    bakeryId = bakeryId,
                    sort = sort
                )
            }
        ).flow.flowOn(networkDispatcher)

    override fun getReviewMenus(bakeryId: Int): Flow<List<ReviewMenu>> {
        return bakeryDataSource.getMenus(bakeryId = bakeryId)
            .map { menus ->
                menus.map { menu ->
                    menu.toReviewMenu()
                }
            }
    }

    override fun postReview(bakeryId: Int, review: WriteBakeryReview): Flow<List<Badge>> {
        val request = WriteBakeryReviewRequest(
            rating = review.rating,
            content = review.content,
            isPrivate = review.isPrivate,
            consumedMenus = review.menus.map {
                WriteBakeryReviewRequest.Menu(
                    menuId = it.id,
                    quantity = it.quantity,
                    breadTypeId = it.breadTypeId
                )
            },
            reviewImgs = review.photos
        )
        return bakeryDataSource.postReview(bakeryId = bakeryId, request = request)
            .map { extra ->
                extra.map { badge ->
                    badge.toExternalModel()
                }
            }
    }

    override fun postLike(bakeryId: Int): Flow<Pair<Int, Boolean>> {
        return bakeryDataSource.postLike(bakeryId = bakeryId)
            .map { it.bakeryId to it.isLike }
    }

    override fun deleteLike(bakeryId: Int): Flow<Pair<Int, Boolean>> {
        return bakeryDataSource.deleteLike(bakeryId = bakeryId)
            .map { it.bakeryId to it.isLike }
    }

    override fun checkReviewEligibility(bakeryId: Int): Flow<Boolean> {
        return bakeryDataSource.checkReviewEligibility(bakeryId = bakeryId)
            .map { it.isEligible }
    }

    override fun getMyBakeries(
        cursor: String,
        myBakeryType: MyBakeryType,
        sort: BakerySortType
    ): Flow<CursorPaging<Bakery>> = flow {
        val response = when (myBakeryType) {
            MyBakeryType.VISITED -> bakeryDataSource.getVisitedBakeries(
                cursorValue = cursor,
                pageSize = DefaultPageSize,
                sort = sort.value
            )

            MyBakeryType.LIKE -> bakeryDataSource.getLikeBakeries(
                cursorValue = cursor,
                pageSize = DefaultPageSize,
                sort = sort.value
            )
        }
        val paging = CursorPaging(
            list = response.items.map { it.toExternalModel() },
            currentCursor = cursor,
            nextCursor = response.nextCursor
        )
        emit(paging)
    }.flowOn(networkDispatcher)

    override fun getRecentBakeries(): Flow<List<RecommendBakery>> {
        return bakeryDataSource.getRecentBakeries()
            .map { bakeries ->
                bakeries.map { bakery ->
                    bakery.toExternalModel()
                }
            }
    }
}