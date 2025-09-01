package com.twolskone.bakeroad.feature.mybakery.mvi

import androidx.compose.runtime.Immutable
import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.Bakery
import com.twolskone.bakeroad.core.model.type.BakerySortType
import com.twolskone.bakeroad.core.ui.model.CursorPagingUiState
import com.twolskone.bakeroad.feature.mybakery.model.Tab
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf

@Immutable
internal data class MyBakeryState(
    val isRefreshing: Boolean = false,
    val tab: Tab = Tab.VISITED,
    val visitedSection: VisitedSectionState = VisitedSectionState(),
    val likeSection: LikeSectionState = LikeSectionState()
) : BaseUiState {

    val currentSort: BakerySortType
        get() = when (tab) {
            Tab.VISITED -> visitedSection.sort
            Tab.LIKE -> likeSection.sort
        }
}

@Immutable
internal data class VisitedSectionState(
    val loading: Boolean = true,
    val sort: BakerySortType = BakerySortType.CREATED_AT_DESC,
    val paging: CursorPagingUiState<Bakery> = CursorPagingUiState(),
    val visitedBakeryIds: PersistentSet<Int> = persistentSetOf()
)

@Immutable
internal data class LikeSectionState(
    val loading: Boolean = true,
    val sort: BakerySortType = BakerySortType.CREATED_AT_DESC,
    val paging: CursorPagingUiState<Bakery> = CursorPagingUiState(),
)