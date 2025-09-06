package com.twolskone.bakeroad.core.remote.model.extra

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BadgeExtraResponse(
    @SerialName("type")
    val type: String = "",
    @SerialName("badge_id")
    val badgeId: Int = -1,
    @SerialName("badge_name")
    val badgeName: String = "",
    @SerialName("badge_img")
    val badgeImg: String = "",
    @SerialName("description")
    val description: String = ""
)