package com.twolskone.bakeroad.core.model.type

/**
 * 관광지 카테고리
 * @property NATURE             자연
 * @property HUMANITIES         인문(문화/예술/역사)
 * @property LEISURE            레포츠
 * @property SHOPPING           쇼핑
 * @property RECOMMENDED_COURSE 추천코스
 */
enum class TourAreaCategory(val code: String) {
    NATURE("A01"),
    HUMANITIES("A02"),
    LEISURE("A03"),
    SHOPPING("A04"),
    RECOMMENDED_COURSE("C01")
}