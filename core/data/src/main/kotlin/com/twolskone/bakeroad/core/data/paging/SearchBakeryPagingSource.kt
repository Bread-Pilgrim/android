package com.twolskone.bakeroad.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.datastore.CacheDataSource
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.paging.StartCursor
import com.twolskone.bakeroad.core.remote.datasource.SearchDataSource

internal class SearchBakeryPagingSource(
    private val pageSize: Int,
    private val searchDataSource: SearchDataSource,
    private val cacheDataSource: CacheDataSource,
    private val query: String
) : PagingSource<String, Bakery>() {

    override fun getRefreshKey(state: PagingState<String, Bakery>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Bakery> {
        val cursor = params.key ?: StartCursor

        return try {
            val response = searchDataSource.searchBakery(
                query = query,
                cursorValue = cursor,
                pageSize = params.loadSize
            )
            val nextCursor = response.nextCursor

            if (cursor == StartCursor) {
                cacheDataSource.putRecentSearchQuery(query = query)
            }

            LoadResult.Page(
                data = response.items.map { it.toExternalModel() },
                prevKey = null,
                nextKey = if (nextCursor.isEmpty() || response.items.size < pageSize) null else nextCursor
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}