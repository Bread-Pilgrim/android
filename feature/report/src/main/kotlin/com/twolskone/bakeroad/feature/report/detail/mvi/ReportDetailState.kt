package com.twolskone.bakeroad.feature.report.detail.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.ReportDate
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class ReportDetailState(
    val dateList: ImmutableList<ReportDate> = persistentListOf(),
    val dateIndex: Int = 0,
) : BaseUiState