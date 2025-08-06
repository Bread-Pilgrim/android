package com.twolskone.bakeroad.feature.bakery.detail

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.core.common.android.extension.ObserveError
import com.twolskone.bakeroad.core.common.android.extension.isEmpty
import com.twolskone.bakeroad.core.navigator.model.RESULT_REFRESH_BAKERY_LIST
import com.twolskone.bakeroad.feature.bakery.detail.model.BakeryDetailTab
import com.twolskone.bakeroad.feature.bakery.detail.model.ReviewTab
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailIntent
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailSideEffect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import timber.log.Timber

@Composable
internal fun BakeryDetailRoute(
    viewModel: BakeryDetailViewModel = hiltViewModel(),
    navigateToWriteBakeryReview: (Int, ActivityResultLauncher<Intent>) -> Unit,
    setResult: (code: Int, withFinish: Boolean) -> Unit
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
            if (tabState == BakeryDetailTab.REVIEW) {
                // 방문자 리뷰 탭 전환 또는 갱신
                when (reviewTabState) {
                    ReviewTab.ALL_REVIEW -> reviewPagingItems.refresh()
                    ReviewTab.MY_REVIEW -> viewModel.intent(BakeryDetailIntent.SelectReviewTab(tab = ReviewTab.ALL_REVIEW))
                }
            } else {
                // 리뷰 프리뷰 갱신 (리뷰 데이터 fetch)
                viewModel.intent(BakeryDetailIntent.RefreshPreviewReviews)
            }
        } else {
            Timber.i("xxx writeReviewLauncher :: Canceled write review")
        }
    }

    BackHandler {
        setResult(RESULT_REFRESH_BAKERY_LIST, true)
    }

    LaunchedEffect(viewModel) {
        viewModel.sideEffect.collect {
            when (it) {
                BakeryDetailSideEffect.NavigateToWriteBakeryReview -> navigateToWriteBakeryReview(viewModel.bakeryId, writeReviewLauncher)
            }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { reviewPagingItems.loadState.refresh }
            .filterIsInstance<LoadState.NotLoading>()
            .filter { !reviewPagingItems.isEmpty }
            .collect {
                reviewPagingItems.peek(0)?.let {
                    viewModel.intent(BakeryDetailIntent.UpdateReviewInfo(avgRating = it.avgRating, count = it.totalCount))
                }
            }
    }

    reviewPagingItems.ObserveError(viewModel)
    myReviewPagingItems.ObserveError(viewModel)

    BaseComposable(baseViewModel = viewModel) {
        BakeryDetailScreen(
            state = state,
            tabState = tabState,
            reviewTabState = reviewTabState,
            reviewSortState = reviewSortState,
            myReviewPagingItems = myReviewPagingItems,
            reviewPagingItems = reviewPagingItems,
            onTabSelect = { tab -> viewModel.intent(BakeryDetailIntent.SelectTab(tab)) },
            onReviewTabSelect = { tab -> viewModel.intent(BakeryDetailIntent.SelectReviewTab(tab)) },
            onReviewSortSelect = { sort -> viewModel.intent(BakeryDetailIntent.SelectReviewSort(sort)) },
            onWriteReviewClick = { viewModel.intent(BakeryDetailIntent.CheckReviewEligibility) },
            onBakeryLikeClick = { isLike -> viewModel.intent(BakeryDetailIntent.ClickBakeryLike(isLike = isLike)) },
            onReviewLikeClick = { id, isLike -> viewModel.intent(BakeryDetailIntent.ClickReviewLike(reviewId = id, isLike = isLike)) }
        )
    }
}