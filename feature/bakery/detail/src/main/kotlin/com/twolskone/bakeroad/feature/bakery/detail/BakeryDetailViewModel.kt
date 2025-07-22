package com.twolskone.bakeroad.feature.bakery.detail

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.domain.usecase.GetBakeryDetailUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetTourAreasUseCase
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import com.twolskone.bakeroad.feature.bakery.detail.model.toBakeryInfo
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailIntent
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailSideEffect
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList

private const val BAKERY_ID = "bakeryId"
private const val AREA_CODE = "areaCode"

@HiltViewModel
internal class BakeryDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getBakeryDetailUseCase: GetBakeryDetailUseCase,
    private val getTourAreasUseCase: GetTourAreasUseCase
) : BaseViewModel<BakeryDetailState, BakeryDetailIntent, BakeryDetailSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): BakeryDetailState {
        return BakeryDetailState()
    }

    init {
        getBakeryDetail()
        getTourAreas()
    }

    override fun handleException(cause: Throwable) {
    }

    override suspend fun handleIntent(intent: BakeryDetailIntent) {
        when (intent) {
            is BakeryDetailIntent.SelectTab -> reduce { copy(tab = intent.tab) }
        }
    }

    private fun getBakeryDetail() = launch {
        savedStateHandle.get<Int>(BAKERY_ID)?.let { id ->
            val bakeryDetail = getBakeryDetailUseCase(bakeryId = id)
            reduce {
                copy(
                    bakeryImageList = bakeryDetail.imageUrls.toImmutableList(),
                    bakeryInfo = bakeryDetail.toBakeryInfo(),
                    menuList = bakeryDetail.menus.toImmutableList()
                )
            }
        }
    }

    private fun getTourAreas() = launch {
        savedStateHandle.get<Int>(AREA_CODE)?.let { areaCode ->
            val tourAreas = getTourAreasUseCase(
                areaCodes = setOf(areaCode),
                tourCategories = TourAreaCategory.entries.toSet()
            )
            reduce { copy(tourAreaList = tourAreas.toImmutableList()) }
        }
    }
}