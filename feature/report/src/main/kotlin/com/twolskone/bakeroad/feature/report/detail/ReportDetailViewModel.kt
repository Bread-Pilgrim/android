package com.twolskone.bakeroad.feature.report.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.user.GetReportDetailUseCase
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
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
    savedStateHandle: SavedStateHandle,
    private val getReportDetailUseCase: GetReportDetailUseCase
) : BaseViewModel<ReportDetailState, ReportDetailIntent, ReportDetailSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): ReportDetailState {
        val dataList = Gson().fromJson<List<ReportDate>>(
            savedStateHandle.toRoute<ReportDetailRoute>().dateListJsonString,
            object : TypeToken<List<ReportDate>>() {}.type
        )
        return ReportDetailState(
            dateList = dataList.toImmutableList(),
            dateIndex = savedStateHandle.toRoute<ReportDetailRoute>().index
        )
    }

    init {
        launch {
            state.value.currentDate?.let { date ->
                val result = getReportDetailUseCase(year = date.year, month = date.month)
                reduce { copy(loading = false, data = result) }
            } ?: Timber.e("xxx current date is null")
        }
    }

    override fun handleException(cause: Throwable) {
        Timber.e(cause)
        when (cause) {
            is ClientException -> {
                showSnackbar(
                    type = SnackbarType.ERROR,
                    message = cause.message,
                    messageRes = cause.error?.messageRes
                )
            }

            is BakeRoadException -> {
                showSnackbar(type = SnackbarType.ERROR, message = cause.message)
            }
        }
    }

    override suspend fun handleIntent(intent: ReportDetailIntent) {
        when (intent) {
            ReportDetailIntent.LoadPrevious -> getPrevious()
            ReportDetailIntent.LoadNext -> getNext()
        }
    }

    private fun getPrevious() = launch {
        if (!state.value.hasPrevious) return@launch

        val prevIndex = state.value.dateIndex + 1
        val date = state.value.dateList[prevIndex]
        reduce { copy(loading = true, dateIndex = prevIndex) }

        val result = getReportDetailUseCase(year = date.year, month = date.month)
        reduce { copy(loading = false, data = result) }
    }

    private fun getNext() = launch {
        if (!state.value.hasNext) return@launch

        val nextIndex = state.value.dateIndex - 1
        val date = state.value.dateList[nextIndex]
        reduce { copy(loading = true, dateIndex = nextIndex) }

        val result = getReportDetailUseCase(year = date.year, month = date.month)
        reduce { copy(loading = false, data = result) }
    }
}