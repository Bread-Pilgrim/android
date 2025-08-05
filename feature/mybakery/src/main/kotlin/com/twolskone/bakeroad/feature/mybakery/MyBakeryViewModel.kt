package com.twolskone.bakeroad.feature.mybakery

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.core.designsystem.component.snackbar.SnackbarType
import com.twolskone.bakeroad.core.exception.BakeRoadException
import com.twolskone.bakeroad.core.exception.ClientException
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakeryIntent
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakerySideEffect
import com.twolskone.bakeroad.feature.mybakery.mvi.MyBakeryState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
internal class MyBakeryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MyBakeryState, MyBakeryIntent, MyBakerySideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): MyBakeryState {
        return MyBakeryState()
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

    override suspend fun handleIntent(intent: MyBakeryIntent) {}
}