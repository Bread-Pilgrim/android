package com.twolskone.bakeroad.feature.bakery.list.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.type.BakeryType

@Immutable
internal data class BakeryListState(
    val bakeryType: BakeryType = BakeryType.PREFERENCE
) : BaseUiState