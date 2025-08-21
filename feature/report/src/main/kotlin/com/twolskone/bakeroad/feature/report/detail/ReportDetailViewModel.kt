package com.twolskone.bakeroad.feature.report.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.model.ReportDate
import com.twolskone.bakeroad.feature.report.detail.mvi.ReportDetailIntent
import com.twolskone.bakeroad.feature.report.detail.mvi.ReportDetailSideEffect
import com.twolskone.bakeroad.feature.report.detail.mvi.ReportDetailState
import com.twolskone.bakeroad.feature.report.detail.navigation.ReportDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber

@HiltViewModel
internal class ReportDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ReportDetailState, ReportDetailIntent, ReportDetailSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): ReportDetailState {
        val dataList = Gson().fromJson<List<ReportDate>>(
            savedStateHandle.toRoute<ReportDetailRoute>().dateListJsonString,
            object : TypeToken<List<ReportDate>>() {}.type
        )
        return ReportDetailState(
            dateList = dataList.toImmutableList(),
            dateIndex = savedStateHandle.toRoute<ReportDetailRoute>().index
        ).also { Timber.i("xxx init ReportDetailState : $it") }
    }

    override fun handleException(cause: Throwable) {
        TODO("Not yet implemented")
    }

    override suspend fun handleIntent(intent: ReportDetailIntent) {
        TODO("Not yet implemented")
    }
}