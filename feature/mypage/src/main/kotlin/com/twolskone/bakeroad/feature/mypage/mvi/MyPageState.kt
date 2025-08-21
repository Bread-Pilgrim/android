package com.twolskone.bakeroad.feature.mypage.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.Profile

@Immutable
internal data class MyPageState(
    val loading: Boolean = true,
    val profile: Profile = Profile.ofEmpty()
) : BaseUiState