package com.twolskone.bakeroad.core.model

/**
 * 리뷰 작성 내 선택 메뉴
 * @param id            메뉴 ID
 * @param name          메뉴 이름
 * @param isSignature   대표메뉴 여부
 * @param count         리뷰 작성 시, 메뉴 선택 개수
 */
data class ReviewMenu(
    val id: Int,
    val name: String,
    val isSignature: Boolean,
    val count: Int = 0
)

fun ReviewMenu.isOtherMenu(): Boolean = (name == OtherMenu)    // TODO. 하드코딩으로 판별.. 맞나?