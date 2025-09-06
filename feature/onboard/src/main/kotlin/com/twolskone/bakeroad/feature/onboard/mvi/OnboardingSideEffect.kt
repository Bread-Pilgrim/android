package com.twolskone.bakeroad.feature.onboard.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiSideEffect
import com.twolskone.bakeroad.core.model.Badge

internal sealed interface OnboardingSideEffect : BaseUiSideEffect {
    data class NavigateToMain(val achievedBadges: List<Badge> = emptyList()) : OnboardingSideEffect
    data class SetResult(val code: Int, val withFinish: Boolean) : OnboardingSideEffect
}