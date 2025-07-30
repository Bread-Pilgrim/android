package com.twolskone.bakeroad.feature.bakery.detail

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.core.common.android.extension.ObserveError
import com.twolskone.bakeroad.feature.bakery.detail.model.ReviewTab
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailIntent
import timber.log.Timber

@Composable
internal fun BakeryDetailRoute(
    viewModel: BakeryDetailViewModel = hiltViewModel(),
    navigateToWriteBakeryReview: (Int, ActivityResultLauncher<Intent>) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tabState by viewModel.tabState.collectAsStateWithLifecycle()
    val reviewTabState by viewModel.reviewTabState.collectAsStateWithLifecycle()
    val reviewSortState by viewModel.reviewSortState.collectAsStateWithLifecycle()
    val myReviewPagingItems = viewModel.myReviewPagingFlow.collectAsLazyPagingItems()
    val reviewPagingItems = viewModel.reviewPagingFlow.collectAsLazyPagingItems()
    val writeReviewLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Timber.i("xxx writeReviewLauncher :: Completed write review")
            when (reviewTabState) {
                ReviewTab.MY_REVIEW -> myReviewPagingItems.refresh()
                ReviewTab.ALL_REVIEW -> reviewPagingItems.refresh()
            }
        } else {
            Timber.i("xxx writeReviewLauncher :: Canceled write review")
        }
    }

    reviewPagingItems.ObserveError(viewModel)
    myReviewPagingItems.ObserveError(viewModel)

    BaseComposable(baseViewModel = viewModel) {
        BakeryDetailScreen(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            state = state,
            tabState = tabState,
            reviewTabState = reviewTabState,
            reviewSortState = reviewSortState,
            myReviewPagingItems = myReviewPagingItems,
            reviewPagingItems = reviewPagingItems,
            onTabSelect = { tab -> viewModel.intent(BakeryDetailIntent.SelectTab(tab)) },
            onReviewTabSelect = { tab -> viewModel.intent(BakeryDetailIntent.SelectReviewTab(tab)) },
            onReviewSortSelect = { sort -> viewModel.intent(BakeryDetailIntent.SelectReviewSort(sort)) },
            onWriteReviewClick = { navigateToWriteBakeryReview(viewModel.bakeryId, writeReviewLauncher) }
        )
    }
}