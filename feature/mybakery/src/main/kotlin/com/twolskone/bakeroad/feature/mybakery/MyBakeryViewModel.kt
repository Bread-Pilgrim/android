package com.twolskone.bakeroad.feature.mybakery

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarState
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.bakery.GetMyBakeriesUseCase
import com.twolskone.bakeroad.core.eventbus.MainEventBus
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.core.model.paging.startPage
import com.twolskone.bakeroad.core.model.type.MyBakeryType
import com.twolskone.bakeroad.feature.mybakery.model.Tab
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakeryIntent
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakerySideEffect
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakeryState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
internal class MyBakeryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mainEventBus: MainEventBus,
    private val getMyBakeriesUseCase: GetMyBakeriesUseCase
) : BaseViewModel<MyBakeryState, MyBakeryIntent, MyBakerySideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): MyBakeryState {
        return MyBakeryState()
    }

    init {
        getVisitedBakeries(refresh = true)
        getLikeBakeries(refresh = true)
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

    override suspend fun handleIntent(intent: MyBakeryIntent) {
        when (intent) {
            is MyBakeryIntent.SelectTab -> reduce { copy(tab = intent.tab) }

            is MyBakeryIntent.SelectVisitedSort -> {
                reduce { copy(visitedSection = visitedSection.copy(sort = intent.sort)) }
                getVisitedBakeries(refresh = true)
            }

            is MyBakeryIntent.SelectLikeSort -> {
                reduce { copy(likeSection = likeSection.copy(sort = intent.sort)) }
                getLikeBakeries(refresh = true)
            }

            is MyBakeryIntent.GetVisitedBakeries -> getVisitedBakeries(refresh = intent.refresh)

            is MyBakeryIntent.GetLikeBakeries -> getLikeBakeries(refresh = intent.refresh)

            is MyBakeryIntent.DeleteBakery -> reduce {
                copy(
                    likeSection = likeSection.copy(
                        paging = likeSection.paging.copy(
                            list = likeSection.paging.list.removeAll { it.id == intent.bakeryId }
                        )
                    )
                )
            }

            MyBakeryIntent.Refresh -> {
                reduce { copy(isRefreshing = true) }
                when (state.value.tab) {
                    Tab.VISITED -> getVisitedBakeries(refresh = true)
                    Tab.LIKE -> getLikeBakeries(refresh = true)
                }
            }
        }
    }

    private fun getVisitedBakeries(refresh: Boolean) {
        launch {
            val section = state.value.visitedSection
            if (!refresh && !section.paging.canRequest) {
                Timber.i("xxx visitedPaging is loading or last page")
                return@launch
            }
            reduce {
                copy(
                    visitedSection = visitedSection.copy(
                        loading = !state.value.isRefreshing && refresh, // PullToRefresh 는 스켈레톤 제외.
                        paging = visitedSection.paging.copy(isLoading = true)
                    )
                )
            }
            val nextPaging = getMyBakeriesUseCase(
                page = if (refresh) startPage else section.paging.nextPage,
                myBakeryType = MyBakeryType.VISITED,
                sort = section.sort
            )
            reduce {
                copy(
                    isRefreshing = false,
                    visitedSection = visitedSection.copy(
                        loading = false,
                        paging = if (refresh) {
                            visitedSection.paging.refresh(nextPaging)
                        } else {
                            visitedSection.paging.merge(nextPaging)
                        }
                    )
                )
            }
        }
    }

    private fun getLikeBakeries(refresh: Boolean) {
        launch {
            val section = state.value.likeSection
            if (!refresh && !section.paging.canRequest) {
                Timber.i("xxx likePaging is loading or last page")
                return@launch
            }
            reduce {
                copy(
                    likeSection = likeSection.copy(
                        loading = !state.value.isRefreshing && refresh, // PullToRefresh 는 스켈레톤 제외.
                        paging = likeSection.paging.copy(isLoading = true)
                    )
                )
            }
            val nextPaging = getMyBakeriesUseCase(
                page = if (refresh) startPage else section.paging.nextPage,
                myBakeryType = MyBakeryType.LIKE,
                sort = section.sort
            )
            reduce {
                copy(
                    isRefreshing = false,
                    likeSection = likeSection.copy(
                        loading = false,
                        paging = if (refresh) {
                            likeSection.paging.refresh(nextPaging)
                        } else {
                            likeSection.paging.merge(nextPaging)
                        }
                    )
                )
            }
        }
    }
}