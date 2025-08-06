package com.twolskone.bakeroad.core.model.type

/**
 * 빵집 정렬
 * @property CREATED_AT_DESC    최신 작성 순
 * @property REVIEW_COUNT_DESC  리뷰 많은 순
 * @property AVG_RATING_DESC    별점 높은 순
 * @property AVG_RATING_ASC     별점 낮은 순
 * @property NAME               가나다 순
 */
enum class BakerySortType(val value: String) {
    CREATED_AT_DESC(value = "CREATED_AT.DESC"),
    REVIEW_COUNT_DESC(value = "REVIEW_COUNT.DESC"),
    AVG_RATING_DESC(value = "AVG_RATING.DESC"),
    AVG_RATING_ASC(value = "AVG_RATING.ASC"),
    NAME(value = "NAME.ASC")
}