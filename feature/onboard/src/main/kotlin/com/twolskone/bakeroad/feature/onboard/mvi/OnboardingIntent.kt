package com.twolskone.bakeroad.feature.onboard.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiIntent
import com.twolskone.bakeroad.core.model.PreferenceOption

internal sealed interface OnboardingIntent : BaseUiIntent {
    data class SelectBreadTypeOption(val selected: Boolean, val option: PreferenceOption) : OnboardingIntent
    data class SelectFlavorOption(val selected: Boolean, val option: PreferenceOption) : OnboardingIntent
    data class SelectBakeryTypeOption(val selected: Boolean, val option: PreferenceOption) : OnboardingIntent
    data class MoveToPage(val page: Int) : OnboardingIntent
    data class OnNicknameTextChanged(val text: String) : OnboardingIntent
}