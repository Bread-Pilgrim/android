package com.twolskone.bakeroad.feature.review.write.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiSideEffect

internal sealed interface WriteBakeryReviewSideEffect : BaseUiSideEffect {
    data class SetResult(val code: Int, val withFinish: Boolean): WriteBakeryReviewSideEffect
}