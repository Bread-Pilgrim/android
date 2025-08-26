package com.twolskone.bakeroad.feature.mypage.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface MyPageIntent : BaseUiIntent {
    data object RefreshProfile : MyPageIntent
}