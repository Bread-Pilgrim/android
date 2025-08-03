package com.twolskone.bakeroad.core.domain.usecase.search

import androidx.paging.PagingData
import com.twolskone.bakeroad.core.domain.repository.SearchRepository
import com.twolskone.bakeroad.core.model.Bakery
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSearchBakeriesUseCase @Inject constructor(private val searchRepository: SearchRepository) {

    operator fun invoke(query: String): Flow<PagingData<Bakery>> =
        searchRepository.searchBakery(query = query)
}