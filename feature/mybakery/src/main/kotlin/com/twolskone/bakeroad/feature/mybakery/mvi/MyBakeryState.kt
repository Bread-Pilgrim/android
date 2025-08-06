package com.twolskone.bakeroad.feature.mybakery.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

@Immutable
internal data class MyBakeryState(
    val localLikeMap: PersistentMap<Int, Boolean> = persistentMapOf()
) : BaseUiState