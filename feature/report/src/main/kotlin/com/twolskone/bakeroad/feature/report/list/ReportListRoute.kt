package com.twolskone.bakeroad.feature.report.list

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
import com.twolskone.bakeroad.core.model.ReportDate
import com.twolskone.bakeroad.feature.report.list.mvi.ReportListIntent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
internal fun ReportListRoute(
    viewModel: ReportListViewModel = hiltViewModel(),
    navigateToReportDetail: (ImmutableList<ReportDate>, Int) -> Unit
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
                viewModel.intent(ReportListIntent.GetReportList(refresh = false))
            }
    }

    BaseComposable(baseViewModel = viewModel) {
        ReportListScreen(
            state = state,
            listState = listState,
            onBackClick = {},
            onItemClick = { index -> navigateToReportDetail(state.paging.list, index) }
        )
    }
}