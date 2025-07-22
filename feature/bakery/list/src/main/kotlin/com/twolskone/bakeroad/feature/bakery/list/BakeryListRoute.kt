package com.twolskone.bakeroad.feature.bakery.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
internal fun BakeryListRoute(
    viewModel: BakeryListViewModel = hiltViewModel(),
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lazyPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()

    BakeryListScreen(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        bakeryType = state.bakeryType,
        pagingItems = lazyPagingItems,
        onBakeryClick = { bakery -> navigateToBakeryDetail(bakery.id, bakery.areaCode) }
    )
}