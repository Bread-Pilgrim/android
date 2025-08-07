package com.twolskone.bakeroad.feature.bakery.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.bakery.GetBakeriesUseCase
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.core.model.EntireBusan
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.feature.bakery.list.mvi.BakeryListIntent
import com.twolskone.bakeroad.feature.bakery.list.mvi.BakeryListSideEffect
import com.twolskone.bakeroad.feature.bakery.list.mvi.BakeryListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

private const val AREA_CODES = "areaCodes"
private const val BAKERY_TYPE = "bakeryType"

@HiltViewModel
internal class BakeryListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getBakeriesUseCase: GetBakeriesUseCase
) : BaseViewModel<BakeryListState, BakeryListIntent, BakeryListSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): BakeryListState {
        return BakeryListState(bakeryType = savedStateHandle.get<BakeryType>(BAKERY_TYPE) ?: BakeryType.PREFERENCE)
    }

    val pagingFlow = getBakeriesUseCase(
        areaCodes = savedStateHandle.get<String>(AREA_CODES) ?: EntireBusan.toString(),
        bakeryType = savedStateHandle.get<BakeryType>(BAKERY_TYPE) ?: BakeryType.PREFERENCE
    ).cachedIn(viewModelScope)

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

    override suspend fun handleIntent(intent: BakeryListIntent) {}
}