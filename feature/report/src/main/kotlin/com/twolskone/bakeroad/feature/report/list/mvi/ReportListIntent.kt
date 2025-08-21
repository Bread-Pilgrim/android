package com.twolskone.bakeroad.feature.report.list.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface ReportListIntent : BaseUiIntent {
    data class GetReportList(val refresh: Boolean) : ReportListIntent
}