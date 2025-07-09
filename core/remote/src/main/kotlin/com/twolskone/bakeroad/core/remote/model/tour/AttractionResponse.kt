package com.twolskone.bakeroad.core.remote.model.tour

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttractionResponse(
    @SerialName("title")
    val title: String = "",
    @SerialName("tour_type")
    val tourType: String = "",
    @SerialName("address")
    val address: String = "",
    @SerialName("mapx")
    val mapX: Int = 0,
    @SerialName("mapy")
    val mapY: Int = 0
)