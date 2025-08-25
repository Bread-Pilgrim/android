package com.twolskone.bakeroad.core.model

/**
 * 프로필
 * @param nickname   닉네임
 * @param imageUrl   프로필 사진
 * @param badgeName  대표뱃지 이름
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