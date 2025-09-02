package com.twolskone.bakeroad.feature.report.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twolskone.bakeroad.core.common.android.base.BaseComposable
import com.twolskone.bakeroad.feature.report.detail.mvi.ReportDetailIntent

@Composable
internal fun ReportDetailRoute(
    viewModel: ReportDetailViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BaseComposable(baseViewModel = viewModel) {
        ReportDetailScreen(
            state = state,
            onBackClick = goBack,
            onPreviousClick = { viewModel.intent(ReportDetailIntent.LoadPrevious) },
            onNextClick = { viewModel.intent(ReportDetailIntent.LoadNext) }
        )
    }
}