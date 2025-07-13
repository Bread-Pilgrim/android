package com.twolskone.bakeroad.feature.home

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.domain.usecase.GetAreasUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetRecommendHotBakeriesUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetRecommendPreferenceBakeriesUseCase
import com.twolskone.bakeroad.core.domain.usecase.GetTourAreasUseCase
import com.twolskone.bakeroad.feature.home.mvi.EntireBusan
import com.twolskone.bakeroad.feature.home.mvi.HomeIntent
import com.twolskone.bakeroad.feature.home.mvi.HomeSideEffect
import com.twolskone.bakeroad.feature.home.mvi.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAreasUseCase: GetAreasUseCase,
    private val getPreferenceBakeriesUseCase: GetRecommendPreferenceBakeriesUseCase,
    private val getHotBakeriesUseCase: GetRecommendHotBakeriesUseCase,
    private val getTourAreasUseCase: GetTourAreasUseCase
) : BaseViewModel<HomeState, HomeIntent, HomeSideEffect>(savedStateHandle) {

    init {
        getAreas()
    }

    override fun initState(savedStateHandle: SavedStateHandle): HomeState {
        return HomeState()
    }

    override suspend fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.RefreshAll -> refreshAll()

            is HomeIntent.SelectArea -> reduce {
                val selectedAreaCodes = if (intent.selected) {
                    if (intent.areaCode == EntireBusan) {
                        state.value.selectedAreaCodes
                            .clear()
                            .add(intent.areaCode)
                    } else {
                        state.value.selectedAreaCodes
                            .remove(EntireBusan)
                            .add(intent.areaCode)
                    }
                } else if (state.value.selectedAreaCodes.size > 1) {
                    state.value.selectedAreaCodes.remove(intent.areaCode)
                } else {
                    state.value.selectedAreaCodes
                }

                copy(selectedAreaCodes = selectedAreaCodes)
            }

            is HomeIntent.SelectTourAreaCategory -> {}
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
            tourAreaCategory = state.value.selectedTourAreaCategories.first()   // TODO.
        )
        reduce { copy(tourAreaList = tourAreas.toImmutableList()) }
    }
}