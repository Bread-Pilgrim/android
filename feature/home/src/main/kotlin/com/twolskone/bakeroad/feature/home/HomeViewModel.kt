package com.twolskone.bakeroad.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.domain.usecase.GetAreasUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetRecommendHotBakeriesUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetRecommendPreferenceBakeriesUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetTourAreasUseCase
import com.twolskone.bakeroad.core.model.EntireBusan
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import com.twolskone.bakeroad.feature.home.mvi.HomeIntent
import com.twolskone.bakeroad.feature.home.mvi.HomeSideEffect
import com.twolskone.bakeroad.feature.home.mvi.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAreasUseCase: GetAreasUseCase,
    private val getPreferenceBakeriesUseCase: GetRecommendPreferenceBakeriesUseCase,
    private val getHotBakeriesUseCase: GetRecommendHotBakeriesUseCase,
    private val getTourAreasUseCase: GetTourAreasUseCase
) : BaseViewModel<HomeState, HomeIntent, HomeSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): HomeState {
        return HomeState()
    }

    private val areaTrigger = MutableSharedFlow<Set<Int>>(replay = 0, extraBufferCapacity = 1)

    private val tourAreaCategoryTrigger = MutableSharedFlow<Set<TourAreaCategory>>(replay = 0, extraBufferCapacity = 1)

    init {
        observeTrigger()
        getAreas()
        refreshAll()
    }

    override suspend fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.RefreshAll -> refreshAll()

            is HomeIntent.SelectArea -> reduce {
                val originAreaCodes = selectedAreaCodes
                val selectedAreaCodes = when {
                    (intent.selected) -> {
                        originAreaCodes.run {
                            if (intent.areaCode == EntireBusan) {
                                clear()
                            } else {
                                remove(EntireBusan)
                            }
                        }.add(intent.areaCode)
                    }

                    (originAreaCodes.size > 1) -> {
                        originAreaCodes.remove(intent.areaCode)
                    }

                    else -> {
                        originAreaCodes
                    }
                }
                areaTrigger.tryEmit(selectedAreaCodes)
                copy(selectedAreaCodes = selectedAreaCodes)
            }

            is HomeIntent.SelectTourAreaCategory -> reduce {
                val originTourCategories = selectedTourAreaCategories
                val selectedTourCategories = when {
                    (intent.selected) -> originTourCategories.add(intent.category)
                    (originTourCategories.size > 1) -> originTourCategories.remove(intent.category)
                    else -> originTourCategories
                }
                tourAreaCategoryTrigger.tryEmit(selectedTourCategories)
                copy(selectedTourAreaCategories = selectedTourCategories)
            }
        }
    }

    override fun handleException(cause: Throwable) {
        Timber.e(cause)
    }

    /**
     * 지역 필터
     */
    private fun getAreas() = launch {
        val areas = getAreasUseCase()
        reduce { copy(areaList = areas.toImmutableList()) }
    }

    /**
     * 전체 목록 갱신
     * - 내 취향 추천 빵집
     * - Hot한 빵집 모음
     * - 같이 가볼만한 관광지
     */
    private fun refreshAll() {
        refreshPreferenceBakeries()
        refreshHotBakeries()
        refreshTourAreas()
    }

    /**
     * 내 취향 추천 빵집
     */
    private fun refreshPreferenceBakeries() = launch {
        val preferenceBakeries = getPreferenceBakeriesUseCase(areaCodes = state.value.selectedAreaCodes)
        reduce { copy(preferenceBakeryList = preferenceBakeries.toImmutableList()) }
    }


    /**
     * Hot한 빵집 모음
     */
    private fun refreshHotBakeries() = launch {
        val hotBakeries = getHotBakeriesUseCase(areaCodes = state.value.selectedAreaCodes)
        reduce { copy(hotBakeryList = hotBakeries.toImmutableList()) }
    }

    /**
     * 같이 가볼만한 관광지
     */
    private fun refreshTourAreas() = launch {
        val tourAreas = getTourAreasUseCase(
            areaCodes = state.value.selectedAreaCodes,
            tourCategories = state.value.selectedTourAreaCategories
        )
        reduce { copy(tourAreaList = tourAreas.toImmutableList()) }
    }

    @OptIn(FlowPreview::class)
    private fun observeTrigger() {
        areaTrigger
            .debounce(400L)
            .filter { areaCodes -> state.value.selectedAreaCodes != areaCodes }
            .onEach { refreshAll() }
            .launchIn(viewModelScope)

        tourAreaCategoryTrigger
            .debounce(400L)
            .filter { tourAreaCategories -> state.value.selectedTourAreaCategories != tourAreaCategories }
            .onEach { refreshTourAreas() }
            .launchIn(viewModelScope)
    }
}