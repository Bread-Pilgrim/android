package com.twolskone.bakeroad.core.remote.model.tour

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TourEventResponse(
    @SerialName("title")
    val title: String = "",
    @SerialName("address")
    val address: String = "",
    @SerialName("start_date")
    val startDate: String = "",
    @SerialName("end_date")
    val endDate: String = "",
    @SerialName("event_img")
    val eventImg: String = "",
    @SerialName("mapx")
    val mapX: Int = 0,
    @SerialName("mapy")
    val mapY: Int = 0,
    @SerialName("tel")
    val tel: String = ""
)