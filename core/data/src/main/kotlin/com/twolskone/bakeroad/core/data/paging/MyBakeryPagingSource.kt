package com.twolskone.bakeroad.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.twolskone.bakeroad.core.data.mapper.toExternalModel
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakerySortType
import com.twolskone.bakeroad.core.model.type.MyBakeryType
import com.twolskone.bakeroad.core.remote.datasource.BakeryDataSource
import timber.log.Timber

internal class MyBakeryPagingSource(
    private val pageSize: Int,
    private val bakeryDataSource: BakeryDataSource,
    private val myBakeryType: MyBakeryType,
    private val sort: BakerySortType
) : PagingSource<Int, Bakery>() {

    override fun getRefreshKey(state: PagingState<Int, Bakery>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }.also {
            Timber.i("xxx getRefreshKey : $it")
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Bakery> {
        val page = params.key ?: InitialPage

        return try {
            val response = when (myBakeryType) {
                MyBakeryType.VISITED -> bakeryDataSource.getLikeBakeries(
                    pageNo = page,
                    pageSize = params.loadSize,
                    sort = sort.value
                )

                MyBakeryType.LIKE -> bakeryDataSource.getLikeBakeries(
                    pageNo = page,
                    pageSize = params.loadSize,
                    sort = sort.value
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