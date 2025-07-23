package com.twolskone.bakeroad.feature.bakery.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailIntent

@Composable
internal fun BakeryDetailRoute(
    viewModel: BakeryDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BakeryDetailScreen(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        state = state,
        myReviewPaging = viewModel.myReviewPagingFlow.collectAsLazyPagingItems(),
        reviewPaging = viewModel.reviewPagingFlow.collectAsLazyPagingItems(),
        onTabSelect = { tab -> viewModel.intent(BakeryDetailIntent.SelectTab(tab)) },
        onReviewTabSelect = { tab -> viewModel.intent(BakeryDetailIntent.SelectReviewTab(tab)) }
    )
}