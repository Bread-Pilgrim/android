package com.twolskone.bakeroad.feature.review.myreviews

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.feature.review.myreviews.mvi.MyReviewsIntent
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
internal fun MyReviewRoute(
    viewModel: MyReviewsViewModel = hiltViewModel(),
    finish: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val isLastItemVisible by remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val lastIndex = listState.layoutInfo.totalItemsCount - 1
            state.paging.canRequest && lastVisibleItemIndex == lastIndex
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { isLastItemVisible }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                viewModel.intent(MyReviewsIntent.GetMyReviews(refresh = false))
            }
    }

    BaseComposable(baseViewModel = viewModel) {
        MyReviewsScreen(
            state = state,
            onBackClick = finish,
            onLikeClick = { id, isLike -> viewModel.intent(MyReviewsIntent.ClickLike(id = id, isLike = isLike)) }
        )
    }
}