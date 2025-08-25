package com.twolskone.bakeroad.feature.badge.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent
import com.twolskone.bakeroad.core.model.Badge

internal sealed interface BadgeListIntent : BaseUiIntent {
    data class EnableBadge(val badge: Badge) : BadgeListIntent
    data class DisableBadge(val badge: Badge) : BadgeListIntent
}