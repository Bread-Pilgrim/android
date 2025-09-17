package com.twolskone.bakeroad.feature.review.myreviews

import android.content.Intent
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
import com.twolskone.bakeroad.core.navigator.util.KEY_PHOTO_VIEWER_INITIAL_PAGE
import com.twolskone.bakeroad.core.navigator.util.KEY_PHOTO_VIEWER_PHOTO_ARRAY
import com.twolskone.bakeroad.core.navigator.util.KEY_PHOTO_VIEWER_TITLE
import com.twolskone.bakeroad.feature.review.myreviews.mvi.MyReviewsIntent
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
internal fun MyReviewRoute(
    viewModel: MyReviewsViewModel = hiltViewModel(),
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int) -> Unit,
    navigateToPhotoViewer: (Intent) -> Unit,
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
            listState = listState,
            onBackClick = finish,
            onBakeryNameClick = navigateToBakeryDetail,
            onLikeClick = { id, isLike -> viewModel.intent(MyReviewsIntent.ClickLike(id = id, isLike = isLike)) },
            onImageClick = { reviewIndex, imageIndex ->
                state.paging.list.getOrNull(reviewIndex)?.let {
                    val intent = Intent().apply {
                        putExtra(KEY_PHOTO_VIEWER_TITLE, it.bakeryName)
                        putExtra(KEY_PHOTO_VIEWER_INITIAL_PAGE, imageIndex)
                        putExtra(KEY_PHOTO_VIEWER_PHOTO_ARRAY, it.photos.toTypedArray())
                    }
                    navigateToPhotoViewer(intent)
                }
            }
        )
    }
}