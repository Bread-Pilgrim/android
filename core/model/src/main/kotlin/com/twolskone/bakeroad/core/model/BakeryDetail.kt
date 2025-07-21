package com.twolskone.bakeroad.core.model

import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus

/**
 * 빵집 상세
 * @param id            빵집 ID
 * @param name          상호명
 * @param address       평균 평점
 * @param phone         전화번호
 * @param rating        평균 평점
 * @param reviewCount   리뷰 개수
 * @param isLike        찜 여부
 * @param imageUrls     썸네일 리스트
 * @param menus         메뉴
 */
data class BakeryDetail(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val rating: Float,
    val reviewCount: Int,
    val openStatus: BakeryOpenStatus,
    val isLike: Boolean,
    val imageUrls: List<String>,
    val menus: List<Menu>
) {

    /**
     * 빵집 메뉴
     * @param name          메뉴 이름
     * @param price         가격
     * @param isSignature   대표메뉴 여부
     * @param imageUrl      메뉴 이미지 url
     */
    data class Menu(
        val name: String,
        val price: Int,
        val isSignature: Boolean,
        val imageUrl: String
    )
}