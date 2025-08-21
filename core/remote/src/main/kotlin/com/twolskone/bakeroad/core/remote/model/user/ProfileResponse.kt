package com.twolskone.bakeroad.core.remote.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    @SerialName("nickname")
    val nickname: String = "",
    @SerialName("profile_img")
    val profileImg: String = "",
    @SerialName("badge_name")
    val badgeName: String = "",
    @SerialName("is_representative")
    val isRepresentative: Boolean = false
)