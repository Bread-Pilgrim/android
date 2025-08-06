package com.twolskone.bakeroad.feature.mybakery

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.bakery.GetMyBakeriesUseCase
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.core.model.paging.startPage
import com.twolskone.bakeroad.core.model.type.MyBakeryType
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakeryIntent
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakerySideEffect
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakeryState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
internal class MyBakeryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMyBakeriesUseCase: GetMyBakeriesUseCase
) : BaseViewModel<MyBakeryState, MyBakeryIntent, MyBakerySideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): MyBakeryState {
        return MyBakeryState()
    }

    init {
        getVisitedBakeries(refresh = true)
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

    override suspend fun handleIntent(intent: MyBakeryIntent) {
        when (intent) {
            is MyBakeryIntent.SelectTab -> {
                reduce { copy(tab = intent.tab) }
            }

            is MyBakeryIntent.SelectVisitedSort -> reduce { copy(visitedSection = visitedSection.copy(sort = intent.sort)) }

            is MyBakeryIntent.SelectLikeSort -> reduce { copy(likeSection = likeSection.copy(sort = intent.sort)) }

            is MyBakeryIntent.ClickBakeryLike -> {}

            is MyBakeryIntent.GetVisitedBakeries -> getVisitedBakeries(refresh = intent.refresh)

            is MyBakeryIntent.GetLikeBakeries -> TODO()
        }
    }

    private fun getVisitedBakeries(refresh: Boolean) {
        launch {
            with(state.value.visitedSection) {
                if (!paging.canRequest) {
                    Timber.i("xxx visitedPaging is loading or last page")
                    return@launch
                }
                reduce { copy(likeSection = likeSection.copy(paging = paging.copy(isLoading = true))) }
                val nextPaging = getMyBakeriesUseCase(
                    page = if (refresh) startPage else paging.nextPage,
                    myBakeryType = MyBakeryType.VISITED,
                    sort = sort
                )
                reduce { copy(visitedSection = visitedSection.copy(paging = paging.merge(nextPaging))) }
            }
        }
    }

    private fun getLikeBakeries(refresh: Boolean) {
        launch {
            with(state.value.likeSection) {
                if (!paging.canRequest) {
                    Timber.i("xxx likePaging is loading or last page")
                    return@launch
                }
                reduce { copy(likeSection = likeSection.copy(paging = paging.copy(isLoading = true))) }
                val nextPaging = getMyBakeriesUseCase(
                    page = if (refresh) startPage else paging.nextPage,
                    myBakeryType = MyBakeryType.LIKE,
                    sort = sort
                )
                reduce { copy(likeSection = likeSection.copy(paging = paging.merge(nextPaging))) }
            }
        }
    }
}