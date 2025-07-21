package com.twolskone.bakeroad.feature.bakery.detail.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.BakeryDetail
import com.twolskone.bakeroad.feature.bakery.detail.model.BakeryInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class BakeryDetailState(
    val bakeryImageList: ImmutableList<String> = persistentListOf(),
    val bakeryInfo: BakeryInfo? = null,
    val menuList: ImmutableList<BakeryDetail.Menu> = persistentListOf()
) : BaseUiState