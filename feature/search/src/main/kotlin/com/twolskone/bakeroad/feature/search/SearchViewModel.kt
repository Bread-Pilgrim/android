package com.twolskone.bakeroad.feature.search

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.feature.search.mvi.SearchIntent
import com.twolskone.bakeroad.feature.search.mvi.SearchSideEffect
import com.twolskone.bakeroad.feature.search.mvi.SearchState
import javax.inject.Inject
import timber.log.Timber

internal class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SearchState, SearchIntent, SearchSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): SearchState {
        return SearchState()
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

    override suspend fun handleIntent(intent: SearchIntent) {
        TODO("Not yet implemented")
    }
}