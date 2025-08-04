package com.twolskone.bakeroad.feature.bakery.list

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.core.common.android.extension.ObserveError
import com.twolskone.bakeroad.core.navigator.model.RESULT_REFRESH_BAKERY_LIST
import com.twolskone.bakeroad.feature.bakery.list.mvi.BakeryListIntent
import timber.log.Timber

@Composable
internal fun BakeryListRoute(
    viewModel: BakeryListViewModel = hiltViewModel(),
    navigateToBakeryDetail: (bakeryId: Int, areaCode: Int, ActivityResultLauncher<Intent>) -> Unit,
    setResult: (code: Int, withFinish: Boolean) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lazyPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()
    val bakeryDetailLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_REFRESH_BAKERY_LIST) {
            Timber.i("xxx bakeryDetailLauncher :: Refresh bakery list")
            lazyPagingItems.refresh()
        } else {
            Timber.i("xxx bakeryDetailLauncher :: Canceled")
        }
    }

    BackHandler {
        setResult(RESULT_REFRESH_BAKERY_LIST, true)
    }

    lazyPagingItems.ObserveError(viewModel = viewModel)

    BaseComposable(baseViewModel = viewModel) {
        BakeryListScreen(
            bakeryType = state.bakeryType,
            pagingItems = lazyPagingItems,
            localLikeMap = state.localLikeMap,
            onBakeryClick = { bakery -> navigateToBakeryDetail(bakery.id, bakery.areaCode, bakeryDetailLauncher) },
            onBakeryLikeClick = { bakeryId, isLike ->
                viewModel.intent(
                    BakeryListIntent.ClickBakeryLike(
                        bakeryId = bakeryId,
                        isLike = isLike
                    )
                )
            }
        )
    }
}