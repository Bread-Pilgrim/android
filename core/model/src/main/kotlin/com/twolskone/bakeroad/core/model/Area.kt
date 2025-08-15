package com.twolskone.bakeroad.core.model

const val EntireBusan = 14  // 부산 전체 code

/**
 * 지역
 * @param code  지역 코드
 * @param name  지역 이름
 */
data class Area(
    val code: Int,
    val name: String
)