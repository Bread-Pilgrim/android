package com.twolskone.bakeroad.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import com.twolskone.bakeroad.core.remote.model.initialCursor
import com.twolskone.bakeroad.core.remote.model.initialSortCursor

internal class BakeryPagingSource(
    private val bakeryDataSource: BakeryDataSource,
    private val areaCodes: String,
    private val bakeryType: BakeryType
) : PagingSource<String, Bakery>() {

    override fun getRefreshKey(state: PagingState<String, Bakery>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Bakery> {
        val cursor = params.key ?: initialCursor

        return try {
            val response = when (bakeryType) {
                BakeryType.PREFERENCE -> bakeryDataSource.getPreferenceBakeries(
                    areaCodes = areaCodes,
                    cursorValue = cursor,
                    pageSize = params.loadSize
                )

                BakeryType.HOT -> bakeryDataSource.getHotBakeries(
                    areaCodes = areaCodes,
                    cursorValue = cursor,
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