package com.twolskone.bakeroad.feature.search.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.feature.search.mvi.SearchSection.RecentSearchQueries
import com.twolskone.bakeroad.feature.search.mvi.SearchSection.RecentSearchResult
import com.twolskone.bakeroad.feature.search.mvi.SearchSection.SearchResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class SearchState(
    val loading: Boolean = false,
    val section: SearchSection = RecentSearchResult,
    val recentSearchBakeryList: ImmutableList<Bakery> = persistentListOf(),
    val recentQueryList: ImmutableList<Pair<String, String>> = persistentListOf(),
    val searchBakeryList: ImmutableList<Bakery> = persistentListOf()
) : BaseUiState

/**
 * 검색 섹션
 * @property RecentSearchResult     최근 조회한 빵집
 * @property RecentSearchQueries    최근 검색어
 * @property SearchResult           검색결과
 */
@Immutable
internal enum class SearchSection {
    RecentSearchResult, RecentSearchQueries, SearchResult
}