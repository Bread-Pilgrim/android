package com.twolskone.bakeroad.feature.mybakery

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.twolskone.bakeroad.core.common.android.extension.ObserveError
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.core.navigator.model.RESULT_REFRESH_BAKERY_LIST
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakeryIntent
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakerySideEffect
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
internal fun MyBakeryRoute(
    viewModel: MyBakeryViewModel = hiltViewModel(),
    padding: PaddingValues,
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, launcher: ActivityResultLauncher<Intent>) -> Unit,
    showSnackbar: (SnackbarState) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tabState by viewModel.tabState.collectAsStateWithLifecycle()
    val sortState by viewModel.sortState.collectAsStateWithLifecycle()
    val bakeryPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()

    val bakeryDetailLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_REFRESH_BAKERY_LIST) {
            Timber.i("xxx bakeryDetailLauncher :: Refresh bakery list")
            bakeryPagingItems.refresh()
        } else {
            Timber.i("xxx bakeryDetailLauncher :: Canceled")
        }
    }

    bakeryPagingItems.ObserveError(viewModel = viewModel)

    LaunchedEffect(Unit) {
        viewModel.snackbarEffect.collectLatest { state ->
            showSnackbar(state)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.sideEffect.collect {
            when (it) {
                MyBakerySideEffect.RefreshBakeries -> bakeryPagingItems.refresh()
            }
        }
    }

    MyBakeryScreen(
        padding = padding,
        tabState = tabState,
        sortState = sortState,
        bakeryPagingItems = bakeryPagingItems,
        localLikeMap = state.localLikeMap,
        onTabSelect = { tab -> viewModel.intent(MyBakeryIntent.SelectTab(tab = tab)) },
        onSortSelect = { sort -> viewModel.intent(MyBakeryIntent.SelectSort(sort = sort)) },
        onBakeryClick = { bakery -> navigateToBakeryDetail(bakery.id, bakery.areaCode, bakeryDetailLauncher) },
        onBakeryLikeClick = { bakeryId, isLike -> viewModel.intent(MyBakeryIntent.ClickBakeryLike(bakeryId = bakeryId, isLike = isLike)) }
    )
}