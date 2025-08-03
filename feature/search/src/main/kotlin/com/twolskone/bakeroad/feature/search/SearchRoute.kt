package com.twolskone.bakeroad.feature.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.feature.search.mvi.SearchIntent
import com.twolskone.bakeroad.feature.search.mvi.SearchSection
import timber.log.Timber

@Composable
internal fun SearchRoute(
    padding: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val queryTextState = rememberTextFieldState()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val resultPagingItems = viewModel.resultPagingFlow.collectAsLazyPagingItems()
    val interactionSource = remember { MutableInteractionSource() }
    val isFocus by interactionSource.collectIsFocusedAsState()

    BackHandler {
        if (state.section != SearchSection.RecentSearchResult) {
            focusManager.clearFocus()
            queryTextState.clearText()
            viewModel.intent(SearchIntent.ChangeSection(section = SearchSection.RecentSearchResult))
        } else {

        }
    }

    LaunchedEffect(isFocus) {
        Timber.i("xxx isFocus $isFocus")
        if (isFocus && state.section == SearchSection.RecentSearchResult) {
            viewModel.intent(SearchIntent.ChangeSection(section = SearchSection.RecentSearchQueries))
        }
    }

    BaseComposable(baseViewModel = viewModel) {
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
                keyboardController?.hide()
                viewModel.intent(SearchIntent.SearchBakery(query = query))
                viewModel.intent(SearchIntent.ChangeSection(section = SearchSection.SearchResult))
            }
        )
    }
}