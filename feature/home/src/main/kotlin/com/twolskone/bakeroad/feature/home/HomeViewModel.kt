package com.twolskone.bakeroad.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.area.GetAreasUseCase
import com.twolskone.bakeroad.core.domain.usecase.bakery.GetRecommendHotBakeriesUseCase
import com.twolskone.bakeroad.core.domain.usecase.bakery.GetRecommendPreferenceBakeriesUseCase
import com.twolskone.bakeroad.core.domain.usecase.tour.GetAreaEventUseCase
import com.twolskone.bakeroad.core.domain.usecase.tour.GetTourAreasUseCase
import com.twolskone.bakeroad.core.domain.usecase.tour.SetAreaEventDismissedTimeMillisUseCase
import com.twolskone.bakeroad.core.eventbus.MainEventBus
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.model.EntireBusan
import com.twolskone.bakeroad.core.navigator.util.KEY_BADGE_ACHIEVED
import com.twolskone.bakeroad.feature.home.mvi.HomeIntent
import com.twolskone.bakeroad.feature.home.mvi.HomeSideEffect
import com.twolskone.bakeroad.feature.home.mvi.HomeState
import com.twolskone.bakeroad.feature.home.mvi.SheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.joinAll
import timber.log.Timber

private const val BakeryMaxCount = 20
private const val TourAreaMaxCount = 5

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val mainEventBus: MainEventBus,
    private val getAreasUseCase: GetAreasUseCase,
    private val getPreferenceBakeriesUseCase: GetRecommendPreferenceBakeriesUseCase,
    private val getHotBakeriesUseCase: GetRecommendHotBakeriesUseCase,
    private val getTourAreasUseCase: GetTourAreasUseCase,
    private val getAreaEventUseCase: GetAreaEventUseCase,
    private val setAreaEventDismissedTimeMillisUseCase: SetAreaEventDismissedTimeMillisUseCase
) : BaseViewModel<HomeState, HomeIntent, HomeSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): HomeState {
        return HomeState()
    }

    val achievedBadges = savedStateHandle.getStateFlow(KEY_BADGE_ACHIEVED, emptyList<Badge>())

    private val areaTrigger = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1)
    private val tourAreaCategoryTrigger = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1)

    init {
        observeTrigger()
        getAreas()
        refreshAll()
        getAreaEvent()
    }

    override suspend fun handleIntent(intent: HomeIntent) {
        when (intent) {
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

                    (originAreaCodes.size > 1) -> originAreaCodes.remove(intent.areaCode)

                    else -> originAreaCodes
                }
                if (originAreaCodes != selectedAreaCodes) {
                    areaTrigger.tryEmit(Unit)
                }
                copy(selectedAreaCodes = selectedAreaCodes)
            }

            is HomeIntent.SelectTourAreaCategory -> reduce {
                val originTourCategories = selectedTourAreaCategories
                val selectedTourCategories = when {
                    (intent.selected) -> originTourCategories.add(intent.category)
                    (originTourCategories.size > 1) -> originTourCategories.remove(intent.category)
                    else -> originTourCategories
                }
                if (originTourCategories != selectedTourCategories) {
                    tourAreaCategoryTrigger.tryEmit(Unit)
                }
                copy(selectedTourAreaCategories = selectedTourCategories)
            }

            is HomeIntent.RefreshBakeries -> {
                val preferenceBakeriesJob = refreshPreferenceBakeries()
                val hotBakeriesJob = refreshHotBakeries()
                joinAll(preferenceBakeriesJob, hotBakeriesJob)
                intent.completeSnackbarState?.let { snackbarState ->
                    mainEventBus.showSnackbar(snackbarState = snackbarState)
                    showSnackbar(
                        type = snackbarState.type,
                        message = snackbarState.message,
                        messageRes = snackbarState.messageRes
                    )
                }
            }

            is HomeIntent.DismissAreaEventSheet -> {
                if (intent.isTodayDismissed) {
                    setAreaEventDismissedTimeMillisUseCase(timeMillis = System.currentTimeMillis())
                }
                reduce { copy(sheetState = SheetState.Nothing) }
            }
        }
    }

    override fun handleException(cause: Throwable) {
        Timber.e(cause)
        launch {
            when (cause) {
                is ClientException -> {
                    mainEventBus.showSnackbar(
                        snackbarState = SnackbarState(
                            type = SnackbarType.ERROR,
                            message = cause.message,
                            messageRes = cause.error?.messageRes
                        )
                    )
                }

                is BakeRoadException -> {
                    mainEventBus.showSnackbar(
                        snackbarState = SnackbarState(type = SnackbarType.ERROR, message = cause.message)
                    )
                }
            }
        }
    }

    /**
     * 지역 필터
     */
    private fun getAreas() = launch {
        val areas = getAreasUseCase()
        reduce {
            copy(
                loadingState = loadingState.copy(areaLoading = false),
                areaList = areas.toImmutableList()
            )
        }
    }

    /**
     * 전체 목록 갱신
     * - 내 취향 추천 빵집
     * - Hot한 빵집 모음
     * - 같이 가볼만한 관광지
     */
    private fun refreshAll() {
        launch {
            reduce { copy(loadingState = loadingState.copy(allLoading = true)) }
            val preferenceBakeriesJob = refreshPreferenceBakeries()
            val hotBakeriesJob = refreshHotBakeries()
            val tourAreasJob = refreshTourAreas()
            joinAll(preferenceBakeriesJob, hotBakeriesJob, tourAreasJob)
            reduce { copy(loadingState = loadingState.copy(allLoading = false)) }
        }
    }

    /**
     * 내 취향 추천 빵집 (최대 20개)
     */
    private fun refreshPreferenceBakeries() = launch {
        reduce { copy(loadingState = loadingState.copy(preferenceBakeryLoading = true)) }
        val preferenceBakeries = getPreferenceBakeriesUseCase(areaCodes = state.value.selectedAreaCodes)
            .take(BakeryMaxCount)
        reduce {
            copy(
                loadingState = loadingState.copy(preferenceBakeryLoading = false),
                preferenceBakeryList = preferenceBakeries.toImmutableList()
            )
        }
    }

    /**
     * Hot한 빵집 모음 (최대 20개)
     */
    private fun refreshHotBakeries() = launch {
        reduce { copy(loadingState = loadingState.copy(hotBakeryLoading = true)) }
        val hotBakeries = getHotBakeriesUseCase(areaCodes = state.value.selectedAreaCodes)
            .take(BakeryMaxCount)
        reduce {
            copy(
                loadingState = loadingState.copy(hotBakeryLoading = false),
                hotBakeryList = hotBakeries.toImmutableList()
            )
        }
    }

    /**
     * 같이 가볼만한 관광지 (최대 5개)
     */
    private fun refreshTourAreas() = launch {
        reduce { copy(loadingState = loadingState.copy(tourAreaLoading = true)) }
        val tourAreas = getTourAreasUseCase(
            areaCodes = state.value.selectedAreaCodes,
            tourCategories = state.value.selectedTourAreaCategories
        ).take(TourAreaMaxCount)
        reduce {
            copy(
                loadingState = loadingState.copy(tourAreaLoading = false),
                tourAreaList = tourAreas.toImmutableList()
            )
        }
    }

    /**
     * 지역 행사
     */
    private fun getAreaEvent() = launch {
        val areaEvent = getAreaEventUseCase(areaCodes = state.value.selectedAreaCodes)
        reduce { copy(sheetState = SheetState.AreaEventSheet(data = areaEvent)) }
    }

    @OptIn(FlowPreview::class)
    private fun observeTrigger() {
        areaTrigger
            .debounce(350L)
            .onEach { refreshAll() }
            .launchIn(viewModelScope)

        tourAreaCategoryTrigger
            .debounce(350L)
            .onEach { refreshTourAreas() }
            .launchIn(viewModelScope)
    }
}