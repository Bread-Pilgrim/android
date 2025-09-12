package com.twolskone.bakeroad.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent
import com.twolskone.bakeroad.core.model.Badge

internal sealed interface MainIntent : BaseUiIntent {
    data object RefreshHome : MainIntent
    data class AchieveBadges(val badges: List<Badge>) : MainIntent
    data object ShowTokenExpiredAlert : MainIntent
    data object HandleTokenExpired : MainIntent
}