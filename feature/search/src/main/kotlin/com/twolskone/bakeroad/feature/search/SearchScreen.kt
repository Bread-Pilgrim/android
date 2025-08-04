package com.twolskone.bakeroad.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.twolskone.bakeroad.core.common.android.extension.isEmpty
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadSearchTopAppBar
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.ui.BakeryCard
import com.twolskone.bakeroad.core.ui.EmptyCard
import com.twolskone.bakeroad.feature.search.component.RecentSearchQueryChip
import com.twolskone.bakeroad.feature.search.component.SectionTitle
import com.twolskone.bakeroad.feature.search.mvi.SearchSection
import com.twolskone.bakeroad.feature.search.mvi.SearchState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentMap
import timber.log.Timber

@Composable
internal fun SearchScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    state: SearchState,
    queryTextState: TextFieldState,
    resultPagingItems: LazyPagingItems<Bakery>,
    interactionSource: MutableInteractionSource,
    onCancelClick: () -> Unit,
    onSearch: (String) -> Unit,
    onDeleteQueryClick: (String) -> Unit,
    onDeleteAllQueriesClick: () -> Unit,
    onSearchResultClick: (Bakery) -> Unit,
    onBakeryLikeClick: (Int, Boolean) -> Unit,
    onClearQueriesClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(bottom = padding.calculateBottomPadding())
    ) {
        BakeRoadSearchTopAppBar(
            state = queryTextState,
            interactionSource = interactionSource,
            showCancelButton = state.section != SearchSection.RecentSearchResult,
            hint = stringResource(id = R.string.feature_search_hint),
            onKeyboardAction = { onSearch(queryTextState.text.toString()) },
            onCancelClick = onCancelClick,
            onClearClick = onClearQueriesClick
        )
        when (state.section) {
            SearchSection.RecentSearchResult -> RecentSearchResult(list = state.recentSearchBakeryList)
            SearchSection.RecentSearchQueries -> RecentSearchQueries(
                queryList = state.recentQueryList,
                onChipClick = onSearch,
                onDeleteClick = onDeleteQueryClick,
                onDeleteAllClick = onDeleteAllQueriesClick
            )

            SearchSection.SearchResult -> SearchResult(
                loading = state.loading,
                resultPagingItems = resultPagingItems,
                localLikeMap = state.localLikeMap,
                onCardClick = onSearchResultClick,
                onLikeClick = onBakeryLikeClick
            )
        }
    }
}

/**
 * 최근 조회한 빵집
 */
@Composable
private fun ColumnScope.RecentSearchResult(list: ImmutableList<Bakery>) {
    SectionTitle(
        title = stringResource(id = R.string.feature_search_title_recent_search_result),
        deleteAllEnabled = list.isNotEmpty(),
        onDeleteAllClick = {}
    )
    if (list.isNotEmpty()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

        }
    } else {
        EmptyCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            description = stringResource(id = R.string.feature_search_empty_recent_search_result)
        )
    }
}

/**
 * 최근 검색어
 */
@Composable
private fun ColumnScope.RecentSearchQueries(
    queryList: ImmutableList<Pair<String, String>>,
    onChipClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    onDeleteAllClick: () -> Unit
) {
    SectionTitle(
        title = stringResource(id = R.string.feature_search_title_recent_search_query),
        deleteAllEnabled = queryList.isNotEmpty(),
        onDeleteAllClick = onDeleteAllClick
    )
    if (queryList.isNotEmpty()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = queryList,
                key = { "${it.first}_${it.second}" }
            ) { (query, timeMillis) ->
                RecentSearchQueryChip(
                    query = query,
                    onClick = { onChipClick(query) },
                    onDeleteClick = onDeleteClick
                )
            }
        }
    } else {
        EmptyCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            description = stringResource(id = R.string.feature_search_empty_recent_search_query)
        )
    }
}

/**
 * 검색 결과
 */
@Composable
private fun ColumnScope.SearchResult(
    loading: Boolean,
    resultPagingItems: LazyPagingItems<Bakery>,
    localLikeMap: PersistentMap<Int, Boolean>,
    onCardClick: (Bakery) -> Unit,
    onLikeClick: (Int, Boolean) -> Unit
) {
    if (loading) {
        Timber.e("xxx SearchResult: loading")
        Box(modifier = Modifier.weight(1f))
    } else if (resultPagingItems.isEmpty) {
        Timber.e("xxx SearchResult: empty")
        EmptyCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            description = stringResource(id = R.string.feature_search_empty_recent_search_query)
        )
    } else {
        Timber.e("xxx SearchResult: result")
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = resultPagingItems.itemCount,
                key = { index -> resultPagingItems.peek(index)?.id ?: "placeholder_$index" }
            ) { index ->
                resultPagingItems[index]?.let { bakery ->
                    BakeryCard(
                        bakery = bakery,
                        likeMap = localLikeMap,
                        onCardClick = onCardClick,
                        onLikeClick = onLikeClick
                    )
                } ?: run {
                    // TODO. Skeleton.
                }
            }
        }
    }
}