package com.twolskone.bakeroad.feature.onboard.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent

internal sealed interface OnboardingIntent : BaseUiIntent {
    data class MoveToPage(val page: Int) : OnboardingIntent
    data class OnNicknameTextChanged(val text: String) : OnboardingIntent
}