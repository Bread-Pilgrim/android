package com.twolskone.bakeroad.feature.home.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.core.model.type.TourAreaCategory

internal sealed interface HomeIntent : BaseUiIntent {
    data class SelectArea(val selected: Boolean, val areaCode: Int) : HomeIntent
    data class SelectTourAreaCategory(val selected: Boolean, val category: TourAreaCategory) : HomeIntent
    data class RefreshBakeries(val completeSnackbarState: SnackbarState? = null) : HomeIntent
    data class DismissAreaEventSheet(val isTodayDismissed: Boolean) : HomeIntent
    data object PullToRefresh : HomeIntent
}