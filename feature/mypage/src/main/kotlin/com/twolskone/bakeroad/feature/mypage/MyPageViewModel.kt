package com.twolskone.bakeroad.feature.mypage

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.eventbus.MainTabEventBus
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.feature.mypage.mvi.MyPageIntent
import com.twolskone.bakeroad.feature.mypage.mvi.MyPageSideEffect
import com.twolskone.bakeroad.feature.mypage.mvi.MyPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
internal class MyPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val mainTabEventBus: MainTabEventBus
) : BaseViewModel<MyPageState, MyPageIntent, MyPageSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): MyPageState {
        return MyPageState()
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

    override suspend fun handleIntent(intent: MyPageIntent) {}
}