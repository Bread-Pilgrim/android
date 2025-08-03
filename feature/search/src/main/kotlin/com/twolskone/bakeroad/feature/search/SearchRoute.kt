package com.twolskone.bakeroad.feature.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.common.android.base.BaseComposable

@Composable
internal fun SearchRoute(
    padding: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val queryTextState = rememberTextFieldState()
    val state by viewModel.state.collectAsStateWithLifecycle()

    BaseComposable(baseViewModel = viewModel) {
        SearchScreen(
            padding = padding,
            queryTextState = queryTextState,
            state = state
        )
    }
}