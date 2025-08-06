package com.twolskone.bakeroad.feature.mybakery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.domain.usecase.bakery.DeleteBakeryLikeUseCase
import com.twolskone.bakeroad.core.domain.usecase.bakery.GetMyBakeriesUseCase
import com.twolskone.bakeroad.core.domain.usecase.bakery.PostBakeryLikeUseCase
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.core.model.type.BakerySortType
import com.twolskone.bakeroad.feature.mybakery.model.Tab
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakeryIntent
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakerySideEffect
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakeryState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber

@HiltViewModel
internal class MyBakeryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMyBakeriesUseCase: GetMyBakeriesUseCase,
    private val postBakeryLikeUseCase: PostBakeryLikeUseCase,
    private val deleteBakeryLikeUseCase: DeleteBakeryLikeUseCase
) : BaseViewModel<MyBakeryState, MyBakeryIntent, MyBakerySideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): MyBakeryState {
        return MyBakeryState()
    }

    private val _tabState = MutableStateFlow(Tab.VISITED)
    val tabState: StateFlow<Tab>
        get() = _tabState.asStateFlow()

    // 빵집 목록 조회에 사용된 정렬
    private val _sortState = MutableStateFlow(BakerySortType.CREATED_AT_DESC)
    val sortState: StateFlow<BakerySortType>
        get() = _sortState.asStateFlow()

    val pagingFlow = combine(tabState, sortState) { tab, sort ->
        tab to sort
    }.distinctUntilChanged()
        .flatMapLatest { (tab, sort) ->
            reduce { copy(localLikeMap = localLikeMap.clear()) }
            getMyBakeriesUseCase(myBakeryType = tab.type, sort = sort)
                .cachedIn(viewModelScope)
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
            is MyBakeryIntent.SelectTab -> _tabState.value = intent.tab

            is MyBakeryIntent.SelectSort -> _sortState.value = intent.sort

            is MyBakeryIntent.ClickBakeryLike -> {
                if (tabState.value == Tab.VISITED) {
                    reduce { copy(localLikeMap = localLikeMap.put(intent.bakeryId, intent.isLike)) }
                }
                if (intent.isLike) {
                    postBakeryLikeUseCase(bakeryId = intent.bakeryId)
                } else {
                    deleteBakeryLikeUseCase(bakeryId = intent.bakeryId)
                }
                postSideEffect(MyBakerySideEffect.RefreshBakeries)
            }
        }
    }
}