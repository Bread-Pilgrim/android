package com.twolskone.bakeroad.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.datastore.CacheDataSource
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.remote.datasource.SearchDataSource
import com.twolskone.bakeroad.core.remote.model.initialCursor

internal class SearchBakeryPagingSource(
    private val searchDataSource: SearchDataSource,
    private val cacheDataSource: CacheDataSource,
    private val query: String
) : PagingSource<String, Bakery>() {

    override fun getRefreshKey(state: PagingState<String, Bakery>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(anchorPageIndex - 1)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Bakery> {
        val cursor = params.key ?: initialCursor

        return try {
            val response = searchDataSource.searchBakery(
                query = query,
                cursorValue = cursor,
                pageSize = params.loadSize
            )
            val hasNextCursor = response.paging.hasNext
            val prevCursor = response.paging.prevCursor
            val nextCursor = response.paging.nextCursor

            if (cursor == initialCursor) {
                cacheDataSource.putRecentSearchQuery(query = query)
            }

            LoadResult.Page(
                data = response.items.map { it.toExternalModel() },
                prevKey = null/*if (params.key == null || params.key == initialCursor || params.key == initialSortCursor) null else prevCursor*/,
                nextKey = if (!hasNextCursor) null else nextCursor
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}