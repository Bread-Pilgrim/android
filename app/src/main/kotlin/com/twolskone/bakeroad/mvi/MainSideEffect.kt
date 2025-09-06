package com.twolskone.bakeroad.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiSideEffect
import com.twolskone.bakeroad.core.model.Badge

internal sealed interface MainSideEffect : BaseUiSideEffect {
    data object NavigateToHome : MainSideEffect
    data class AchieveBadges(val badges: List<Badge>) : MainSideEffect
}