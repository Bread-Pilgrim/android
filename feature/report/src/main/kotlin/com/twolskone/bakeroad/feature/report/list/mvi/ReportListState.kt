package com.twolskone.bakeroad.feature.report.list.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.ReportDate
import com.twolskone.bakeroad.core.ui.model.CursorPagingUiState

@Immutable
internal data class ReportListState(
    val paging: CursorPagingUiState<ReportDate> = CursorPagingUiState()
) : BaseUiState