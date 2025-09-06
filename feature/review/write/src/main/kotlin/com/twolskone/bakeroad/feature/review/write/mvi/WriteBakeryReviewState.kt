package com.twolskone.bakeroad.feature.review.write.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.Badge
import com.twolskone.bakeroad.core.model.ReviewMenu
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

/**
 * @param loading           로딩 여부
 * @param rating            별점
 * @param isPrivate         나만 보기
 * @param content           리뷰 내용
 * @param menuList          선택 메뉴 목록
 * @param photoList         첨부 사진 목록
 * @param achievedBadgeList 획득한 뱃지 목록
 */
internal data class WriteBakeryReviewState(
    val loading: Boolean = false,
    val rating: Float = 2.5f,
    val isPrivate: Boolean = false,
    val content: String = "",
    val menuList: ImmutableList<ReviewMenu> = persistentListOf(),
    val photoList: PersistentList<String> = persistentListOf(),
    val achievedBadgeList: ImmutableList<Badge> = persistentListOf()
) : BaseUiState {

    /* 선택 메뉴 */
    val selectedMenuList: List<ReviewMenu>
        get() = menuList.filter { menu -> menu.count > 0 }
}