package com.twolskone.bakeroad.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.paging.StartCursor
import com.twolskone.bakeroad.core.model.paging.StartCursorWithSort
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource

internal class BakeryReviewPagingSource(
    private val pageSize: Int,
    private val bakeryDataSource: BakeryDataSource,
    private val myReview: Boolean,
    private val bakeryId: Int,
    private val sort: ReviewSortType?
) : PagingSource<String, BakeryReview>() {

    override fun getRefreshKey(state: PagingState<String, BakeryReview>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, BakeryReview> {
        return try {
            val response = if (myReview) {
                bakeryDataSource.getMyReviews(
                    bakeryId = bakeryId,
                    cursorValue = params.key ?: StartCursor,
                    pageSize = params.loadSize
                )
            } else {
                bakeryDataSource.getReviews(
                    bakeryId = bakeryId,
                    sort = sort?.value ?: ReviewSortType.LIKE_COUNT_DESC.value,
                    cursorValue = params.key ?: StartCursorWithSort,
                    pageSize = params.loadSize
                )
            }
            val nextCursor = response.nextCursor

            LoadResult.Page(
                data = response.items.map {
                    it.toExternalModel(
                        avgRating = response.avgRating,
                        reviewCount = response.reviewCount
                    )
                },
                prevKey = null,
                nextKey = if (nextCursor.isEmpty() || response.items.size < pageSize) null else nextCursor
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}