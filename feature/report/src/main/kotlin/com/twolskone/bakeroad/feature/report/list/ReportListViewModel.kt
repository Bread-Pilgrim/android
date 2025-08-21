package com.twolskone.bakeroad.feature.report.list

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.user.GetReportMonthlyListUseCase
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.core.model.paging.StartCursor
import com.twolskone.bakeroad.feature.report.list.mvi.ReportListIntent
import com.twolskone.bakeroad.feature.report.list.mvi.ReportListSideEffect
import com.twolskone.bakeroad.feature.report.list.mvi.ReportListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
internal class ReportListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getReportMonthlyListUseCase: GetReportMonthlyListUseCase
) : BaseViewModel<ReportListState, ReportListIntent, ReportListSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): ReportListState {
        return ReportListState()
    }

    init {
        getReportList(refresh = true)
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

    override suspend fun handleIntent(intent: ReportListIntent) {
        when (intent) {
            is ReportListIntent.GetReportList -> getReportList(refresh = intent.refresh)
        }
    }

    private fun getReportList(refresh: Boolean) {
        launch {
            if (!refresh && !state.value.paging.canRequest) {
                Timber.i("xxx ReportListPaging is loading or last page.")
                return@launch
            }
            val nextPaging = getReportMonthlyListUseCase(cursor = if (refresh) StartCursor else state.value.paging.nextCursor)
            reduce {
                copy(
                    paging = if (refresh) {
                        paging.refresh(next = nextPaging)
                    } else {
                        paging.merge(next = nextPaging)
                    }
                )
            }
        }
    }
}