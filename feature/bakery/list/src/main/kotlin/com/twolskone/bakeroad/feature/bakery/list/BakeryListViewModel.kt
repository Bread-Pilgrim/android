package com.twolskone.bakeroad.feature.bakery.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.domain.usecase.GetBakeriesUseCase
import com.twolskone.bakeroad.core.model.EntireBusan
import com.twolskone.bakeroad.core.model.type.BakeryType
import com.twolskone.bakeroad.feature.bakery.list.mvi.BakeryListIntent
import com.twolskone.bakeroad.feature.bakery.list.mvi.BakeryListSideEffect
import com.twolskone.bakeroad.feature.bakery.list.mvi.BakeryListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch

private const val AREA_CODE = "areaCode"
private const val BAKERY_TYPE = "bakeryType"

@HiltViewModel
internal class BakeryListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getBakeriesUseCase: GetBakeriesUseCase
) : BaseViewModel<BakeryListState, BakeryListIntent, BakeryListSideEffect>(savedStateHandle) {

    val pagingFlow = getBakeriesUseCase(
        areaCode = savedStateHandle.get<String>(AREA_CODE) ?: EntireBusan.toString(),
        bakeryType = savedStateHandle.get<BakeryType>(BAKERY_TYPE) ?: BakeryType.PREFERENCE
    ).cachedIn(viewModelScope).catch { cause -> handleException(cause) }

    override fun initState(savedStateHandle: SavedStateHandle): BakeryListState {
        return BakeryListState(bakeryType = savedStateHandle.get<BakeryType>(BAKERY_TYPE) ?: BakeryType.PREFERENCE)
    }

    override fun handleException(cause: Throwable) {}

    override suspend fun handleIntent(intent: BakeryListIntent) {}
}