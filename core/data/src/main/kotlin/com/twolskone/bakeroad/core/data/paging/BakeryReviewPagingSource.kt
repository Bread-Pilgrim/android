package com.twolskone.bakeroad.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import timber.log.Timber

internal class BakeryReviewPagingSource(
    private val pageSize: Int,
    private val bakeryDataSource: BakeryDataSource,
    private val myReview: Boolean,
    private val bakeryId: Int,
    private val sort: ReviewSortType?
) : PagingSource<Int, BakeryReview>() {

    override fun getRefreshKey(state: PagingState<Int, BakeryReview>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }.also {
            Timber.i("xxx getRefreshKey : $it")
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BakeryReview> {
        val page = params.key ?: InitialPage

        return try {
            val response = if (myReview) {
                bakeryDataSource.getMyReviews(
                    bakeryId = bakeryId,
                    pageNo = page,
                    pageSize = params.loadSize
                )
            } else {
                bakeryDataSource.getReviews(
                    bakeryId = bakeryId,
                    sort = sort?.value ?: ReviewSortType.LIKE_COUNT_DESC.value,
                    pageNo = page,
                    pageSize = params.loadSize
                )
            }
            val hasNext = response.hasNext

            LoadResult.Page(
                data = response.items.map {
                    it.toExternalModel(
                        avgRating = response.avgRating,
                        reviewCount = response.reviewCount
                    )
                },
                prevKey = if (page == InitialPage) null else page - 1,
                nextKey = if (!hasNext || response.items.size < pageSize) null else page + (params.loadSize / pageSize)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}