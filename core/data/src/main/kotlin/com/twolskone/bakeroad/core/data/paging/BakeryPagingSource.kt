package com.twolskone.bakeroad.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.paging.StartCursor
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource

internal class BakeryPagingSource(
    private val pageSize: Int,
    private val bakeryDataSource: BakeryDataSource,
    private val areaCodes: String,
    private val bakeryType: BakeryType
) : PagingSource<String, Bakery>() {

    override fun getRefreshKey(state: PagingState<String, Bakery>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Bakery> {
        val cursor = params.key ?: StartCursor

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
            val nextCursor = response.nextCursor

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