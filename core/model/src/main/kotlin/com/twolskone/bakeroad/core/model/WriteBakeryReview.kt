package com.twolskone.bakeroad.core.model

/**
 * 리뷰 작성
 * @param rating    별점
 * @param content   리뷰내용
 * @param isPrivate 나만보기 여부
 * @param menus     선택메뉴 목록
 * @param photos    첨부사진 목록
 */
data class WriteBakeryReview(
    val rating: Float,
    val content: String,
    val isPrivate: Boolean,
    val menus: List<Menu>,
    val photos: List<String>
) {

    /**
     * 선택 메뉴
     * @param id            메뉴 ID
     * @param quantity      개수
     * @param breadTypeId   빵 타입 ID
     */
    data class Menu(
        val id: Int,
        val quantity: Int,
        val breadTypeId: Int
    )
}