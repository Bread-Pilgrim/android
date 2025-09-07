package com.twolskone.bakeroad.feature.search

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.twolskone.bakeroad.core.common.android.extension.ObserveError
import com.twolskone.bakeroad.feature.search.mvi.SearchIntent
import com.twolskone.bakeroad.feature.search.mvi.SearchSection
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import timber.log.Timber

@Composable
internal fun SearchRoute(
    padding: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, launcher: ActivityResultLauncher<Intent>) -> Unit,
    goBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val queryTextState = rememberTextFieldState()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val resultPagingItems = viewModel.resultPagingFlow.collectAsLazyPagingItems()
    val interactionSource = remember { MutableInteractionSource() }
    val isFocus by interactionSource.collectIsFocusedAsState()
    val bakeryDetailLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Timber.i("xxx bakeryDetailLauncher :: resultCode ${result.resultCode}")
    }

    BackHandler {
        if (state.section != SearchSection.RecentSearchResult) {
            focusManager.clearFocus()
            queryTextState.clearText()
            viewModel.intent(SearchIntent.ChangeSection(section = SearchSection.RecentSearchResult))
        } else {
            goBack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.mainEventBus.searchRefreshState.collectLatest { refresh ->
            if (refresh) {
                Timber.i("xxx collect searchRefreshState")
                viewModel.mainEventBus.setSearchRefreshState(value = false)
                viewModel.intent(SearchIntent.RefreshRecentBakeries)
            }
        }
    }

    LaunchedEffect(isFocus, queryTextState.text) {
        if (isFocus && (queryTextState.text.isBlank() || state.section == SearchSection.RecentSearchResult)) {
            viewModel.intent(SearchIntent.ChangeSection(section = SearchSection.RecentSearchQueries))
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { resultPagingItems.loadState.refresh }
            .filterIsInstance<LoadState.NotLoading>()
            .collect {
                Timber.e("xxx loadState: $it")
                viewModel.intent(SearchIntent.SetLoading(loading = false))
            }
    }

    resultPagingItems.ObserveError(viewModel = viewModel)

    SearchScreen(
        padding = padding,
        state = state,
        queryTextState = queryTextState,
        interactionSource = interactionSource,
        resultPagingItems = resultPagingItems,
        onCancelClick = {
            focusManager.clearFocus()
            queryTextState.clearText()
            viewModel.intent(SearchIntent.ChangeSection(section = SearchSection.RecentSearchResult))
        },
        onSearch = { query ->
            focusManager.clearFocus()
            keyboardController?.hide()
            viewModel.intent(SearchIntent.SearchBakery(query = query))
            viewModel.intent(SearchIntent.ChangeSection(section = SearchSection.SearchResult))
        },
        onDeleteQueryClick = { query -> viewModel.intent(SearchIntent.DeleteQuery(query = query)) },
        onDeleteAllQueriesClick = { viewModel.intent(SearchIntent.DeleteAllQueries) },
        onSearchResultClick = { bakery -> navigateToBakeryDetail(bakery.id, bakery.areaCode, bakeryDetailLauncher) },
        onClearQueriesClick = {
            queryTextState.clearText()
            viewModel.intent(SearchIntent.ChangeSection(section = SearchSection.RecentSearchQueries))
        },
        onRecentBakeryClick = { bakery -> navigateToBakeryDetail(bakery.id, bakery.areaCode, bakeryDetailLauncher) },
        onDeleteRecentBakeriesClick = { viewModel.intent(SearchIntent.DeleteRecentBakeries) }
    )
}