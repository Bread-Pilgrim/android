package com.twolskone.bakeroad.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.bakery.GetRecentBakeriesUseCase
import com.twolskone.bakeroad.core.domain.usecase.search.DeleteAllRecentSearchQueriesUseCase
import com.twolskone.bakeroad.core.domain.usecase.search.DeleteRecentSearchQueryUseCase
import com.twolskone.bakeroad.core.domain.usecase.search.GetRecentSearchQueriesUseCase
import com.twolskone.bakeroad.core.domain.usecase.search.GetSearchBakeriesUseCase
import com.twolskone.bakeroad.core.eventbus.MainEventBus
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.feature.search.mvi.SearchIntent
import com.twolskone.bakeroad.feature.search.mvi.SearchSection
import com.twolskone.bakeroad.feature.search.mvi.SearchSideEffect
import com.twolskone.bakeroad.feature.search.mvi.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val mainEventBus: MainEventBus,
    private val getRecentSearchQueriesUseCase: GetRecentSearchQueriesUseCase,
    private val deleteRecentSearchQueryUseCase: DeleteRecentSearchQueryUseCase,
    private val deleteAllRecentSearchQueriesUseCase: DeleteAllRecentSearchQueriesUseCase,
    private val getSearchBakeriesUseCase: GetSearchBakeriesUseCase,
    private val getRecentBakeriesUseCase: GetRecentBakeriesUseCase
) : BaseViewModel<SearchState, SearchIntent, SearchSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): SearchState {
        return SearchState()
    }

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val resultPagingFlow = query
        .filter { it.isNotBlank() }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            reduce { copy(loading = true) }
            getSearchBakeriesUseCase(query = query).cachedIn(viewModelScope)
        }

    init {
        getRecentBakeryList()
    }

    override fun handleException(cause: Throwable) {
        Timber.e(cause)
        when (cause) {
            is ClientException -> {
                showSnackbar(
                    type = SnackbarType.ERROR,
                    message = cause.message,
                    messageRes = cause.error?.messageRes
                )
            }

            is BakeRoadException -> {
                showSnackbar(type = SnackbarType.ERROR, message = cause.message)
            }
        }
    }

    override suspend fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.ChangeSection -> {
                when (intent.section) {
                    SearchSection.RecentSearchResult -> {
                        // TODO. Get recent searched bakeries.
                    }

                    SearchSection.RecentSearchQueries -> {
                        getRecentSearchQueryList()
                    }

                    SearchSection.SearchResult -> {
                        // Search result use changed query.
                    }
                }
                reduce { copy(section = intent.section) }
            }

            is SearchIntent.SearchBakery -> _query.value = (intent.query).trim()

            is SearchIntent.DeleteQuery -> {
                deleteRecentSearchQueryUseCase(query = intent.query)
                getRecentSearchQueryList()
            }

            SearchIntent.DeleteAllQueries -> {
                deleteAllRecentSearchQueriesUseCase()
                getRecentSearchQueryList()
            }

            is SearchIntent.SetLoading -> reduce { copy(loading = intent.loading) }

            SearchIntent.RefreshRecentBakeries -> getRecentBakeryList()
        }
    }

    /**
     * 최근 조회한 빵집
     */
    private fun getRecentBakeryList() {
        launch {
            val recentBakeries = getRecentBakeriesUseCase()
            reduce { copy(recentBakeryList = recentBakeries.toImmutableList()) }
        }
    }

    /**
     * 최근 검색어 목록
     */
    private suspend fun getRecentSearchQueryList() {
        val queries = getRecentSearchQueriesUseCase()
        reduce { copy(recentQueryList = queries.toImmutableList()) }
    }
}