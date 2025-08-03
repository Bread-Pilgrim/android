package com.twolskone.bakeroad.core.model.type

/**
 * 빵집 영업 상태
 * @property OPEN           영업중
 * @property CLOSED         영업종료
 * @property DAY_OFF        휴무일
 * @property BEFORE_OPEN    영업전
 */
enum class BakeryOpenStatus(val status: String) {
    OPEN("O"),
    CLOSED("C"),
    DAY_OFF("D"),
    BEFORE_OPEN("B");

    companion object {
        fun ofStatus(status: String): BakeryOpenStatus? = entries.find { it.status == status }
    }
}