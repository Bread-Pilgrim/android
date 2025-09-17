package com.twolskone.bakeroad.feature.photoviewer

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.feature.photoviewer.mvi.PhotoViewerIntent

@Composable
internal fun PhotoViewerRoute(
    viewModel: PhotoViewerViewModel = hiltViewModel(),
    finish: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(initialPage = viewModel.initialPage) { state.photoList.size }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.intent(PhotoViewerIntent.OnPageChanged(page = page))
        }
    }

    PhotoViewerScreen(
        state = state,
        pagerState = pagerState,
        onBackClick = finish
    )
}