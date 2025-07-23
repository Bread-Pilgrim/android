package com.twolskone.bakeroad.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.model.BakeryReview
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource

internal class BakeryReviewPagingSource(
    private val bakeryDataSource: BakeryDataSource,
    private val myReview: Boolean,
    private val bakeryId: Int,
    private val sort: ReviewSortType?
) : PagingSource<Int, BakeryReview>() {

    override fun getRefreshKey(state: PagingState<Int, BakeryReview>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BakeryReview> {
        val cursor = params.key ?: 0

        return try {
            val response = if (myReview) {
                bakeryDataSource.getMyReviews(
                    bakeryId = bakeryId,
                    cursorId = cursor,
                    pageSize = params.loadSize
                )
            } else {
                bakeryDataSource.getReviews(
                    bakeryId = bakeryId,
                    sort = sort?.value ?: ReviewSortType.LIKE_COUNT_DESC.value,
                    cursorId = cursor,
                    pageSize = params.loadSize
                )
            }
            val beforeCursor = response.paging.cursor.before
            val afterCursor = response.paging.cursor.after

            LoadResult.Page(
                data = response.items.map { it.toExternalModel() },
                prevKey = if (cursor == 0) null else beforeCursor,
                nextKey = if (afterCursor == -1) null else afterCursor
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}