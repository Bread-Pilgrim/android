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
        Timber.i("xxx bakeryDetailLauncher :: resultCode ${result.resultCode}")
    }

    BackHandler {
        setResult(RESULT_REFRESH_BAKERY_LIST, true)
    }

    lazyPagingItems.ObserveError(viewModel = viewModel)

    BaseComposable(baseViewModel = viewModel) {
        BakeryListScreen(
            bakeryType = state.bakeryType,
            pagingItems = lazyPagingItems,
            onBakeryClick = { bakery -> navigateToBakeryDetail(bakery.id, bakery.areaCode, bakeryDetailLauncher) }
        )
    }
}