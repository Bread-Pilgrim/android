package com.twolskone.bakeroad.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.twolskone.bakeroad.core.common.kotlin.network.BakeRoadDispatcher
import com.twolskone.bakeroad.core.common.kotlin.network.Dispatcher
import com.twolskone.bakeroad.core.data.paging.SearchBakeryPagingSource
import com.twolskone.bakeroad.core.datastore.CacheDataSource
import com.twolskone.bakeroad.core.domain.repository.SearchRepository
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.remote.datasource.SearchDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource,
    private val cacheDataSource: CacheDataSource,
    @Dispatcher(BakeRoadDispatcher.IO) private val networkDispatcher: CoroutineDispatcher
) : SearchRepository {

    override fun getRecentQueries(): Flow<List<Pair<String, String>>> = flow {
        emit(cacheDataSource.getRecentSearchQueries())
    }.flowOn(networkDispatcher)

    override suspend fun deleteRecentQuery(query: String) {
        cacheDataSource.deleteRecentSearchQuery(query = query)
    }

    override suspend fun deleteAllRecentQueries() {
        cacheDataSource.clearRecentSearchQueries()
    }

    override fun searchBakery(query: String): Flow<PagingData<Bakery>> =
        Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = true,
                initialLoadSize = 15
            ),
            pagingSourceFactory = {
                SearchBakeryPagingSource(
                    searchDataSource = searchDataSource,
                    cacheDataSource = cacheDataSource,
                    query = query
                )
            }
        ).flow.flowOn(networkDispatcher)
}