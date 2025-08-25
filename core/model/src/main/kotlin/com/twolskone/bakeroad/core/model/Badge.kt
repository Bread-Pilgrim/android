package com.twolskone.bakeroad.core.model

/**
 * 뱃지
 * @param id                뱃지 ID
 * @param name              뱃지 이름
 * @param description       뱃지 상세설명
 * @param imageUrl          뱃지 이미지
 * @param isEarned          획득 여부
 * @param isRepresentative  대표뱃지 여부
 */
data class Badge(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val isEarned: Boolean,
    val isRepresentative: Boolean
) {

    companion object {
        fun ofEmpty(): Badge = Badge(
            id = -1,
            name = "",
            description = "",
            imageUrl = "",
            isEarned = false,
            isRepresentative = false
        )
    }
}