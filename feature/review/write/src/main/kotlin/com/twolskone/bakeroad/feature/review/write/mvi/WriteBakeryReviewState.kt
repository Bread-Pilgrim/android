package com.twolskone.bakeroad.feature.review.write.mvi

import com.twolskone.bakeroad.core.common.android.base.BaseUiState
import com.twolskone.bakeroad.core.model.ReviewMenu
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

/**
 * @property loading    로딩 여부
 * @property rating     별점
 * @property isPrivate  나만보기
 * @property content    리뷰내용
 * @property menuList   선택메뉴 목록
 * @property photoList  첨부사진 목록
 */
internal data class WriteBakeryReviewState(
    val loading: Boolean = false,
    val rating: Float = 2.5f,
    val isPrivate: Boolean = false,
    val content: String = "",
    val menuList: ImmutableList<ReviewMenu> = persistentListOf(),
    val photoList: PersistentList<String> = persistentListOf()
) : BaseUiState {

    /* 선택 메뉴 */
    val selectedMenuList: List<ReviewMenu>
        get() = menuList.filter { menu -> menu.count > 0 }
}