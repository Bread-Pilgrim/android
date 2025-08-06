package com.twolskone.bakeroad.feature.mybakery.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakerySortType
import com.twolskone.bakeroad.core.ui.model.PagingUiState
import com.twolskone.bakeroad.feature.mybakery.model.Tab
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

@Immutable
internal data class MyBakeryState(
    val tab: Tab = Tab.VISITED,
    val visitedSection: VisitedSectionState = VisitedSectionState(),
    val likeSection: LikeSectionState = LikeSectionState(),
    val localLikeMap: PersistentMap<Int, Boolean> = persistentMapOf()
) : BaseUiState {

    val currentSort: BakerySortType
        get() = when (tab) {
            Tab.VISITED -> visitedSection.sort
            Tab.LIKE -> likeSection.sort
        }
}

@Immutable
internal data class VisitedSectionState(
    val sort: BakerySortType = BakerySortType.CREATED_AT_DESC,
    val paging: PagingUiState<Bakery> = PagingUiState(),
)

@Immutable
internal data class LikeSectionState(
    val sort: BakerySortType = BakerySortType.CREATED_AT_DESC,
    val paging: PagingUiState<Bakery> = PagingUiState(),
)