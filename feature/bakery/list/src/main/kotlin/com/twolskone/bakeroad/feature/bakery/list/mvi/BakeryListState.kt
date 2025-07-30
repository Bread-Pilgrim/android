package com.twolskone.bakeroad.feature.bakery.list.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.type.BakeryType
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

@Immutable
internal data class BakeryListState(
    val bakeryType: BakeryType = BakeryType.PREFERENCE,
    val localLikeMap: PersistentMap<Int, Boolean> = persistentMapOf()
) : BaseUiState