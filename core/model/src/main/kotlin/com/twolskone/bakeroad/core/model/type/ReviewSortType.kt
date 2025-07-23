package com.twolskone.bakeroad.core.model.type

/**
 * 리뷰 정렬
 * @property LIKE_COUNT_DESC    좋아요순
 * @property CREATED_AT_DESC    최신 작성순
 * @property RATING_DESC        높은 평가순
 * @property RATING_ASC         낮은 평가순
 */
enum class ReviewSortType(val value: String) {
    LIKE_COUNT_DESC("LIKE_COUNT.DESC"),
    CREATED_AT_DESC("CREATED_AT.DESC"),
    RATING_DESC("RATING.DESC"),
    RATING_ASC("RATING.ASC");

    companion object {
        fun ofValue(value: String): ReviewSortType? = entries.find { it.value == value }
    }
}