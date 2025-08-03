package com.twolskone.bakeroad.core.domain.usecase.search

import com.twolskone.bakeroad.core.domain.repository.SearchRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

class GetRecentSearchQueriesUseCase @Inject constructor(private val searchRepository: SearchRepository) {

    suspend operator fun invoke(): List<Pair<String, String>> =
        searchRepository.getRecentQueries().firstOrNull().orEmpty()
}