package com.twolskone.bakeroad.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface MainIntent : BaseUiIntent {
    data object RefreshHome: MainIntent
}