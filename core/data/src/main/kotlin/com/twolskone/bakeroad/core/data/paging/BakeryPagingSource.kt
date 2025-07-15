package com.twolskone.bakeroad.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource

internal const val StartCursor = 0
internal const val PageSize = 15

internal class BakeryPagingSource(
    private val bakeryDataSource: BakeryDataSource,
    private val areaCode: String,
    private val bakeryType: BakeryType
) : PagingSource<Int, Bakery>() {

    override fun getRefreshKey(state: PagingState<Int, Bakery>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Bakery> {
        val cursor = params.key ?: StartCursor

        return try {
            // TODO. BakeryType 분기
            val response = bakeryDataSource.getPreferenceBakeries(
                areaCode = areaCode,
                cursorId = cursor,
                pageSize = params.loadSize
            )
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