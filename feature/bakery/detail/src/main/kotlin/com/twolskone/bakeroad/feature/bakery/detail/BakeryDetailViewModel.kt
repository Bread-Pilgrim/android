package com.twolskone.bakeroad.feature.bakery.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.common.kotlin.extension.orZero
import com.twolskone.bakeroad.core.domain.usecase.GetBakeryDetailUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetBakeryMyReviewsUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetBakeryPreviewReviewsUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetBakeryReviewsUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetTourAreasUseCase
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import com.twolskone.bakeroad.feature.bakery.detail.model.toBakeryInfo
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailIntent
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailSideEffect
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.catch
import timber.log.Timber

private const val BAKERY_ID = "bakeryId"
private const val AREA_CODE = "areaCode"

@HiltViewModel
internal class BakeryDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getBakeryDetailUseCase: GetBakeryDetailUseCase,
    private val getBakeryPreviewReviewsUseCase: GetBakeryPreviewReviewsUseCase,
    private val getTourAreasUseCase: GetTourAreasUseCase,
    private val getBakeryMyReviewsUseCase: GetBakeryMyReviewsUseCase,
    private val getBakeryReviewsUseCase: GetBakeryReviewsUseCase
) : BaseViewModel<BakeryDetailState, BakeryDetailIntent, BakeryDetailSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): BakeryDetailState {
        return BakeryDetailState()
    }

    val myReviewPagingFlow by lazy {
        getBakeryMyReviewsUseCase(
            bakeryId = savedStateHandle.get<Int>(BAKERY_ID).orZero()
        ).cachedIn(viewModelScope).catch { cause -> handleException(cause) }
    }

    val reviewPagingFlow by lazy {
        getBakeryReviewsUseCase(
            bakeryId = savedStateHandle.get<Int>(BAKERY_ID).orZero(),
            reviewSortType = state.value.reviewState.reviewSortType
        ).cachedIn(viewModelScope).catch { cause -> handleException(cause) }
    }

    init {
        getBakeryDetail()
        getPreviewReviews()
        getTourAreas()
    }

    override fun handleException(cause: Throwable) {
        Timber.e(cause)
    }

    override suspend fun handleIntent(intent: BakeryDetailIntent) {
        when (intent) {
            is BakeryDetailIntent.SelectTab -> reduce { copy(tab = intent.tab) }

            is BakeryDetailIntent.SelectReviewTab -> reduce {
                copy(
                    reviewState = reviewState.copy(
                        tab = intent.tab
                    )
                )
            }
        }
    }

    private fun getBakeryDetail() = launch {
        savedStateHandle.get<Int>(BAKERY_ID)?.let { id ->
            val bakeryDetail = getBakeryDetailUseCase(bakeryId = id)
            reduce {
                copy(
                    bakeryImageList = bakeryDetail.imageUrls.toImmutableList(),
                    bakeryInfo = bakeryDetail.toBakeryInfo(),
                    menuList = bakeryDetail.menus.toImmutableList(),
                    reviewState = reviewState.copy(reviewCount = bakeryDetail.reviewCount)
                )
            }
        }
    }

    private fun getPreviewReviews() = launch {
        savedStateHandle.get<Int>(BAKERY_ID)?.let { id ->
            val reviews = getBakeryPreviewReviewsUseCase(bakeryId = id)
            reduce {
                copy(
                    reviewState = reviewState.copy(
                        previewReviewList = reviews.toImmutableList()
                    )
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