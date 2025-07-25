package com.twolskone.bakeroad.feature.home.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.Area
import com.twolskone.bakeroad.core.model.EntireBusan
import com.twolskone.bakeroad.core.model.RecommendBakery
import com.twolskone.bakeroad.core.model.TourArea
import com.twolskone.bakeroad.core.model.type.TourAreaCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

@Immutable
internal data class HomeState(
    val selectedAreaCodes: PersistentSet<Int> = persistentSetOf(EntireBusan),
    val selectedTourAreaCategories: PersistentSet<TourAreaCategory> = persistentSetOf(TourAreaCategory.NATURE),
    val areaList: ImmutableList<Area> = persistentListOf(),
    val preferenceBakeryList: ImmutableList<RecommendBakery> = persistentListOf(),
    val hotBakeryList: ImmutableList<RecommendBakery> = persistentListOf(),
    val tourAreaList: ImmutableList<TourArea> = persistentListOf()
) : BaseUiState