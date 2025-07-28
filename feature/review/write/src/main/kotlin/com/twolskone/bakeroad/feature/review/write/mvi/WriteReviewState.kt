package com.twolskone.bakeroad.feature.review.write.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.ReviewMenu
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal data class WriteReviewState(
    val rating: Float = 2.5f,
    val menuList: ImmutableList<ReviewMenu> = persistentListOf(),
    val photoList: PersistentList<String> = persistentListOf()
) : BaseUiState {

    /* 선택 메뉴 */
    val selectedMenuList: List<ReviewMenu>
        get() = menuList.filter { menu -> menu.count > 0 }
}