package com.twolskone.bakeroad.feature.bakery.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.common.kotlin.extension.orZero
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.GetBakeryDetailUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetBakeryMyReviewsUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetBakeryPreviewReviewsUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetBakeryReviewsUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetTourAreasUseCase
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.core.model.type.ReviewSortType
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import com.twolskone.bakeroad.feature.bakery.detail.model.BakeryDetailTab
import com.twolskone.bakeroad.feature.bakery.detail.model.ReviewTab
import com.twolskone.bakeroad.feature.bakery.detail.model.toBakeryInfo
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailIntent
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailSideEffect
import com.twolskone.bakeroad.feature.bakery.detail.mvi.BakeryDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
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

    val bakeryId: Int = savedStateHandle.get<Int>(BAKERY_ID).orZero()

    private val _tabState = MutableStateFlow(BakeryDetailTab.HOME)
    val tabState: StateFlow<BakeryDetailTab>
        get() = _tabState.asStateFlow()

    private val _reviewTabState = MutableStateFlow(ReviewTab.MY_REVIEW)
    val reviewTabState: StateFlow<ReviewTab>
        get() = _reviewTabState.asStateFlow()

    private val _reviewSortState = MutableStateFlow(ReviewSortType.LIKE_COUNT_DESC)
    val reviewSortState: StateFlow<ReviewSortType>
        get() = _reviewSortState.asStateFlow()

    val myReviewPagingFlow by lazy {
        combine(tabState, reviewTabState) { currentTab, currentReviewTab ->
            currentTab == BakeryDetailTab.REVIEW && currentReviewTab == ReviewTab.MY_REVIEW
        }.distinctUntilChanged()
            .filter { it }
            .flatMapLatest {
                getBakeryMyReviewsUseCase(bakeryId = bakeryId).cachedIn(viewModelScope)
            }
    }
    val reviewPagingFlow by lazy {
        combine(tabState, reviewTabState, reviewSortState) { tab, reviewTab, reviewSort ->
            Triple(tab, reviewTab, reviewSort)
        }.distinctUntilChanged()
            .filter { (tab, reviewTab, _) -> tab == BakeryDetailTab.REVIEW && reviewTab == ReviewTab.ALL_REVIEW }
            .flatMapLatest { (_, _, reviewSort) ->
                getBakeryReviewsUseCase(bakeryId = bakeryId, reviewSortType = reviewSort).cachedIn(viewModelScope)
            }
    }

    init {
        getBakeryDetail()
        getPreviewReviews()
        getTourAreas()
    }

    override fun handleException(cause: Throwable) {
        Timber.e(cause)
        when (cause) {
            is ClientException -> {
                showSnackbar(
                    type = SnackbarType.ERROR,
                    message = cause.message,
                    messageRes = cause.error?.messageId
                )
            }

            is BakeRoadException -> {
                showSnackbar(type = SnackbarType.ERROR, message = cause.message)
            }
        }
    }

    override suspend fun handleIntent(intent: BakeryDetailIntent) {
        when (intent) {
            is BakeryDetailIntent.SelectTab -> _tabState.value = intent.tab
            is BakeryDetailIntent.SelectReviewTab -> _reviewTabState.value = intent.tab
            is BakeryDetailIntent.SelectReviewSort -> _reviewSortState.value = intent.sort
        }
    }

    private fun getBakeryDetail() = launch {
        val bakeryDetail = getBakeryDetailUseCase(bakeryId = bakeryId)
        reduce {
            copy(
                bakeryImageList = bakeryDetail.imageUrls.toImmutableList(),
                bakeryInfo = bakeryDetail.toBakeryInfo(),
                menuList = bakeryDetail.menus.toImmutableList(),
                reviewState = reviewState.copy(
                    count = bakeryDetail.reviewCount,
                    avgRating = bakeryDetail.rating
                )
            )
        }
    }

    private fun getPreviewReviews() = launch {
        val reviews = getBakeryPreviewReviewsUseCase(bakeryId = bakeryId)
        reduce {
            copy(
                reviewState = reviewState.copy(
                    previewReviewList = reviews.toImmutableList()
                )
            )
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