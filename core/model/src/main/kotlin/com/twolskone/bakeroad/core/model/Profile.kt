package com.twolskone.bakeroad.core.model

/**
 * 프로필
 * @property nickname   닉네임
 * @property imageUrl   프로필 사진
 * @property badgeName  대표뱃지 이름
 */
data class Profile(
    val nickname: String,
    val imageUrl: String,
    val badgeName: String
) {

    companion object {
        fun ofEmpty() = Profile(nickname = "", imageUrl = "", badgeName = "")
    }
}