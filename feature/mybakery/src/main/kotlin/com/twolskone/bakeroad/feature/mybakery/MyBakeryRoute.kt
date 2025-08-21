package com.twolskone.bakeroad.feature.mybakery

import android.content.Intent
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.navigator.util.KEY_BAKERY_ID
import com.twolskone.bakeroad.core.navigator.util.KEY_BAKERY_LIKE
import com.twolskone.bakeroad.core.navigator.util.RESULT_REFRESH_BAKERY_LIST
import com.twolskone.bakeroad.feature.mybakery.model.Tab
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakeryIntent
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
internal fun MyBakeryRoute(
    viewModel: MyBakeryViewModel = hiltViewModel(),
    padding: PaddingValues,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, launcher: ActivityResultLauncher<Intent>) -> Unit,
    goBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val visitedListState = rememberLazyListState()
    val likeListState = rememberLazyListState()
    val bakeryDetailLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Timber.i("xxx bakeryDetailLauncher :: resultCode ${result.resultCode}")
        if (result.resultCode == RESULT_REFRESH_BAKERY_LIST) {
            val bakeryId = result.data?.getIntExtra(KEY_BAKERY_ID, -1) ?: return@rememberLauncherForActivityResult
            val isLike = result.data?.getBooleanExtra(KEY_BAKERY_LIKE, true) ?: return@rememberLauncherForActivityResult
            Timber.i("xxx bakeryDetailLauncher :: bakeryId($bakeryId), isLike($isLike)")
            if (!isLike) {
                viewModel.intent(MyBakeryIntent.DeleteBakery(bakeryId = bakeryId))
            }
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

    BackHandler { goBack() }

    LaunchedEffect(Unit) {
        viewModel.mainEventBus.myBakeryReselectEvent.collect {
            Timber.i("xxx collect myBakeryReselectEvent")
            when (state.tab) {
                Tab.VISITED -> visitedListState.animateScrollToItem(0)
                Tab.LIKE -> likeListState.animateScrollToItem(0)
            }
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
                Tab.VISITED -> {
                    scope.launch { visitedListState.scrollToItem(0) }
                    viewModel.intent(MyBakeryIntent.SelectVisitedSort(sort = sort))
                }

                Tab.LIKE -> {
                    scope.launch { likeListState.scrollToItem(0) }
                    viewModel.intent(MyBakeryIntent.SelectLikeSort(sort = sort))
                }
            }
        },
        onBakeryClick = { bakery -> navigateToBakeryDetail(bakery.id, bakery.areaCode, bakeryDetailLauncher) },
        onRefresh = {
            scope.launch {
                visitedListState.scrollToItem(0)
                likeListState.scrollToItem(0)
            }
            viewModel.intent(MyBakeryIntent.Refresh)
        }
    )
}