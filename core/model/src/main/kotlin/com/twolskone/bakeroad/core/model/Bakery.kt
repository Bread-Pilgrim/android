package com.twolskone.bakeroad.core.model

import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus

/**
 * 빵집
 * @param id                빵집 ID
 * @param name              상호명
 * @param areaCode          빵집 지역 코드
 * @param rating            평균 평점
 * @param reviewCount       리뷰 개수
 * @param openStatus        영업 상태
 * @param imageUrl          썸네일
 * @param addressGu         자치구
 * @param addressDong       동
 * @param signatureMenus    메뉴
 */
data class Bakery(
    val id: Int,
    val name: String,
    val areaCode: Int,
    val rating: Float,
    val reviewCount: Int,
    val openStatus: BakeryOpenStatus,
    val imageUrl: String,
    val addressGu: String,
    val addressDong: String,
    val signatureMenus: List<String>
)