package com.twolskone.bakeroad.core.remote.model.badge

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BadgeResponse(
    @SerialName("badge_id")
    val badgeId: Int = -1,
    @SerialName("badge_name")
    val badgeName: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("img_url")
    val imgUrl: String = "",
    @SerialName("is_earned")
    val isEarned: Boolean = false,
    @SerialName("is_representative")
    val isRepresentative: Boolean = false
)