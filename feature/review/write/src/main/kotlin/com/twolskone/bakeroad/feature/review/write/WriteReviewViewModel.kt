package com.twolskone.bakeroad.feature.review.write

import androidx.lifecycle.SavedStateHandle
import com.twolskone.bakeroad.core.common.android.base.BaseViewModel
import com.twolskone.bakeroad.feature.review.write.mvi.WriteReviewIntent
import com.twolskone.bakeroad.feature.review.write.mvi.WriteReviewSideEffect
import com.twolskone.bakeroad.feature.review.write.mvi.WriteReviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class WriteReviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<WriteReviewState, WriteReviewIntent, WriteReviewSideEffect>(savedStateHandle) {

    override fun initState(savedStateHandle: SavedStateHandle): WriteReviewState {
        return WriteReviewState()
    }

    override fun handleException(cause: Throwable) {
        TODO("Not yet implemented")
    }

    override suspend fun handleIntent(intent: WriteReviewIntent) {
        TODO("Not yet implemented")
    }
}