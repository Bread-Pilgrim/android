package com.twolskone.bakeroad.core.remote.model.tour

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TourAreaResponse(
    @SerialName("title")
    val title: String = "",
    @SerialName("tour_type")
    val tourType: String = "",
    @SerialName("address")
    val address: String = "",
    @SerialName("tour_img")
    val tourImg: String = "",
    @SerialName("mapx")
    val mapX: Float = 0f,
    @SerialName("mapy")
    val mapY: Float = 0f
)