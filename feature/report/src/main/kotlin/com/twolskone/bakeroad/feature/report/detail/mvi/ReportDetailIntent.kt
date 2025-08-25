package com.twolskone.bakeroad.feature.report.detail.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface ReportDetailIntent : BaseUiIntent {
    data object LoadPrevious : ReportDetailIntent
    data object LoadNext : ReportDetailIntent
}