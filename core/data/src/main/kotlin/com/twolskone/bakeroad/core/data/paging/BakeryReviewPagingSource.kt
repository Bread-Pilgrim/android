package com.twolskone.bakeroad.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import com.twolskone.bakeroad.core.remote.model.initialCursor
import com.twolskone.bakeroad.core.remote.model.initialSortCursor

internal class BakeryReviewPagingSource(
    private val bakeryDataSource: BakeryDataSource,
    private val myReview: Boolean,
    private val bakeryId: Int,
    private val sort: ReviewSortType?
) : PagingSource<String, BakeryReview>() {

    override fun getRefreshKey(state: PagingState<String, BakeryReview>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(anchorPageIndex - 1)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, BakeryReview> {
        return try {
            val response = if (myReview) {
                bakeryDataSource.getMyReviews(
                    bakeryId = bakeryId,
                    cursorValue = params.key ?: initialCursor,
                    pageSize = params.loadSize
                )
            } else {
                bakeryDataSource.getReviews(
                    bakeryId = bakeryId,
                    sort = sort?.value ?: ReviewSortType.LIKE_COUNT_DESC.value,
                    cursorValue = params.key ?: initialSortCursor,
                    pageSize = params.loadSize
                )
            }
            val hasNextCursor = response.paging.hasNext
            val prevCursor = response.paging.prevCursor
            val nextCursor = response.paging.nextCursor

            LoadResult.Page(
                data = response.items.map { it.toExternalModel() },
                prevKey = if (params.key == null || params.key == initialCursor || params.key == initialSortCursor) null else prevCursor,
                nextKey = if (!hasNextCursor) null else nextCursor
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}