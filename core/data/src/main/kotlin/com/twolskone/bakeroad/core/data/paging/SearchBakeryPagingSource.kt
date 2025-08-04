package com.twolskone.bakeroad.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.datastore.CacheDataSource
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.remote.datasource.SearchDataSource

internal class SearchBakeryPagingSource(
    private val pageSize: Int,
    private val searchDataSource: SearchDataSource,
    private val cacheDataSource: CacheDataSource,
    private val query: String
) : PagingSource<Int, Bakery>() {

    override fun getRefreshKey(state: PagingState<Int, Bakery>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(anchorPageIndex - 1)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Bakery> {
        val page = params.key ?: InitialPage

        return try {
            val response = searchDataSource.searchBakery(
                query = query,
                pageNo = page,
                pageSize = params.loadSize
            )
            val hasNext = response.hasNext

            if (page == InitialPage) {
                cacheDataSource.putRecentSearchQuery(query = query)
            }

            LoadResult.Page(
                data = response.items.map { it.toExternalModel() },
                prevKey = if (page == InitialPage) null else page - 1,
                nextKey = if (!hasNext || response.items.size < pageSize) null else page + (params.loadSize / pageSize)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}