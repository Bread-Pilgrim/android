package com.twolskone.bakeroad.core.model

/**
 * 빵 취향 옵션 목록
 * @param breadTypes        빵 종류
 * @param flavors           빵 맛
 * @param atmospheres       빵집 분위기
 */
data class PreferenceOptions(
    val flavors: List<PreferenceOption>,
    val breadTypes: List<PreferenceOption>,
    val atmospheres: List<PreferenceOption>
)

/**
 * 빵 취향 옵션
 * @param id    아이디
 * @param name  옵션명
 */
data class PreferenceOption(
    val type: PreferenceOptionType,
    val id: Int,
    val name: String
)

enum class PreferenceOptionType {
    BREAD_TYPE,         // 빵 종류
    FLAVOR,             // 빵 맛
    ATMOSPHERE,         // 빵집 분위기
}

/**
 * 빵 취향 옵션 ID 목록
 */
data class PreferenceOptionIds(
    val flavors: List<Int>,
    val breadTypes: List<Int>,
    val atmospheres: List<Int>
)