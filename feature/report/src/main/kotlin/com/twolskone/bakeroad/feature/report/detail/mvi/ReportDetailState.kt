package com.twolskone.bakeroad.feature.report.detail.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.ReportDate
import com.twolskone.bakeroad.core.model.ReportDetail
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class ReportDetailState(
    val loading: Boolean = true,
    val dateList: ImmutableList<ReportDate> = persistentListOf(),
    val dateIndex: Int = 0,
    val data: ReportDetail = ReportDetail.ofEmpty()
) : BaseUiState {

    val currentDate: ReportDate?
        get() = dateList.getOrNull(dateIndex)

    val hasPrevious: Boolean
        get() = dateIndex < dateList.lastIndex

    val hasNext: Boolean
        get() = dateIndex > 0
}