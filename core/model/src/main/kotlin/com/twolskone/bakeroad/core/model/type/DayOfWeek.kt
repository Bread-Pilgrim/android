package com.twolskone.bakeroad.core.model.type

/**
 * 요일
 */
enum class DayOfWeek(val value: Int) {
    MONDAY(value = 0),
    TUESDAY(value = 1),
    WEDNESDAY(value = 2),
    THURSDAY(value = 3),
    FRIDAY(value = 4),
    SATURDAY(value = 5),
    SUNDAY(value = 6);

    companion object {
        fun ofValue(value: Int): DayOfWeek? = entries.find { it.value == value }
    }
}