package com.twolskone.bakeroad.core.domain.repository

import androidx.paging.PagingData
import com.twolskone.bakeroad.core.model.Bakery
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getRecentQueries(): Flow<List<Pair<String, String>>>
    suspend fun deleteRecentQuery(query: String)
    suspend fun deleteAllRecentQueries()
    fun searchBakery(query: String): Flow<PagingData<Bakery>>
}