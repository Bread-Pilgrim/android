package com.twolskone.bakeroad.core.model

import com.twolskone.bakeroad.core.model.type.BakeryOpenStatus

/**
 * 추천 빵집
 * @param id            빵집 ID
 * @param name          상호명
 * @param areaCode      빵집 지역 코드
 * @param rating        평균 평점
 * @param reviewCount   리뷰 개수
 * @param openStatus    영업 상태
 * @param imageUrl      썸네일
 * @param isLike        찜 여부
 */
data class RecommendBakery(
    val id: Int,
    val name: String,
    val areaCode: Int,
    val rating: Float,
    val reviewCount: Int,
    val openStatus: BakeryOpenStatus,
    val imageUrl: String,
    val isLike: Boolean
)