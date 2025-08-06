package com.twolskone.bakeroad.feature.mybakery

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.core.navigator.model.RESULT_REFRESH_BAKERY_LIST
import com.twolskone.bakeroad.feature.mybakery.model.Tab
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakeryIntent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import timber.log.Timber

@Composable
internal fun MyBakeryRoute(
    viewModel: MyBakeryViewModel = hiltViewModel(),
    padding: PaddingValues,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, launcher: ActivityResultLauncher<Intent>) -> Unit,
    showSnackbar: (SnackbarState) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val visitedListState = rememberLazyListState()
    val likeListState = rememberLazyListState()

    val bakeryDetailLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_REFRESH_BAKERY_LIST) {
            Timber.i("xxx bakeryDetailLauncher :: Refresh bakery list")
        } else {
            Timber.i("xxx bakeryDetailLauncher :: Canceled")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.snackbarEffect.collectLatest { state ->
            showSnackbar(state)
        }
    }

    val isLastVisitedItemVisible by remember {
        derivedStateOf {
            val lastVisibleItemIndex = visitedListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val lastIndex = visitedListState.layoutInfo.totalItemsCount - 1
            state.visitedSection.paging.canRequest && lastVisibleItemIndex == lastIndex
        }
    }
    val isLastLikeItemVisible by remember {
        derivedStateOf {
            val lastVisibleItemIndex = likeListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val lastIndex = likeListState.layoutInfo.totalItemsCount - 1
            state.likeSection.paging.canRequest && lastVisibleItemIndex == lastIndex
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { isLastVisitedItemVisible }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                viewModel.intent(MyBakeryIntent.GetVisitedBakeries(refresh = false))
            }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { isLastLikeItemVisible }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                viewModel.intent(MyBakeryIntent.GetLikeBakeries(refresh = false))
            }
    }

    MyBakeryScreen(
        padding = padding,
        state = state,
        visitedListState = visitedListState,
        likeListState = likeListState,
        onTabSelect = { tab -> viewModel.intent(MyBakeryIntent.SelectTab(tab = tab)) },
        onSortSelect = { tab, sort ->
            when (tab) {
                Tab.VISITED -> viewModel.intent(MyBakeryIntent.SelectVisitedSort(sort = sort))
                Tab.LIKE -> viewModel.intent(MyBakeryIntent.SelectLikeSort(sort = sort))
            }
        },
        onBakeryClick = { bakery -> navigateToBakeryDetail(bakery.id, bakery.areaCode, bakeryDetailLauncher) },
        onBakeryLikeClick = { bakeryId, isLike -> viewModel.intent(MyBakeryIntent.ClickBakeryLike(bakeryId = bakeryId, isLike = isLike)) }
    )
}