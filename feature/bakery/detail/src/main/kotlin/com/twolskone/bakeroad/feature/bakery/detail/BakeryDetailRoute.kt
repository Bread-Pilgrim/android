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
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailIntent
import timber.log.Timber

@Composable
internal fun BakeryDetailRoute(
    viewModel: BakeryDetailViewModel = hiltViewModel(),
    navigateToWriteBakeryReview: (Int, ActivityResultLauncher<Intent>) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val reviewSort by viewModel.reviewSort.collectAsStateWithLifecycle()
    val writeReviewLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Timber.i("xxx writeReviewLauncher :: Completed write review")
            result.data?.let { intent ->

            }
        } else {
            Timber.i("xxx writeReviewLauncher :: Canceled write review")
        }
    }

    BakeryDetailScreen(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        state = state,
        reviewSort = reviewSort,
        myReviewPaging = viewModel.myReviewPagingFlow.collectAsLazyPagingItems(),
        reviewPaging = viewModel.reviewPagingFlow.collectAsLazyPagingItems(),
        onTabSelect = { tab -> viewModel.intent(BakeryDetailIntent.SelectTab(tab)) },
        onReviewTabSelect = { tab -> viewModel.intent(BakeryDetailIntent.SelectReviewTab(tab)) },
        onReviewSortSelect = { sort -> viewModel.intent(BakeryDetailIntent.SelectReviewSort(sort)) },
        onWriteReviewClick = { navigateToWriteBakeryReview(viewModel.bakeryId, writeReviewLauncher) }
    )
}