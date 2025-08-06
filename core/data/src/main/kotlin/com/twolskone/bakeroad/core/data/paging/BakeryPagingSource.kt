package com.twolskone.bakeroad.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import timber.log.Timber

internal class BakeryPagingSource(
    private val pageSize: Int,
    private val bakeryDataSource: BakeryDataSource,
    private val areaCodes: String,
    private val bakeryType: BakeryType
) : PagingSource<Int, Bakery>() {

    override fun getRefreshKey(state: PagingState<Int, Bakery>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }.also {
            Timber.i("xxx getRefreshKey : $it")
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Bakery> {
        val page = params.key ?: InitialPage

        return try {
            val response = when (bakeryType) {
                BakeryType.PREFERENCE -> bakeryDataSource.getPreferenceBakeries(
                    areaCodes = areaCodes,
                    pageNo = page,
                    pageSize = params.loadSize
                )

                BakeryType.HOT -> bakeryDataSource.getHotBakeries(
                    areaCodes = areaCodes,
                    pageNo = page,
                    pageSize = params.loadSize
                )
            }
            val hasNext = response.hasNext

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