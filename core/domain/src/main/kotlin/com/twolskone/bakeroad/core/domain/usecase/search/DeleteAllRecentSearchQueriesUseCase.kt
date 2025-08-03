package com.twolskone.bakeroad.core.domain.usecase.search

import com.twolskone.bakeroad.core.domain.repository.SearchRepository
import javax.inject.Inject

class DeleteAllRecentSearchQueriesUseCase @Inject constructor(private val searchRepository: SearchRepository) {

    suspend operator fun invoke() = searchRepository.deleteAllRecentQueries()
}